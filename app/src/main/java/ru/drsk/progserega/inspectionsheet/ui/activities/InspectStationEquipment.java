package ru.drsk.progserega.inspectionsheet.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.ui.adapters.InspectionAdapter;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.InspectStationEquipmentContract;
import ru.drsk.progserega.inspectionsheet.ui.presenters.InspectStationEquipmentPresenter;

public class InspectStationEquipment extends AppCompatActivity
        implements InspectStationEquipmentContract.View {

    private InspectStationEquipmentContract.Presenter presenter;
    private InspectionSheetApplication application;
    private InspectionAdapter inspectionAdapter;
    private ListView inspectionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect_station_equipment);

        this.application = (InspectionSheetApplication) this.getApplication();
        this.presenter = new InspectStationEquipmentPresenter(this, application);

        setTitle("Осмотр <NAME>");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //initAddCommonPhotoBtn();
        initInspectionList();
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

    private void initInspectionList() {

        inspectionAdapter = new InspectionAdapter(this, new ArrayList<>(), (inspectionItem, photo, position) -> {
            //TODO
           // presenter.onInspectionPhotoClicked(inspectionItem, position);
        });
        inspectionList = (ListView) findViewById(R.id.inspect_station_equipment__inspection);
        inspectionList.setAdapter(inspectionAdapter);

        AdapterView.OnItemClickListener itemClickListener = (list, itemView, position, id) -> {
            //onListItemClick(list, itemView, position, id);
            //presenter.onInspectionsListItemClick(position);
        };
        inspectionList.setOnItemClickListener(itemClickListener);
    }

    @Override
    public void setEquipmentName(String name) {
        setTitle("Осмотр "+name);

        TextView equipmentName = (TextView) findViewById(R.id.inspect_station_equipment__name);
        equipmentName.setText(name);

    }

    @Override
    public void setInspection(List< InspectionItem > inspections) {
        inspectionAdapter.setInspectionItems(inspections);
        inspectionAdapter.notifyDataSetChanged();
        justifyListViewHeightBasedOnChildren(inspectionList);
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    public static void justifyListViewHeightBasedOnChildren(ListView listView) {

        InspectionAdapter adapter = (InspectionAdapter) listView.getAdapter();

        if (adapter == null) {
            return;
        }
        ViewGroup vg = listView;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(par);
        listView.requestLayout();
    }
}
