package ru.drsk.progserega.inspectionsheet.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.activities.adapters.EquipmentListAdapter;
import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;
import ru.drsk.progserega.inspectionsheet.entities.inspections.EquipmentInspection;
import ru.drsk.progserega.inspectionsheet.entities.Point;
import ru.drsk.progserega.inspectionsheet.entities.Voltage;
import ru.drsk.progserega.inspectionsheet.entities.inspections.SubstationInspection;
import ru.drsk.progserega.inspectionsheet.services.EquipmentService;
import ru.drsk.progserega.inspectionsheet.services.ILocation;
import ru.drsk.progserega.inspectionsheet.services.ILocationChangeListener;
import ru.drsk.progserega.inspectionsheet.services.OrganizationService;

public class SearchObject extends AppCompatActivity implements SelectOrganizationDialogFragment.ISelectOrganizationListener, ILocationChangeListener {

    private static final int REQUEST_CODE_ACCESS_FINE_LOCATION = 5;

    public final static String OBJECT_TYPE = "object_type";
    public final static String LINE_TYPE = "line_type";

    private InspectionSheetApplication application;
    private EquipmentListAdapter listAdapter;
    private EquipmentService equipmentService;
    private OrganizationService organizationService;
    private ILocation locationService;

    SelectOrganizationDialogFragment selectOrganizationDlog;

    private CheckBox gpsCheckbox;
    private boolean allowGPS = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_object);

        this.application = (InspectionSheetApplication) this.getApplication();
        this.equipmentService = this.application.getEquipmentService();
        this.locationService = this.application.getLocationService();
        this.organizationService = this.application.getOrganizationService();

        Intent intent = getIntent();

        EquipmentType equipmentType = (EquipmentType) intent.getSerializableExtra(OBJECT_TYPE);
        equipmentService.addFilter(EquipmentService.FILTER_TYPE, equipmentType);

        Log.i("SearchObject", equipmentType.name());

        if (intent.hasExtra(LINE_TYPE)) {
            Voltage voltage = (Voltage) intent.getSerializableExtra(LINE_TYPE);
            equipmentService.addFilter(EquipmentService.FILTER_VOLTAGE, voltage);
        }

        final SearchObject that = this;

        listAdapter = new EquipmentListAdapter(this, new ArrayList<Equipment>());
        ListView equipmentList = (ListView) findViewById(R.id.equipmentList);
        equipmentList.setAdapter(listAdapter);
        //Создание слушателя
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> list, View itemView, int position, long id) {
                that.onListItemClick(list, itemView, position, id);
            }
        };
        equipmentList.setOnItemClickListener(itemClickListener);

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

        locationService.setLocationChangeListener(this);
    }

    protected void onListItemClick(AdapterView<?> list, View v, int position, long id) {
        Equipment equipment = (Equipment) listAdapter.getItem(position);

        // Toast.makeText(this, equipment.getEquipmentInspection() + " selected", Toast.LENGTH_LONG).show();

        if (equipment.getType() == EquipmentType.TRANS_SUBSTATION) {
            //NOT IMPLEMENTED
            return;
        }

        EquipmentInspection equipmentInspection = null;
        Intent intent = null;
        if (equipment.getType() == EquipmentType.LINE) {
            equipmentInspection = new EquipmentInspection(equipmentService.getLineById(equipment.getId()));
            intent = new Intent(this, InspectTower.class);
            application.setEquipmentInspection(equipmentInspection);
        }

        if (equipment.getType() == EquipmentType.SUBSTATION) {
            //equipmentInspection = new EquipmentInspection(equipmentService.getSubstationById(equipment.getId()));
            SubstationInspection substationInspection = new SubstationInspection(equipmentService.getSubstationById(equipment.getId()));
            application.setSubstationInspection(substationInspection);

            intent = new Intent(this, InspectTransformator.class);
        }


        startActivity(intent);

        locationService.stopUsingGPS();
    }

    private void ReloadListValues() {
        List<Equipment> equipments = equipmentService.getEquipments();

        // update data in our listAdapter
        listAdapter.getValues().clear();
        listAdapter.getValues().addAll(equipments);
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
    public void onSelectOrganization(int enterpriseId, int areaId) {

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

    private boolean checkGPSPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            Toast.makeText(this, "Permission (already) Granted!", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    private void requestGPSAccess() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

            showExplanation(
                    "Необходимо разрешение",
                    "Для получения координат местоположения, необходим доступ к модулю GPS",
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    REQUEST_CODE_ACCESS_FINE_LOCATION);

        } else {
            requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, REQUEST_CODE_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ACCESS_FINE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();
                    filterByPosition(locationService.getUserPosition());
                    ReloadListValues();
                } else {
                    Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                    gpsCheckbox.setChecked(false);
                }
        }
    }

    private void showExplanation(String title, String message, final String permission, final int permissionRequestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        requestPermission(permission, permissionRequestCode);
                    }
                });
        builder.create().show();
    }

    private void requestPermission(String permissionName, int permissionRequestCode) {
        ActivityCompat.requestPermissions(SearchObject.this,
                new String[]{permissionName}, permissionRequestCode);
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
        filterByPosition(location);
        ReloadListValues();
    }
}
