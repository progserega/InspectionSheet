package ru.drsk.progserega.inspectionsheet.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.print.PrinterId;
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
    private EquipmentListAdapter adapter;
    private EquipmentType equipmentType;
    private EquipmentService equipmentService;
        private Voltage voltage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_object);

        this.application = (InspectionSheetApplication) this.getApplication();
        this.equipmentService = this.application.getEquipmentService();

        Intent intent = getIntent();

        equipmentType = (EquipmentType) intent.getSerializableExtra(OBJECT_TYPE);
        Log.i("SearchObject", equipmentType.name());

        voltage = null;
        if(intent.hasExtra(LINE_TYPE)) {
            voltage = (Voltage) intent.getSerializableExtra(LINE_TYPE);
        }

        List<Equipment> equipments = loadEquipments();
        this.adapter = new EquipmentListAdapter(this, equipments);
        setListAdapter(this.adapter);

        EditText yourEditText = (EditText) findViewById(R.id.editText);

        final SearchObject that = this;
        yourEditText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {

               //  Toast.makeText(that, s.toString(), Toast.LENGTH_LONG).show();
                List<Equipment> equipments = that.loadEquipmentsByName(s.toString());
                that.ReloadListValues(equipments);
            }
        });
    }

    private List<Equipment> loadEquipments(){
        List<Equipment> equipments = new ArrayList<>();

        if(equipmentType == EquipmentType.LINE){
            equipments = equipmentService.getByType(equipmentType, voltage);
        }else {
            equipments = equipmentService.getByType(equipmentType);
        }

        return equipments;
    }

    private List<Equipment> loadEquipmentsByName(String name){
        List<Equipment> equipments = new ArrayList<>();

        if(equipmentType == EquipmentType.LINE){
            equipments = equipmentService.getByTypeAndName(equipmentType, voltage, name);
        }else {
           // equipments = this.application.getEquipmentService().getByType(equipmentType);
        }

        return equipments;
    }


//    @Override
//    protected void onListItemClick(ListView l, View v, int position, long id) {
//        Equipment item = (Equipment) getListAdapter().getItem(position);
//
//        Toast.makeText(this, item.getName() + " selected", Toast.LENGTH_LONG).show();
//    }

    public void SearchBtnClick(View view) {
        List<Equipment> equipments = this.application.getEquipmentService().getByType(EquipmentType.LINE, Voltage.VL_35_110KV);

        ReloadListValues(equipments);
    }

    private void ReloadListValues( List<Equipment> equipments){
        // update data in our adapter
        this.adapter.getValues().clear();
        this.adapter.getValues().addAll(equipments);
        // fire the event
        this.adapter.notifyDataSetChanged();
    }
}
