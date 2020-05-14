package ru.drsk.progserega.inspectionsheet.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.inspections.IStationInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItemResult;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItemType;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TransformerInspection;
import ru.drsk.progserega.inspectionsheet.services.PhotoFullscreenManager;
import ru.drsk.progserega.inspectionsheet.ui.adapters.HorizontalPhotoListAdapter;
import ru.drsk.progserega.inspectionsheet.ui.adapters.InspectionAdapter;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.InspectStationContract;
import ru.drsk.progserega.inspectionsheet.ui.presenters.InspectStationPresenter;
import ru.drsk.progserega.inspectionsheet.utility.PhotoUtility;

import static ru.drsk.progserega.inspectionsheet.ui.activities.AddTransformerDefect.DEFFECT_NAME;
import static ru.drsk.progserega.inspectionsheet.ui.activities.FullscreenImageActivity.IMAGE_IDX;
import static ru.drsk.progserega.inspectionsheet.ui.activities.InspectLineSection.NEXT_SECTION;
import static ru.drsk.progserega.inspectionsheet.ui.activities.InspectTransformer.GET_DEFFECT_VALUE_REQUEST;

public class InspectStation extends AppCompatActivity implements InspectStationContract.View {

    private InspectionSheetApplication application;
    private InspectStationContract.Presenter presenter;

    private InspectionAdapter inspectionAdapter;
    private ListView transfInspectionList;

    private IStationInspection substationInspection;

    private PhotoUtility photoUtility;

