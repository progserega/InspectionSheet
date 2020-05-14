package ru.drsk.progserega.inspectionsheet.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.ui.adapters.HorizontalPhotoListAdapter;
import ru.drsk.progserega.inspectionsheet.services.PhotoFullscreenManager;
import ru.drsk.progserega.inspectionsheet.ui.adapters.InspectionAdapter;
import ru.drsk.progserega.inspectionsheet.ui.adapters.TransformerSpinnerAdapter;
import ru.drsk.progserega.inspectionsheet.utility.ButtonUtils;
import ru.drsk.progserega.inspectionsheet.utility.PhotoUtility;
import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.TransformerType;
import ru.drsk.progserega.inspectionsheet.entities.Transformer;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItemResult;
import ru.drsk.progserega.inspectionsheet.entities.inspections.IStationInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItemType;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TransformerInspection;
import ru.drsk.progserega.inspectionsheet.services.InspectionService;
import ru.drsk.progserega.inspectionsheet.storages.IInspectionStorage;
import ru.drsk.progserega.inspectionsheet.storages.ITransformerStorage;

import static ru.drsk.progserega.inspectionsheet.ui.activities.AddTransformerDefect.DEFFECT_NAME;
import static ru.drsk.progserega.inspectionsheet.ui.activities.FullscreenImageActivity.IMAGE_IDX;

public class InspectTransformer extends AppCompatActivity implements
        SelectTransformerDialog.AddTransformerListener,
        InspectionAdapter.OnItemPhotoClickListener,
        PhotoUtility.ChoosedListener    {

    static final int GET_DEFFECT_VALUE_REQUEST = 1001;

    private InspectionAdapter inspectionAdapter;
    private InspectionSheetApplication application;
    private ITransformerStorage transformerStorage;

    // private ArrayAdapter<String> transformerSpinnerAdapter;
    private TransformerSpinnerAdapter transformerSpinnerAdapter;
    private Spinner transformatorSpinner;
    private  ListView transfInspectionList;

    private IStationInspection substationInspection;

    //private List<TransformerInSlot> transformers;

    private List<TransformerInspection> transformerInspections;

    private SelectTransformerDialog selectTransformerDialog;

    private InspectionService inspectionService;

    private TransformerInspection currentInspection;

    private int year = 0;
    private int maxYear = Calendar.getInstance().get(Calendar.YEAR);

    private TextView transformerManufactureYear;
    private PhotoUtility photoUtility;
    private HorizontalPhotoListAdapter commonPhotoListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect_transformator);

        ButtonUtils.initSaveBtnImg((ImageButton) findViewById(R.id.inpsect_transformator_save_btn));

        this.application = (InspectionSheetApplication) this.getApplication();

        substationInspection = this.application.getCurrentStationInspection();
        inspectionService = this.application.getInspectionService();

        TextView substationNameText = (TextView) findViewById(R.id.inspection_transformator_substation);
        Equipment substation = substationInspection.getEquipment();
        substationNameText.setText(substation.getName());

        setTitle(substation.getName());
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        transformerStorage = this.application.getTransformerStorage();

        transformerInspections = substationInspection.getTransformerInspections();
