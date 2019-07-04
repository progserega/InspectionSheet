package ru.drsk.progserega.inspectionsheet.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.utility.ButtonUtils;
import ru.drsk.progserega.inspectionsheet.entities.Line;
import ru.drsk.progserega.inspectionsheet.entities.LineTower;
import ru.drsk.progserega.inspectionsheet.entities.catalogs.InspectionType;
import ru.drsk.progserega.inspectionsheet.entities.catalogs.Material;
import ru.drsk.progserega.inspectionsheet.entities.catalogs.TowerType;
//import ru.drsk.progserega.inspectionsheet.services.TowersService;
import ru.drsk.progserega.inspectionsheet.storages.ICatalogStorage;
import ru.drsk.progserega.inspectionsheet.ui.activities.TowerDeffectsActivity;

@Deprecated
public class InspectTower extends AppCompatActivity {

    public final static String LINE_ID = "line_id";
    public final static String LINE_NAME = "line_name";

    private InspectionSheetApplication application;
   // private TowersService towersService;
    private ICatalogStorage catalogStorage;

   // private long lineId;

    private Spinner towersSpinner;
    private ArrayAdapter towersAdapter;
    private Spinner materialsSpinner;
    private ArrayAdapter materialsAdapter;
    private Spinner towerTypesSpinner;
    private ArrayAdapter towerTypesAdapter;
    private Spinner inspectionTypesSpinner;
    private ArrayAdapter inspectionTypesAdapter;

    private List<LineTower> lineTowers;
    private LineTower selectedLineTower;
    List<Material> materials;
    List<TowerType> towerTypes;
    List<InspectionType> inspectionTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect_tower);

        ButtonUtils.initSaveBtnImg((ImageButton) findViewById(R.id.inpsect_tower_save_btn));

        this.application = (InspectionSheetApplication) this.getApplication();
       // towersService = application.getTowersService();
        catalogStorage = application.getCatalogStorage();

//        Intent intent = getIntent();

//        lineId = intent.getLongExtra(LINE_ID, 0);
//        String lineName = intent.getStringExtra(LINE_NAME);
        Line line = application.getLineInspection().getLine();

        TextView lineNameText = (TextView) findViewById(R.id.inspection_tower_name);
        lineNameText.setText(line.getName());


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

        //lineTowers = towersService.getTowersByLine(lineId);
//        lineTowers = line.getTowers();
//        setTowersSpinnerData(lineTowers);

        materials = catalogStorage.getMaterials();
        setMaterialsSpinnerData(materials);

        towerTypes = catalogStorage.getTowerTypes();
        setTowerTypesSpinnerData(towerTypes);

        inspectionTypes = catalogStorage.getInspectionTypes();
        setInspectionTypesSpinnerData(inspectionTypes);
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

    private void setMaterialsSpinnerData(List<Material> materials){
        List<String> spinerItems = new ArrayList<>();
        spinerItems.add("не задан");
        for(Material mat: materials){
            spinerItems.add(mat.getName());
        }

        materialsAdapter.clear();
        materialsAdapter.addAll(spinerItems);
        materialsAdapter.notifyDataSetChanged();
    }

    private void setTowerTypesSpinnerData(List<TowerType> types){
        List<String> spinerItems = new ArrayList<>();
        spinerItems.add("не задан");
        for(TowerType type: types){
            spinerItems.add(type.getType());
        }

        towerTypesAdapter.clear();
        towerTypesAdapter.addAll(spinerItems);
        towerTypesAdapter.notifyDataSetChanged();
    }

    private void setInspectionTypesSpinnerData(List<InspectionType> types){
        List<String> spinerItems = new ArrayList<>();
        spinerItems.add("не задан");
        for(InspectionType type: types){
            spinerItems.add(type.getInspection());
        }

        inspectionTypesAdapter.clear();
        inspectionTypesAdapter.addAll(spinerItems);
        inspectionTypesAdapter.notifyDataSetChanged();
    }

    private void onSelectTower(int position) {
        if(position == 0){
            selectedLineTower = null;
            return;
        }

        selectedLineTower = lineTowers.get(position - 1);
       // application.getLineInspection().setLineTower(selectedLineTower);

    }

    private void onSelectMaterial(int position) {

        if(position == 0 || selectedLineTower == null){
            materialsSpinner.setSelection(0);
            return;
        }

        selectedLineTower.getTower().setMaterial(materials.get(position - 1));

    }

    private void onSelectTowerType(int position) {
        if(position == 0 || selectedLineTower == null){
            return;
        }

        selectedLineTower.getTower().setTowerType(towerTypes.get(position - 1));
    }

    private void onSelectInspectionType(int position) {
        if(position == 0){
            return;
        }
        application.getLineInspection().setInspectionType(inspectionTypes.get(position - 1));
    }

    public void onNextBtnPress(View view){
        Intent intent = new Intent(this, TowerDeffectsActivity.class);
        startActivity(intent);
    }

    public void onSaveBtnPress(View view){

    }

}
