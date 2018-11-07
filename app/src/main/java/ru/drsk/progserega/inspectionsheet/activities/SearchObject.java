package ru.drsk.progserega.inspectionsheet.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;
import ru.drsk.progserega.inspectionsheet.entities.Voltage;
import ru.drsk.progserega.inspectionsheet.services.EquipmentService;

public class SearchObject extends ListActivity {

    public final static String OBJECT_TYPE = "object_type";
    public final static String LINE_TYPE = "line_type";

    private InspectionSheetApplication application;
    private EquipmentListAdapter listAdapter;
    private EquipmentService equipmentService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_object);

        this.application = (InspectionSheetApplication) this.getApplication();
        this.equipmentService = this.application.getEquipmentService();

        Intent intent = getIntent();

        EquipmentType equipmentType = (EquipmentType) intent.getSerializableExtra(OBJECT_TYPE);
        equipmentService.addFilter(EquipmentService.FILTER_TYPE, equipmentType);

        Log.i("SearchObject", equipmentType.name());

        if (intent.hasExtra(LINE_TYPE)) {
            Voltage voltage = (Voltage) intent.getSerializableExtra(LINE_TYPE);
            equipmentService.addFilter(EquipmentService.FILTER_VOLTAGE, voltage);
        }


        listAdapter = new EquipmentListAdapter(this, new ArrayList<Equipment>());
        setListAdapter(listAdapter);
        ReloadListValues();


        EditText nameFilterText = (EditText) findViewById(R.id.editText);
        final SearchObject that = this;
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

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Equipment item = (Equipment) getListAdapter().getItem(position);

        Toast.makeText(this, item.getName() + " selected", Toast.LENGTH_LONG).show();
    }

//    public void SearchBtnClick(View view) {
//        List<Equipment> equipments = this.application.getEquipmentService().getByType(EquipmentType.LINE, Voltage.VL_35_110KV);
//
//        ReloadListValues(equipments);
//    }

    private void ReloadListValues() {
        List<Equipment> equipments = equipmentService.getEquipments();

        // update data in our listAdapter
        listAdapter.getValues().clear();
        listAdapter.getValues().addAll(equipments);
        // fire the event
        listAdapter.notifyDataSetChanged();
    }
}