//        if (transformerInspections == null) {
//            transformerInspections = inspectionService.getSubstationTransformersWithInspections(substation);
//            substationInspection.setInspection(transformerInspections);
//        }


        transformerSpinnerAdapter = new TransformerSpinnerAdapter(this, transformerInspections);
        transformatorSpinner = (Spinner) findViewById(R.id.select_transformator_spinner);
        transformatorSpinner.setAdapter(transformerSpinnerAdapter);
        transformatorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                onSelectTransormator(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });


        TransformerInspection inspection = new TransformerInspection(substation, null);
        inspectionAdapter = new InspectionAdapter(this, inspection.getInspectionItems(), this);
        transfInspectionList = (ListView) findViewById(R.id.inspection_transformator_list);
        transfInspectionList.setAdapter(inspectionAdapter);

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> list, View itemView, int position, long id) {
                onListItemClick(list, itemView, position, id);
            }
        };
        transfInspectionList.setOnItemClickListener(itemClickListener);

        transformerManufactureYear = (TextView) findViewById(R.id.inspection_transformer_manufacture_year);
        transformerManufactureYear.setText("");

        ImageButton addPhotoBtn = (ImageButton) findViewById(R.id.add_transformer_photo_btn);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            addPhotoBtn.setImageResource(R.drawable.ic_baseline_photo_camera_24px);
        } else {
            /* старые версии не поддерживают векторные рисунки */
            addPhotoBtn.setImageResource(R.drawable.ic_camera_png);
        }
        addPhotoBtn.invalidate();

        photoUtility = new PhotoUtility(this, this);

        RecyclerView list = (RecyclerView) findViewById(R.id.inspect_transformer_common_photos);
        list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        commonPhotoListAdapter = new HorizontalPhotoListAdapter(new ArrayList<InspectionPhoto>(), new HorizontalPhotoListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(InspectionPhoto photo, int position) {
                commonPhotoItemClick(position, currentInspection.getTransformator().getPhotoList());
            }
        });
        list.setAdapter(commonPhotoListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.transformer_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // получим идентификатор выбранного пункта меню
        int id = item.getItemId();
        // Операции для выбранного пункта меню
        switch (id) {
            case R.id.transformer_menu_add_t1:
                // Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
                onAddTramsformerMenuClick(1);
                return true;
            case R.id.transformer_menu_add_t2:
                // Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
                onAddTramsformerMenuClick(2);
                return true;
            case R.id.transformer_menu_add_t3:
                // Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
                onAddTramsformerMenuClick(3);
                return true;
            case R.id.transformer_menu_switch:
                onSwitchInspectionsMenuClick();

                return true;

            case android.R.id.home:
                onBackPressed();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }

    }


    private void onListItemClick(AdapterView<?> list, View v, int position, long id) {
        InspectionItem inspectionItem = (InspectionItem) inspectionAdapter.getItem(position);

        if (inspectionItem.getType().equals(InspectionItemType.HEADER)) {
            List<InspectionItem> allItems = inspectionAdapter.getInspectionItems();
            InspectionItem header = inspectionItem;
            List<InspectionItem> group = getInspectionGroup(header, allItems);
            application.setCurrentInspectionItem(header);
            application.setInspectionItemsGroup(group);

            Intent intent = new Intent(this, GroupAddTransfrmerDeffect.class);
            startActivityForResult(intent, GET_DEFFECT_VALUE_REQUEST);

        } else {
            InspectionItemResult inspectionItemResult = inspectionItem.getResult();

            application.setCurrentDeffect(inspectionItemResult);
            // Toast.makeText(this, inspectionItem.getName() + " selected!", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, AddTransformerDefect.class);
            intent.putExtra(DEFFECT_NAME, inspectionItem.getName());
            startActivityForResult(intent, GET_DEFFECT_VALUE_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        inspectionAdapter.notifyDataSetChanged();

        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        //Toast.makeText(this, "GET DEFFECT RESULT!!!", Toast.LENGTH_LONG).show();
        //transformatorInspectionAdapter.notifyDataSetChanged();

        if(requestCode == PhotoUtility.REQUEST_CAMERA || requestCode == PhotoUtility.SELECT_FILE) {
            photoUtility.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void onSelectTransormator(int position) {

        currentInspection = transformerInspections.get(position);
        //inspectionAdapter.setInspection(currentInspection);
        inspectionAdapter.setInspectionItems(currentInspection.getInspectionItems());
        inspectionAdapter.notifyDataSetChanged();
        justifyListViewHeightBasedOnChildren(transfInspectionList);

        setManufactureYear(currentInspection.getTransformator().getYear());

        setTransformerPhotos(currentInspection);
    }

    public void onSaveBtnPress(View view) {

        List<TransformerInspection> allInspections = substationInspection.getTransformerInspections();
        IInspectionStorage inspectionStorage = application.getInspectionStorage();
        float sum = 0;
        Date inspectionDate = new Date();
        for (TransformerInspection transformerInspection : allInspections) {
            transformerInspection.getTransformator().setInspectionDate(inspectionDate);
            inspectionStorage.saveInspection(transformerInspection);
            transformerInspection.setDone(true);

            sum += transformerInspection.calcInspectionPercent();
        }
        float middlePercent = sum / allInspections.size();

        TransformerInspection inspection = (TransformerInspection) transformatorSpinner.getSelectedItem();
        inspection.getSubstation().setInspectionDate(inspectionDate);
        inspection.getSubstation().setInspectionPercent(middlePercent);
        inspectionStorage.updateSubstationInspectionInfo(inspection);

        transformerSpinnerAdapter.notifyDataSetChanged();

        Toast.makeText(this, "Сохранено!", Toast.LENGTH_LONG).show();
    }


    public void onAddTramsformerMenuClick(int slot) {
        FragmentManager fm = getSupportFragmentManager();
        if (selectTransformerDialog == null) {
            selectTransformerDialog = SelectTransformerDialog.newInstance();
        }
        selectTransformerDialog.setSlot(slot);
        selectTransformerDialog.setType(substationInspection.getEquipment().getType());


        selectTransformerDialog.show(fm, "select_transformer");

    }

    @Override
    public void onAddTransformer(long transformerTypeId, int slot) {
        //Добавляем трансформатор к списку оборудования подстанции

        long insertedId = transformerStorage.addToSubstation(transformerTypeId, substationInspection.getEquipment(), slot);

        //Выбираем трансформатор
        TransformerType transformerType = transformerStorage.getById(transformerTypeId);
        Transformer transformer = new Transformer(insertedId, slot, transformerType);

        //Создаем новый объект для осмотра
        TransformerInspection inspection = new TransformerInspection(substationInspection.getEquipment(), transformer);
        inspection.setInspectionItems(inspectionService.loadInspectionTemplates(substationInspection.getEquipment().getType()));

        //Добавляем к списку осмотров
        substationInspection.getTransformerInspections().add(inspection);


        transformerSpinnerAdapter.notifyDataSetChanged();

    }

    @Override
    public void onItemPhotoClick(InspectionItem inspectionItem, InspectionPhoto photo, int position) {
        // Toast.makeText(this, "TAP ON PHOTO  "+ photo.getPath(), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, FullscreenImageActivity.class);
        intent.putExtra(IMAGE_IDX, position);
        //application.setPhotosForFullscreen(inspectionItem.getResult().getPhotos());
        application.getPhotoFullscreenManager().setPhotos(inspectionItem.getResult().getPhotos());
        application.getPhotoFullscreenManager().setPhotoOwner(PhotoFullscreenManager.INSPECTION_ITEM_PHOTO);
        application.getPhotoFullscreenManager().setDeletePhotoCompleteListener(new PhotoFullscreenManager.DeletePhotoCompleteListener() {
            @Override
            public void onPhotoDeleted() {
                inspectionAdapter.notifyDataSetChanged();
            }
        });
        startActivity(intent);
    }

    private List<InspectionItem> getInspectionGroup(InspectionItem header, List<InspectionItem> allItems) {
        List<InspectionItem> group = new ArrayList<>();
        for (InspectionItem item : allItems) {
            if (item.getParentId() == header.getValueId()) {
                group.add(item);
            }
        }
        return group;
    }

    private void onSwitchInspectionsMenuClick() {
        FragmentManager fm = getSupportFragmentManager();

        SwitchTransformerInspectionsDialog dialog = SwitchTransformerInspectionsDialog.newInstance(
                this.transformerInspections,
                new SwitchTransformerInspectionsDialog.AcceptListener() {
                    @Override
                    public void onAcceptBtnClick(long sourcePos, long destPos) {
                        //Toast.makeText(InspectTransformer.this, String.format("SWITCH transformers  %d <---> %d",sourcePos, destPos), Toast.LENGTH_LONG).show();
                        switchInspections(sourcePos, destPos);
                    }
                });
        dialog.show(fm, "switch_inspections");
    }

    private void switchInspections(long sourcePos, long destPos) {
        if (sourcePos == destPos) {
            return;
        }

        TransformerInspection sourceInspection = transformerInspections.get((int) sourcePos);
        TransformerInspection destInspection = transformerInspections.get((int) destPos);

        List<InspectionItem> tmp = sourceInspection.getInspectionItems();

        sourceInspection.setInspectionItems(destInspection.getInspectionItems());
        destInspection.setInspectionItems(tmp);


        transformerSpinnerAdapter.notifyDataSetChanged();
        inspectionAdapter.notifyDataSetChanged();
    }

    public static void justifyListViewHeightBasedOnChildren (ListView listView) {

        InspectionAdapter adapter = (InspectionAdapter)listView.getAdapter();

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

    public void onManufactureYearClick(View view){

        FragmentManager fm = getSupportFragmentManager();

        SelectYearDialog dialog = SelectYearDialog.newInstance(year, maxYear, new SelectYearDialog.SelectYearListener() {
                    @Override
                    public void onSelectYear(int year) {
                        currentInspection.getTransformator().setYear(year);
                        setManufactureYear(year);
                    }
                }
        );
        dialog.show(fm, "select_year");

    }

    void setManufactureYear(int year){
        this.year = year;

        if(year == 0){
            transformerManufactureYear.setText(Html.fromHtml("<u>    не задан    </u>"));
        }else {
            transformerManufactureYear.setText(Html.fromHtml("<u>" + year + "</u>"));
        }

    }
//java.lang.IllegalStateException: Could not find method onAddTransformerPhotoBtnClick(View) in a parent or ancestor Context for android:onClick attribute defined on view class android.support.v7.widget.AppCompatImageButton with id 'add_transformer_photo_btn'
    public void onAddTransformerPhotoBtnClick(View view){
        if(currentInspection == null){
            return;
        }
        photoUtility.showPhotoDialog();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        photoUtility.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void onImageTaken(String photoPath) {
        currentInspection.getTransformator().getPhotoList().add(new InspectionPhoto(0,photoPath, this));
        commonPhotoListAdapter.notifyDataSetChanged();
    }


    public void commonPhotoItemClick(int position, List<InspectionPhoto> photos) {
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

    private void setTransformerPhotos(TransformerInspection inspection){
        commonPhotoListAdapter.setItems(inspection.getTransformator().getPhotoList());
        commonPhotoListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("image_path_transformer", photoUtility.getmCurrentPhotoPath());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedState){
        super.onRestoreInstanceState(savedState);
        photoUtility.setmCurrentPhotoPath(savedState.getString("image_path_transformer"));
    }
}
