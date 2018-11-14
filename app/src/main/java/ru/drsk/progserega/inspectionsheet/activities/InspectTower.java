package ru.drsk.progserega.inspectionsheet.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.LineTower;
import ru.drsk.progserega.inspectionsheet.services.TowersService;

public class InspectTower extends AppCompatActivity {

    public final static String LINE_ID = "line_id";
    public final static String LINE_NAME = "line_name";

    private InspectionSheetApplication application;
    private TowersService towersService;
    private long lineId;

    private Spinner towersSpinner;
    private ArrayAdapter towersAdapter;
    private Spinner materialsSpinner;
    private ArrayAdapter materialsAdapter;
    private Spinner towerTypesSpinner;
    private ArrayAdapter towerTypesAdapter;
    private Spinner inspectionTypesSpinner;
    private ArrayAdapter inspectionTypesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect_tower);

        this.application = (InspectionSheetApplication) this.getApplication();
        towersService = application.getTowersService();

        Intent intent = getIntent();

        lineId = intent.getLongExtra(LINE_ID, 0);
        String lineName = intent.getStringExtra(LINE_NAME);

        TextView lineNameText = (TextView) findViewById(R.id.inspection_tower_name);
        lineNameText.setText(lineName);


        towersAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new ArrayList<String>());
        towersAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        towersSpinner = (Spinner) findViewById(R.id.towers_spinner);
        towersSpinner.setAdapter(towersAdapter);
        towersSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                onSelectTower(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        materialsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new ArrayList<String>());
        materialsAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        materialsSpinner = (Spinner) findViewById(R.id.materials_spinner);
        materialsSpinner.setAdapter(materialsAdapter);
        materialsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                onSelectMaterial(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        towerTypesSpinner = (Spinner) findViewById(R.id.tower_type_spinner);
        towerTypesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new ArrayList<String>());
        towerTypesAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        towerTypesSpinner.setAdapter(towerTypesAdapter);

        towerTypesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                onSelectTowerType(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

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


        List<LineTower> towers = towersService.getTowersByLine(lineId);
        setTowersSpinnerData(towers);
    }

    private void setTowersSpinnerData(List<LineTower> lineTowers){
        List<String> spinnerItems = new ArrayList<>();
        spinnerItems.add("");
        for (LineTower lineTower : lineTowers) {
            spinnerItems.add(lineTower.getNumber());
        }

        towersAdapter.clear();
        towersAdapter.addAll(spinnerItems);
        towersAdapter.notifyDataSetChanged();
    }


    private void onSelectTower(int position) {
    }

    private void onSelectMaterial(int position) {
    }

    private void onSelectTowerType(int position) {
    }

    private void onSelectInspectionType(int position) {
    }

}
