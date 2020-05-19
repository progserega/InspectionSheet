package ru.drsk.progserega.inspectionsheet.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.ui.adapters.InspectionAdapter;
import ru.drsk.progserega.inspectionsheet.ui.adapters.StationEquipmentsListAdapter;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.StationEquipmentContract;
import ru.drsk.progserega.inspectionsheet.ui.presenters.InspectStationPresenter;
import ru.drsk.progserega.inspectionsheet.ui.presenters.StationEquipmentPresenter;

public class StationEquipment extends AppCompatActivity implements StationEquipmentContract.View {

    private InspectionSheetApplication application;
    private StationEquipmentContract.Presenter presenter;

    private StationEquipmentsListAdapter equipmentsListAdapter;
    private RecyclerView equipmentsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_equipment);

        this.application = (InspectionSheetApplication) this.getApplication();
        this.presenter = new StationEquipmentPresenter(application, this);

        setTitle("Список оборудования");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        initEquipmentList();

        presenter.onViewCreated();

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

    private void initEquipmentList() {

        equipmentsListAdapter = new StationEquipmentsListAdapter(new ArrayList<>());
        equipmentsList = (RecyclerView) findViewById(R.id.station_equipment_list);
        equipmentsList.setAdapter(equipmentsListAdapter);

        equipmentsListAdapter.setOnItemClickListener(new StationEquipmentsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                presenter.onEquipmentListItemClick(position);
            }
        });
        equipmentsList.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        equipmentsList.addItemDecoration(itemDecoration);
    }

    @Override
    public void setEquipments(List<Equipment> equipments) {
        equipmentsListAdapter.setEquipments(equipments);
    }

    @Override
    public void startInspectEquipmentActivity() {
        Intent intent = new Intent(this, InspectStationEquipment.class);
        //intent.putExtra(NEXT_SECTION, nextSectionId);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
