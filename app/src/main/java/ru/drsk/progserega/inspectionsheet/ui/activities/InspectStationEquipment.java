package ru.drsk.progserega.inspectionsheet.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.InspectStationEquipmentContract;
import ru.drsk.progserega.inspectionsheet.ui.presenters.InspectStationEquipmentPresenter;
import ru.drsk.progserega.inspectionsheet.ui.presenters.InspectStationPresenter;

public class InspectStationEquipment extends AppCompatActivity
        implements InspectStationEquipmentContract.View {

    private InspectStationEquipmentContract.Presenter presenter;
    private InspectionSheetApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect_station_equipment);

        this.application = (InspectionSheetApplication) this.getApplication();
        this.presenter = new InspectStationEquipmentPresenter(this, application);

        setTitle("Осмотр ПС ТП <NAME>");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //initAddCommonPhotoBtn();
        //initInspectionList();
        //initCommonPhotoList();


        this.presenter.onViewCreated();
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

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
