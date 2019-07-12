package ru.drsk.progserega.inspectionsheet.ui.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TowerDeffect;
import ru.drsk.progserega.inspectionsheet.services.PhotoFullscreenManager;
import ru.drsk.progserega.inspectionsheet.ui.adapters.HorizontalPhotoListAdapter;
import ru.drsk.progserega.inspectionsheet.entities.Point;
import ru.drsk.progserega.inspectionsheet.entities.Tower;
import ru.drsk.progserega.inspectionsheet.services.ILocationChangeListener;
import ru.drsk.progserega.inspectionsheet.ui.adapters.LineTowerDeffectsListAdapter;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.InspectLineTowerContract;
import ru.drsk.progserega.inspectionsheet.ui.presenters.InspectLineTowerPresenter;
import ru.drsk.progserega.inspectionsheet.utility.PhotoUtility;

import static ru.drsk.progserega.inspectionsheet.ui.activities.FullscreenImageActivity.IMAGE_IDX;
import static ru.drsk.progserega.inspectionsheet.ui.activities.InspectLineSection.NEXT_SECTION;

public class InspectLineTower extends ActivityWithGPS implements InspectLineTowerContract.View,
        SelectTowerDialog.ISelectTowerListener,
        PhotoUtility.ChoosedListener {

    public static final String NEXT_TOWER = "next_tower";

    private InspectionSheetApplication application;
    private InspectLineTowerContract.Presenter presenter;

    private LineTowerDeffectsListAdapter deffectsListAdapter;
    private ListView inspectionList;

    private SelectTowerDialog selectTowerDialog;

    private Switch GPSSwitch;

    private ArrayAdapter materialsAdapter;
    private Spinner materialsSpinner;

    private ArrayAdapter towerTypesAdapter;
    private Spinner towerTypesSpinner;

    private PhotoUtility photoUtility;
    private HorizontalPhotoListAdapter deffectsPhotoListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect_line_tower);

        this.application = (InspectionSheetApplication) this.getApplication();
        this.presenter = new InspectLineTowerPresenter(this, application);
        this.application.getLocationService().setLocationChangeListener(new WeakReference<ILocationChangeListener>(this));

        setTitle("Осмотр опоры");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initSelectTowerBtn();
        initSwitchGPSBtn();
        initMaterialSpinner();
        initTowerTypeSpinner();

        initInspectionItemsList();
        initAddPhotoBtnImg();
        initPhotoList();


        photoUtility = new PhotoUtility(this, this);


        Intent intent = getIntent();

        Long nextTower = 0l;
        if (intent.hasExtra(NEXT_TOWER)) {
            nextTower = (Long) intent.getSerializableExtra(NEXT_TOWER);
        }

        presenter.onViewCreated(nextTower);
    }

    private void initSwitchGPSBtn() {
        GPSSwitch = (Switch) findViewById(R.id.inspect_line_tower_switch_gps);
        GPSSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (checkGPSPermission()) {
                    presenter.onGPSSwitchChange(isChecked);
                } else {
                    requestGPSAccess();
                }
            }
        });
    }

    @Override
    protected void onGPSRequestGaranted() {
        presenter.onGPSSwitchChange(GPSSwitch.isChecked());
    }

    @Override
    protected void onGPSRequestDenied() {
        presenter.onGPSSwitchChange(false);
        GPSSwitch.setChecked(false);
    }

    @Override
    public void onLocationChange(Point location) {
        if (GPSSwitch.isChecked()) {
            presenter.onGPSLocationChange();
        }
    }

    private void initInspectionItemsList() {
        deffectsListAdapter = new LineTowerDeffectsListAdapter(this);
        inspectionList = (ListView) findViewById(R.id.inspect_line_tower_deffects_list);
        inspectionList.setAdapter(deffectsListAdapter);
        deffectsListAdapter.setSelectionListener(new LineTowerDeffectsListAdapter.IDeffectSelectionListener() {
            @Override
            public void onDeffectSelectionChange(int position, boolean isSelect) {
                presenter.onDeffectSelectionChange(position, isSelect);
            }
        });
    }

    private void initSelectTowerBtn() {
        TextView textView = (TextView) findViewById(R.id.inspect_line_select_tower_btn);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onSelectTowerBtnClick();
            }
        });
    }

    private void initMaterialSpinner() {
        materialsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new ArrayList<String>());
        materialsAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        materialsSpinner = (Spinner) findViewById(R.id.materials_spinner);
        materialsSpinner.setAdapter(materialsAdapter);
        materialsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                presenter.onMaterialSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    private void initTowerTypeSpinner() {
        towerTypesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new ArrayList<String>());
        towerTypesAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        towerTypesSpinner = (Spinner) findViewById(R.id.tower_type_spinner);
        towerTypesSpinner.setAdapter(towerTypesAdapter);
        towerTypesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                presenter.onTowerTypeSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    @Override
    public void setMaterialsSpinnerData(List<String> materials, int sel) {
        materialsAdapter.clear();
        materialsAdapter.addAll(materials);
        materialsAdapter.notifyDataSetChanged();
        materialsSpinner.setSelection(sel);
    }

    @Override
    public void setTowerTypesSpinnerData(List<String> towerTypes, int sel) {
        towerTypesAdapter.clear();
        towerTypesAdapter.addAll(towerTypes);
        towerTypesAdapter.notifyDataSetChanged();
        towerTypesSpinner.setSelection(sel);
    }

    @Override
    public void showSelectTowerDialog(List<Tower> towers) {
        FragmentManager fm = getSupportFragmentManager();
        if (selectTowerDialog == null) {
            selectTowerDialog = SelectTowerDialog.newInstance();
        }
        selectTowerDialog.setTowers(towers);

        selectTowerDialog.show(fm, "select_tower");

    }

    @Override
    public void setTowerNumber(String number) {
        EditText numberView = (EditText) findViewById(R.id.inspect_line_tower_number);
        numberView.setText(number);
    }

    private void initAddPhotoBtnImg() {
        ImageButton addPhotoBtn = (ImageButton) findViewById(R.id.add_line_tower_deffect_photo_btn);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            addPhotoBtn.setImageResource(R.drawable.ic_baseline_photo_camera_24px);
        } else {
            /* старые версии не поддерживают векторные рисунки */
            addPhotoBtn.setImageResource(R.drawable.ic_camera_png);
        }
        addPhotoBtn.invalidate();
    }

    @Override
    public void setDeffectsList(List<TowerDeffect> towerDeffects) {
        deffectsListAdapter.setDeffects(towerDeffects);
        deffectsListAdapter.notifyDataSetChanged();
        //justifyListViewHeightBasedOnChildren(inspectionList, this);
    }

    @Override
    public void onSelectTower(int position) {
        presenter.onTowerSelected(position);
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
    public void showNextSectionSelectorDialog(final String[] selectionItems) {
        // Initializing a new alert dialog
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Set the alert dialog title
        builder.setTitle("Перейти к пролету");

        final Map<Integer, Integer> selectedPos = new HashMap<>();
        selectedPos.put(0, -1);

        // Set a single choice items list for alert dialog
        builder.setSingleChoiceItems(
                selectionItems, // Items list
                -1, // Index of checked item (-1 = no selection)
                new DialogInterface.OnClickListener() // Item click listener
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        selectedPos.put(0, i);
                    }
                });

        // Set the alert dialog positive button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int pos = selectedPos.get(0);
                if (pos != -1) {
                    presenter.onNextSectionSelected(pos);
                }
            }
        });

        // Create the alert dialog
        AlertDialog dialog = builder.create();

        // Finally, display the alert dialog
        dialog.show();
    }

    @Override
    public void showEndOfLineDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Конец линии или отпайки\nВыберите другую опору или завершите осмотр")
                .setTitle("Конец линии");

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void gotoSectionInspection(long nextSectionId) {
        Intent intent = new Intent(this, InspectLineSection.class);
        intent.putExtra(NEXT_SECTION, nextSectionId);
        startActivity(intent);
    }


    @Override
    public void setComment(String comment) {
        EditText commentView = (EditText) findViewById(R.id.inspect_line_tower_comment);
        commentView.setText(comment);
    }

    @Override
    public String getComment() {
        EditText commentView = (EditText) findViewById(R.id.inspect_line_tower_comment);
        return commentView.getText().toString();
    }

    public void onNextBtnClick(View view) {
        presenter.nextButtonPressed();
        //Toast.makeText(this, "Данные сохранены!", Toast.LENGTH_SHORT).show();
    }


    private void initPhotoList() {
        RecyclerView list = (RecyclerView) findViewById(R.id.transformer_photos);
        list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        deffectsPhotoListAdapter = new HorizontalPhotoListAdapter(new ArrayList<InspectionPhoto>(), new HorizontalPhotoListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(InspectionPhoto photo, int position) {
                deffectsPhotoItemClick(position, deffectsPhotoListAdapter.getItems());
            }
        });
        list.setAdapter(deffectsPhotoListAdapter);
    }

    @Override
    public void setTowerPhotos(List<InspectionPhoto> photos) {
        deffectsPhotoListAdapter.setItems(photos);
        deffectsPhotoListAdapter.notifyDataSetChanged();
    }

    public void onFinishBtnClick(View view) {
        presenter.finishButtonPressed();
        Intent intent = new Intent(this, InspectLineFinish.class);
        startActivity(intent);
    }

    public void onAddTowerPhotoBtnClick(View view) {
        photoUtility.showPhotoDialog();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == PhotoUtility.REQUEST_CAMERA || requestCode == PhotoUtility.SELECT_FILE) {
            photoUtility.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        photoUtility.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onImageTaken(String photoPath) {
        //  Toast.makeText(this, "выбрана фотография\n" + photoPath, Toast.LENGTH_SHORT).show();
        presenter.onImageTaken(photoPath);
        deffectsPhotoListAdapter.notifyDataSetChanged();
    }

    public void deffectsPhotoItemClick(int position, List<InspectionPhoto> photos) {
        application.getPhotoFullscreenManager().setPhotos(photos);
        application.getPhotoFullscreenManager().setPhotoOwner(PhotoFullscreenManager.LINE_INSPECTION_PHOTO);
        application.getPhotoFullscreenManager().setDeletePhotoCompleteListener(new PhotoFullscreenManager.DeletePhotoCompleteListener() {
            @Override
            public void onPhotoDeleted() {
                deffectsPhotoListAdapter.notifyDataSetChanged();
            }
        });

        //application.setPhotosForFullscreen(photos);
        Intent intent = new Intent(this, FullscreenImageActivity.class);
        intent.putExtra(IMAGE_IDX, position);
        startActivity(intent);
    }

    // работает криво если высота динамическая
//    public static void justifyListViewHeightBasedOnChildren(ListView listView, Context context) {
//
//        LineTowerDeffectsListAdapter adapter = (LineTowerDeffectsListAdapter) listView.getAdapter();
//
//        if (adapter == null) {
//            return;
//        }
//        ViewGroup vg = listView;
//        int totalHeight = 0;
//
//        int width = MetricsUtils.getDisplayWidthDp(context);
//
//        for (int i = 0; i < adapter.getCount(); i++) {
//            View listItem = adapter.getView(i, null, vg);
//            listItem.measure(0, 0);
//            totalHeight += listItem.getMeasuredHeight();
//        }
//
//        ViewGroup.LayoutParams par = listView.getLayoutParams();
//        par.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
//        listView.setLayoutParams(par);
//        listView.requestLayout();
//
//
//    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
