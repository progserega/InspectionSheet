package ru.drsk.progserega.inspectionsheet.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;
import ru.drsk.progserega.inspectionsheet.entities.Point;
import ru.drsk.progserega.inspectionsheet.entities.Voltage;
import ru.drsk.progserega.inspectionsheet.entities.inspections.ISubstationInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.SubstationInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TransformerSubstationInspection;
import ru.drsk.progserega.inspectionsheet.services.EquipmentService;
import ru.drsk.progserega.inspectionsheet.services.ILocation;
import ru.drsk.progserega.inspectionsheet.services.ILocationChangeListener;
import ru.drsk.progserega.inspectionsheet.services.OrganizationService;
import ru.drsk.progserega.inspectionsheet.ui.adapters.EquipmentRCListAdapter;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.SearchObjectsContract;
import ru.drsk.progserega.inspectionsheet.ui.presenters.SearchObjectPresenter;

public class SearchObject extends ActivityWithGPS implements
        SelectOrganizationDialogFragment.ISelectOrganizationListener,
        ILocationChangeListener,
        SearchObjectsContract.View {

    // private static final int REQUEST_CODE_ACCESS_FINE_LOCATION = 5;

    public final static String OBJECT_TYPE = "object_type";
    public final static String LINE_TYPE = "line_type";

    private InspectionSheetApplication application;
    //private EquipmentListAdapter listAdapter;
    private EquipmentRCListAdapter listAdapter;
    private EquipmentService equipmentService;
    private OrganizationService organizationService;
    private ILocation locationService;
    private EquipmentType equipmentType;

    SelectOrganizationDialogFragment selectOrganizationDlog;

    private SearchObjectsContract.Presenter presenter;

    private CheckBox gpsCheckbox;
    private boolean allowGPS = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_object);

        setTitle("Выбор объекта осмотра");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        this.application = (InspectionSheetApplication) this.getApplication();

        this.presenter = new SearchObjectPresenter(this, this.application);

        this.equipmentService = this.application.getEquipmentService();
        this.locationService = this.application.getLocationService();
        this.organizationService = this.application.getOrganizationService();
        this.application.setCurrentSubstationInspection(null);

        Intent intent = getIntent();

        equipmentType = (EquipmentType) intent.getSerializableExtra(OBJECT_TYPE);
        equipmentService.addFilter(EquipmentService.FILTER_TYPE, equipmentType);

        Log.i("SearchObject", equipmentType.name());

        Button searchBySP = (Button) findViewById(R.id.selectBySpBt);
        if (equipmentType.equals(EquipmentType.LINE)) {
            searchBySP.setVisibility(View.GONE);
        } else if (equipmentType.equals(EquipmentType.SUBSTATION)) {
            searchBySP.setVisibility(View.GONE);
        } else {
            searchBySP.setVisibility(View.VISIBLE);
        }
