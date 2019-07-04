package ru.drsk.progserega.inspectionsheet.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;
import ru.drsk.progserega.inspectionsheet.entities.catalogs.InspectionType;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.InspectLineContract;
import ru.drsk.progserega.inspectionsheet.ui.presenters.InspectLinePresenter;

import static ru.drsk.progserega.inspectionsheet.ui.activities.InspectLineTower.NEXT_TOWER;


public class InspectLine extends AppCompatActivity  implements InspectLineContract.View{

    private InspectionSheetApplication application;
    private InspectLineContract.Presenter presenter;

    private Spinner inspectionTypesSpinner;
    private ArrayAdapter inspectionTypesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect_line);

        this.application = (InspectionSheetApplication) this.getApplication();
        this.presenter = new InspectLinePresenter(this, application);

        setTitle("Осмотр линии");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        createInspectionTypeSpinner();

        presenter.onViewCreate();
    }

    @Override
    public void setLineName(String lineName){
        TextView lineNameText = (TextView) findViewById(R.id.inspection_line_line_name);
        lineNameText.setText(lineName);
    }

    @Override
    public void setSPName(String spName) {
        TextView view = (TextView) findViewById(R.id.inspection_line_sp_name);
        view.setText(spName);
    }

    @Override
    public void setResName(String resName) {
        TextView view = (TextView) findViewById(R.id.inspection_line_res_name);
        view.setText(resName);
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

    public void onStartExploitationYearClick(View view) {
    }

    public void onNextBtnClick(View view) {
        Intent intent = new Intent(this, InspectLineTower.class);
        intent.putExtra(NEXT_TOWER, "");
        startActivity(intent);
    }

    private void createInspectionTypeSpinner(){
        inspectionTypesSpinner = (Spinner) findViewById(R.id.inspection_type_spinner);
        inspectionTypesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new ArrayList<String>());
        inspectionTypesAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        inspectionTypesSpinner.setAdapter(inspectionTypesAdapter);

        inspectionTypesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                onSelectInspectionType(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    @Override
    public void setInspectionTypesSpinnerData(List<InspectionType> types, int  selected){
        List<String> spinerItems = new ArrayList<>();
        spinerItems.add("не задан");
        for(InspectionType type: types){
            spinerItems.add(type.getInspection());
        }

        inspectionTypesAdapter.clear();
        inspectionTypesAdapter.addAll(spinerItems);
        inspectionTypesAdapter.notifyDataSetChanged();
        inspectionTypesSpinner.setSelection(selected);
    }

    private void onSelectInspectionType(int position) {
        if(position == 0){
            return;
        }
        presenter.onInspectionTypeSelect(position - 1);
    }


    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
