package ru.drsk.progserega.inspectionsheet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
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
import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;
import ru.drsk.progserega.inspectionsheet.entities.Point;
import ru.drsk.progserega.inspectionsheet.entities.Voltage;
import ru.drsk.progserega.inspectionsheet.services.EquipmentService;
import ru.drsk.progserega.inspectionsheet.services.ILocation;
import ru.drsk.progserega.inspectionsheet.services.OrganizationService;

public class SearchObject extends AppCompatActivity implements SelectOrganizationDialogFragment.ISelectOrganizationListener {

    public final static String OBJECT_TYPE = "object_type";
    public final static String LINE_TYPE = "line_type";

    private InspectionSheetApplication application;
    private EquipmentListAdapter listAdapter;
    private EquipmentService equipmentService;
    private OrganizationService organizationService;
    private ILocation locationService;

    SelectOrganizationDialogFragment selectOrganizationDlog;

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
    }

    protected void onListItemClick(AdapterView<?> list, View v, int position, long id) {
        Equipment item = (Equipment) listAdapter.getItem(position);

        Toast.makeText(this, item.getName() + " selected", Toast.LENGTH_LONG).show();
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
            Point userPosition = locationService.getUserPosition();
            equipmentService.addFilter(EquipmentService.FILTER_POSITION, userPosition);
        } else {
            equipmentService.removeFilter(EquipmentService.FILTER_POSITION);
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
}
