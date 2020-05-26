package ru.drsk.progserega.inspectionsheet.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
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
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;
import ru.drsk.progserega.inspectionsheet.services.PhotoFullscreenManager;
import ru.drsk.progserega.inspectionsheet.ui.adapters.InspectionAdapter;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.InspectStationEquipmentContract;
import ru.drsk.progserega.inspectionsheet.ui.presenters.InspectStationEquipmentPresenter;

import static ru.drsk.progserega.inspectionsheet.ui.activities.AddTransformerDefect.DEFFECT_NAME;
import static ru.drsk.progserega.inspectionsheet.ui.activities.FullscreenImageActivity.IMAGE_IDX;

public class InspectStationEquipment extends AppCompatActivity
        implements InspectStationEquipmentContract.View {

    private InspectStationEquipmentContract.Presenter presenter;
    private InspectionSheetApplication application;
    private InspectionAdapter inspectionAdapter;
    private ListView inspectionList;
    private TextView manufactureYearTextView;

    static final int GET_DEFFECT_VALUE_REQUEST = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect_station_equipment);

        this.application = (InspectionSheetApplication) this.getApplication();
        this.presenter = new InspectStationEquipmentPresenter(this, application);

        setTitle("Осмотр <NAME>");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initManufactureYearBtn();
        initInspectionList();

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

    private void initManufactureYearBtn() {
        manufactureYearTextView = (TextView) findViewById(R.id.inspect_station_equipment__manufacture_year);
        manufactureYearTextView.setText("");
    }

    private void initInspectionList() {

        inspectionAdapter = new InspectionAdapter(this, new ArrayList<>(), (inspectionItem, photo, position) -> {
            presenter.onInspectionPhotoClicked(inspectionItem, position);
        });
        inspectionList = (ListView) findViewById(R.id.inspect_station_equipment__inspection);
        inspectionList.setAdapter(inspectionAdapter);

        AdapterView.OnItemClickListener itemClickListener = (list, itemView, position, id) -> {
            presenter.onInspectionsListItemClick(position);
        };
        inspectionList.setOnItemClickListener(itemClickListener);
    }

    @Override
    public void setEquipmentName(String name) {
        setTitle("Осмотр " + name);

        TextView equipmentName = (TextView) findViewById(R.id.inspect_station_equipment__name);
        equipmentName.setText(name);

    }

    @Override
    public void setInspection(List<InspectionItem> inspections) {
        inspectionAdapter.setInspectionItems(inspections);
        inspectionAdapter.notifyDataSetChanged();
        justifyListViewHeightBasedOnChildren(inspectionList);
    }

    @Override
    public void startEditInspectionGroupActivity(InspectionItem currentInspectionItem, List<InspectionItem> group) {
        Intent intent = new Intent(this, GroupAddTransfrmerDeffect.class);
        startActivityForResult(intent, GET_DEFFECT_VALUE_REQUEST);
    }

    @Override
    public void startEditInspectionActivity(InspectionItem currentInspectionItem) {
        Intent intent = new Intent(this, AddTransformerDefect.class);
        intent.putExtra(DEFFECT_NAME, currentInspectionItem.getName());
        startActivityForResult(intent, GET_DEFFECT_VALUE_REQUEST);
    }

    @Override
    public void showInspectionPhotoFullcreen(int position, List<InspectionPhoto> photos) {
        Intent intent = new Intent(this, FullscreenImageActivity.class);
        intent.putExtra(IMAGE_IDX, position);
        //application.setPhotosForFullscreen(inspectionItem.getResult().getPhotos());
        application.getPhotoFullscreenManager().setPhotos(photos);
        application.getPhotoFullscreenManager().setPhotoOwner(PhotoFullscreenManager.INSPECTION_ITEM_PHOTO);
        application.getPhotoFullscreenManager().setDeletePhotoCompleteListener(new PhotoFullscreenManager.DeletePhotoCompleteListener() {
            @Override
            public void onPhotoDeleted(InspectionPhoto photo) {
                inspectionAdapter.notifyDataSetChanged();
            }
        });
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        inspectionAdapter.notifyDataSetChanged();

        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == GET_DEFFECT_VALUE_REQUEST) {
            //Toast.makeText(this, "EDIT DEFFECTS DONE!", Toast.LENGTH_LONG).show();
            presenter.onInspectionValueEdited();
        }

//        if(requestCode == PhotoUtility.REQUEST_CAMERA || requestCode == PhotoUtility.SELECT_FILE) {
//            photoUtility.onActivityResult(requestCode, resultCode, data);
//        }
    }

    public void onEquipmentManufactureYearClick(View view){
        presenter.onManufactureYearClick();
    }

    @Override
    public void showSelectYearDialog(int year, int maxYear){
        FragmentManager fm = getSupportFragmentManager();

        SelectYearDialog dialog = SelectYearDialog.newInstance(year, maxYear, new SelectYearDialog.SelectYearListener() {
                    @Override
                    public void onSelectYear(int year) {
                        presenter.onManufactureYearSelected(year);
                    }
                }
        );
        dialog.show(fm, "select_year");
    }

    @Override
    public void setManufactureYear(int year){
        if(year == 0){
            manufactureYearTextView.setText(Html.fromHtml("<u>    не задан    </u>"));
        }else {
            manufactureYearTextView.setText(Html.fromHtml("<u>" + year + "</u>"));
        }
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