    private HorizontalPhotoListAdapter commonPhotoListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect_station);

        this.application = (InspectionSheetApplication) this.getApplication();
        this.presenter = new InspectStationPresenter(this, application);

        setTitle("Осмотр ПС ТП <NAME>");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initAddCommonPhotoBtn();
        initInspectionList();
        initCommonPhotoList();


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

    private void initAddCommonPhotoBtn(){
        initAddPhotoBtnImg();

        Context context = this;
        WeakReference<Context> contextWeakReference = new WeakReference<>(this);
        photoUtility = new PhotoUtility(this, new PhotoUtility.ChoosedListener() {
            @Override
            public void onImageTaken(String photoPath) {
                presenter.onCommonPhotoTaken(photoPath);
                commonPhotoListAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initAddPhotoBtnImg() {
        ImageButton addPhotoBtn = (ImageButton) findViewById(R.id.inspect_station_add_common_photo);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            addPhotoBtn.setImageResource(R.drawable.ic_baseline_photo_camera_24px);
        } else {
            /* старые версии не поддерживают векторные рисунки */
            addPhotoBtn.setImageResource(R.drawable.ic_camera_png);
        }
        addPhotoBtn.invalidate();
    }

    private void initInspectionList() {

        inspectionAdapter = new InspectionAdapter(this, new ArrayList<>(), (inspectionItem, photo, position) -> {
            //TODO
            presenter.onInspectionPhotoClicked(inspectionItem, position);
        });
        transfInspectionList = (ListView) findViewById(R.id.inspection_transformator_list);
        transfInspectionList.setAdapter(inspectionAdapter);

        AdapterView.OnItemClickListener itemClickListener = (list, itemView, position, id) -> {
            //onListItemClick(list, itemView, position, id);
            presenter.onInspectionsListItemClick(position);
        };
        transfInspectionList.setOnItemClickListener(itemClickListener);
    }

    private void initCommonPhotoList(){

        RecyclerView list = (RecyclerView) findViewById(R.id.inspect_station_common_photos);
        list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        commonPhotoListAdapter = new HorizontalPhotoListAdapter(new ArrayList<InspectionPhoto>(), new HorizontalPhotoListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(InspectionPhoto photo, int position) {
                presenter.onCommonPhotoClicked(position);
            }
        });
        list.setAdapter(commonPhotoListAdapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        photoUtility.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void setInspection(List<InspectionItem> inspections) {
        inspectionAdapter.setInspectionItems(inspections);
        inspectionAdapter.notifyDataSetChanged();
        justifyListViewHeightBasedOnChildren(transfInspectionList);
    }

    @Override
    public void startEditInspectionGroupActivity(InspectionItem currentInspectionItem, List<InspectionItem> group) {
        application.setCurrentInspectionItem(currentInspectionItem);
        application.setInspectionItemsGroup(group);

        Intent intent = new Intent(this, GroupAddTransfrmerDeffect.class);
        startActivityForResult(intent, GET_DEFFECT_VALUE_REQUEST);
    }

    @Override
    public void startEditInspectionActivity(InspectionItem currentInspectionItem) {
        InspectionItemResult inspectionItemResult = currentInspectionItem.getResult();

        application.setCurrentDeffect(inspectionItemResult);

        Intent intent = new Intent(this, AddTransformerDefect.class);
        intent.putExtra(DEFFECT_NAME, currentInspectionItem.getName());
        startActivityForResult(intent, GET_DEFFECT_VALUE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GET_DEFFECT_VALUE_REQUEST && resultCode == Activity.RESULT_OK){
             //Toast.makeText(this, "EDIT DEFFECTS DONE!", Toast.LENGTH_LONG).show();
            presenter.onInspectionValueEdited();
        }

        inspectionAdapter.notifyDataSetChanged();

        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if(requestCode == PhotoUtility.REQUEST_CAMERA || requestCode == PhotoUtility.SELECT_FILE) {
             photoUtility.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void setStationName(String name) {
        setTitle("Осмотр "+name);

        TextView stationName = (TextView) findViewById(R.id.inspect_station_name);
        stationName.setText(name);

    }

    public void onAddCommonPhotoBtnClick(View view){
        photoUtility.showPhotoDialog();
    }


    @Override
    public void showCommonPhotoFullscreen(int position, List<InspectionPhoto> photos) {
        application.getPhotoFullscreenManager().setPhotos(photos);
        application.getPhotoFullscreenManager().setPhotoOwner(PhotoFullscreenManager.TRANSFORMER_PHOTO);
        application.getPhotoFullscreenManager().setDeletePhotoCompleteListener(new PhotoFullscreenManager.DeletePhotoCompleteListener() {
            @Override
            public void onPhotoDeleted() {
                commonPhotoListAdapter.notifyDataSetChanged();
            }
        });

        //application.setPhotosForFullscreen(photos);
        Intent intent = new Intent(this, FullscreenImageActivity.class);
        intent.putExtra(IMAGE_IDX, position);
        startActivity(intent);
    }

    @Override
    public void showInspectionPhotoFullcreen(int position, List<InspectionPhoto> photos){
        Intent intent = new Intent(this, FullscreenImageActivity.class);
        intent.putExtra(IMAGE_IDX, position);
        //application.setPhotosForFullscreen(inspectionItem.getResult().getPhotos());
        application.getPhotoFullscreenManager().setPhotos(photos);
        application.getPhotoFullscreenManager().setPhotoOwner(PhotoFullscreenManager.INSPECTION_ITEM_PHOTO);
        application.getPhotoFullscreenManager().setDeletePhotoCompleteListener(new PhotoFullscreenManager.DeletePhotoCompleteListener() {
            @Override
            public void onPhotoDeleted() {
                inspectionAdapter.notifyDataSetChanged();
            }
        });
        startActivity(intent);
    }

    @Override
    public void setCommonPhotos(List<InspectionPhoto> photos){
        commonPhotoListAdapter.setItems(photos);
        commonPhotoListAdapter.notifyDataSetChanged();
    }

    public void onGotoEquipmentBtnClick(View view) {
        presenter.onGotoEquipmentBtnClicked();
    }

    @Override
    public void startSelectEquipmentActivity() {
        Intent intent = new Intent(this, StationEquipment.class);
        //intent.putExtra(NEXT_SECTION, nextSectionId);
        startActivity(intent);
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

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }


}