//        if (intent.hasExtra(LINE_TYPE)) {
//            Voltage voltage = (Voltage) intent.getSerializableExtra(LINE_TYPE);
//           // equipmentService.addFilter(EquipmentService.FILTER_VOLTAGE, voltage);
//        }

        final SearchObject that = this;

        listAdapter = new EquipmentRCListAdapter(new ArrayList< Equipment >());
        RecyclerView equipmentList = (RecyclerView) findViewById(R.id.equipment_rc_list);
        equipmentList.setAdapter(listAdapter);
        equipmentList.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        equipmentList.addItemDecoration(itemDecoration);

        listAdapter.setOnItemClickListener(new EquipmentRCListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                onListItemClick(position);
            }
        });

        listAdapter.setMapClickListener(new EquipmentRCListAdapter.OnItemMapClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                // Toast.makeText(that, "MAP CLICK!!!", Toast.LENGTH_SHORT).show();
                onListItemShowOnMapClick(position);
            }
        });


        ReloadListValues();


        EditText nameFilterText = (EditText) findViewById(R.id.editText);

        nameFilterText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String nameFilter = s.toString();
                if (nameFilter.isEmpty()) {
                    that.equipmentService.removeFilter(EquipmentService.FILTER_NAME);
                } else {
                    that.equipmentService.addFilter(EquipmentService.FILTER_NAME, nameFilter);
                }
                that.ReloadListValues();
            }
        });

        gpsCheckbox = (CheckBox) findViewById(R.id.searchNearestChb);

        locationService.setLocationChangeListener(new WeakReference< ILocationChangeListener >(this));

        Spinner selectVoltageSpinner = (Spinner) findViewById(R.id.voltage_spinner);
        TextView voltageText = (TextView) findViewById(R.id.selectVoltageText);
        if (equipmentType.equals(EquipmentType.LINE)) {
            selectVoltageSpinner.setVisibility(View.VISIBLE);
            voltageText.setVisibility(View.VISIBLE);
        } else {
            selectVoltageSpinner.setVisibility(View.GONE);
            voltageText.setVisibility(View.GONE);
        }


        final List< String > voltageSpinnerItems = Voltage.names();
        voltageSpinnerItems.add(0, "ВСЕ");


        ArrayAdapter< String > voltageListAdapter = new ArrayAdapter< String >(this, android.R.layout.simple_spinner_item, voltageSpinnerItems);
        voltageListAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        selectVoltageSpinner.setAdapter(voltageListAdapter);
        // enterpriseSpinner.setSelection(enterpriseSpinnerSelection);

        selectVoltageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView< ? > parentView, View selectedItemView, int position, long id) {
                String voltageSelected = voltageSpinnerItems.get(position);
                if (voltageSelected.equals("ВСЕ")) {
                    that.equipmentService.removeFilter(EquipmentService.FILTER_VOLTAGE);
                    that.ReloadListValues();
                } else {
                    that.equipmentService.addFilter(EquipmentService.FILTER_VOLTAGE, Voltage.get(voltageSelected));
                    that.ReloadListValues();
                }
            }

            @Override
            public void onNothingSelected(AdapterView< ? > parentView) {
                // your code here
            }

        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // получим идентификатор выбранного пункта меню
        int id = item.getItemId();
        // Операции для выбранного пункта меню
        switch (id) {

            case android.R.id.home:
                onBackPressed();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }

    }

    //protected void onListItemClick(AdapterView< ? > list, View v, int position, long id) {
    protected void onListItemClick(int position) {
        Equipment equipment = (Equipment) listAdapter.getEquipments().get(position);


        Intent intent = null;
        if (equipment.getType() == EquipmentType.LINE) {

            LineInspection lineInspection = application.getLineInspectionStorage().getLineInspection(equipment.getId());
            if (lineInspection == null) {
                return;
            }
            intent = new Intent(this, InspectLine.class);
            application.setCurrentLineInspection(lineInspection);
        }

        if (equipment.getType() == EquipmentType.SUBSTATION) {

            ISubstationInspection substationInspection = getInspection(equipment);
            if (substationInspection == null) {
                substationInspection = new SubstationInspection(equipmentService.getSubstationById(equipment.getId()));
                application.getSubstationInspections().add(substationInspection);
            }

            application.setCurrentSubstationInspection(substationInspection);

            intent = new Intent(this, InspectTransformer.class);
        }

        if (equipment.getType() == EquipmentType.TRANS_SUBSTATION) {

            ISubstationInspection substationInspection = getInspection(equipment);
            if (substationInspection == null) {
                substationInspection = new TransformerSubstationInspection(equipmentService.getTransformerSubstationById(equipment.getId()));
                application.getSubstationInspections().add(substationInspection);
            }
            application.setCurrentSubstationInspection(substationInspection);

            intent = new Intent(this, InspectTransformer.class);
        }


        startActivity(intent);

        locationService.stopUsingGPS();
    }

    protected void onListItemShowOnMapClick(int position) {
        Equipment equipment = (Equipment) listAdapter.getEquipments().get(position);

        Point location = equipment.getLocation();
        if(location == null){
            return;
        }

        String uriStr = String.format("geo:%f,%f?z=19", location.getLat(),location.getLon());
        showMap(Uri.parse(uriStr));
    }

    public void showMap(Uri geoLocation) {
        Intent mapIntent = new Intent(Intent.ACTION_VIEW);
        mapIntent.setData(geoLocation);
        // Make the Intent explicit by setting the Google Maps package
        //mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    private void ReloadListValues() {
        List< Equipment > equipments = equipmentService.getEquipments();

        // update data in our listAdapter
        listAdapter.getEquipments().clear();
        listAdapter.getEquipments().addAll(equipments);
        // fire the event
        listAdapter.notifyDataSetChanged();
    }

    public void onSearchNearestCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        if (checked) {

            if (checkGPSPermission()) {
                filterByPosition(locationService.getUserPosition());
            } else {
                requestGPSAccess();
            }
        } else {
            equipmentService.removeFilter(EquipmentService.FILTER_POSITION);
            locationService.stopUsingGPS();
        }

        ReloadListValues();

    }

    public void onSelectOrganizationBtnClick(View view) {
        FragmentManager fm = getSupportFragmentManager();
        if (selectOrganizationDlog == null) {
            selectOrganizationDlog = SelectOrganizationDialogFragment.newInstance(organizationService);
        }
        selectOrganizationDlog.show(fm, "select_organization");

    }

    @Override
    public void onSelectOrganization(long enterpriseId, long areaId) {

        equipmentService.removeFilter(EquipmentService.FILTER_AREA);
        equipmentService.removeFilter(EquipmentService.FILTER_ENTERPRISE);

        if (enterpriseId != 0) {
            equipmentService.addFilter(EquipmentService.FILTER_ENTERPRISE, enterpriseId);
        }

        if (areaId != 0) {
            equipmentService.addFilter(EquipmentService.FILTER_AREA, areaId);
        }

        ReloadListValues();
        //Toast.makeText(this, inputText, Toast.LENGTH_LONG).show();
    }

    private void filterByPosition(Point userPosition) {
        Toast.makeText(this, "Фильтруем позицию!!!!", Toast.LENGTH_LONG).show();

        if (userPosition.getLon() == 0 || userPosition.getLat() == 0) {
            buildAlertMessageNoGps();
        }
        equipmentService.addFilter(EquipmentService.FILTER_POSITION, userPosition);
    }

    @Override
    protected void onGPSRequestGaranted() {
        filterByPosition(locationService.getUserPosition());
    }

    @Override
    protected void onGPSRequestDenied() {
        gpsCheckbox.setChecked(false);
    }

    private void buildAlertMessageNoGps() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        // builder1.setMessage(getResources().getString(R.string.location_not_deter));
        builder1.setMessage("ПОЗИЦИЯ НЕ УСТАНОВЛЕНА");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                //getResources().getString(R.string.try_again),
                "ПОПРОБОВАТЬ СНОВА",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        //setLocationAddress();
                    }
                });

        builder1.setNegativeButton(
                android.R.string.cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    @Override
    public void onLocationChange(Point location) {
        if (gpsCheckbox.isChecked()) {
            filterByPosition(location);
            ReloadListValues();
        }
        // Toast.makeText(this, "Позиция изменилась!!", Toast.LENGTH_SHORT).show();
    }


    private ISubstationInspection getInspection(Equipment equipment) {
        List< ISubstationInspection > inspections = application.getSubstationInspections();

        for (ISubstationInspection substationInspection : inspections) {
            Equipment substationInspectionEquipment = substationInspection.getEquipment();
            if (substationInspectionEquipment.getType().equals(equipment.getType())
                    && substationInspectionEquipment.getId() == equipment.getId()) {
                return substationInspection;
            }
        }
        return null;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (equipmentType.equals(EquipmentType.SUBSTATION) || equipmentType.equals(EquipmentType.TRANS_SUBSTATION)) {
            ISubstationInspection inspection = application.getCurrentSubstationInspection();
            if (inspection == null) {
                return;
            }

            Equipment equipment = inspection.getEquipment();
            if (equipment == null) {
                return;
            }

            for (Equipment eq : listAdapter.getEquipments()) {
                if (eq.getId() == equipment.getId()) {
                    eq.setInspectionDate(equipment.getInspectionDate());
                    eq.setInspectionPercent(equipment.getInspectionPercent());
                    break;
                }
            }

            listAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        equipmentService.clearFilters();
        locationService.stopUsingGPS();

        presenter.onDestroy();
    }
}
