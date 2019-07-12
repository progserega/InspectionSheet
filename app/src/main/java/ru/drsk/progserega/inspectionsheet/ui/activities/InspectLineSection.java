package ru.drsk.progserega.inspectionsheet.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineSectionDeffect;
import ru.drsk.progserega.inspectionsheet.services.PhotoFullscreenManager;
import ru.drsk.progserega.inspectionsheet.ui.adapters.HorizontalPhotoListAdapter;
import ru.drsk.progserega.inspectionsheet.ui.adapters.LineSectionDeffectsListAdapter;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.InspectLineSectionContract;
import ru.drsk.progserega.inspectionsheet.ui.presenters.InspectLineSectionPresenter;
import ru.drsk.progserega.inspectionsheet.utility.PhotoUtility;

import static ru.drsk.progserega.inspectionsheet.ui.activities.FullscreenImageActivity.IMAGE_IDX;
import static ru.drsk.progserega.inspectionsheet.ui.activities.InspectLineTower.NEXT_TOWER;

public class InspectLineSection extends AppCompatActivity implements
        InspectLineSectionContract.View, PhotoUtility.ChoosedListener {
    private InspectionSheetApplication application;
    private InspectLineSectionContract.Presenter presenter;
    private PhotoUtility photoUtility;

    private ArrayAdapter materialsAdapter;
    private Spinner materialsSpinner;

    private ListView inspectionList;
    private LineSectionDeffectsListAdapter deffectsListAdapter;
    private HorizontalPhotoListAdapter deffectsPhotoListAdapter;

    public static final String NEXT_SECTION = "next_section";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect_line_section);

        this.application = (InspectionSheetApplication) this.getApplication();
        this.presenter = new InspectLineSectionPresenter(this, application);


        setTitle("Осмотр пролета");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initMaterialSpinner();
        initInspectionItemsList();
        initAddPhotoBtnImg();
        initPhotoList();

        photoUtility = new PhotoUtility(this, this);

        Intent intent = getIntent();
        Long nextSection = 0l;
        if (intent.hasExtra(NEXT_SECTION)) {
            nextSection = (Long) intent.getSerializableExtra(NEXT_SECTION);
        }

        presenter.onViewCreated(nextSection);

    }

    private void initMaterialSpinner() {
        materialsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new ArrayList<String>());
        materialsAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        materialsSpinner = (Spinner) findViewById(R.id.inspection_line_section_materials_spinner);
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

    private void initInspectionItemsList() {
        deffectsListAdapter = new LineSectionDeffectsListAdapter(this);
        inspectionList = (ListView) findViewById(R.id.inspect_line_section_deffects_list);
        inspectionList.setAdapter(deffectsListAdapter);
        deffectsListAdapter.setSelectionListener(new LineSectionDeffectsListAdapter.IDeffectSelectionListener() {
            @Override
            public void onDeffectSelectionChange(int position, boolean isSelect) {
                presenter.onDeffectSelectionChange(position, isSelect);
            }
        });
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

    private void initAddPhotoBtnImg() {
        ImageButton addPhotoBtn = (ImageButton) findViewById(R.id.add_line_section_deffect_photo_btn);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            addPhotoBtn.setImageResource(R.drawable.ic_baseline_photo_camera_24px);
        } else {
            /* старые версии не поддерживают векторные рисунки */
            addPhotoBtn.setImageResource(R.drawable.ic_camera_png);
        }
        addPhotoBtn.invalidate();
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

    public void onNextBtnClick(View view) {
        presenter.nextButtonPressed();
    }

    public void onFinishBtnClick(View view) {

        presenter.finishButtonPressed();

        Intent intent = new Intent(this, InspectLineFinish.class);
        startActivity(intent);

    }

    @Override
    public void setDeffectsList(List<LineSectionDeffect> sectionDeffects) {
        deffectsListAdapter.setDeffects(sectionDeffects);
        deffectsListAdapter.notifyDataSetChanged();
        // justifyListViewHeightBasedOnChildren(inspectionList, this);
    }

    @Override
    public void setSectionNumber(String number) {
        EditText numberView = (EditText) findViewById(R.id.inspect_line_section_number);
        numberView.setText(number);
    }

    @Override
    public void gotoNextTowerInspection(long nextTowerUniqId) {
        Intent intent = new Intent(this, InspectLineTower.class);
        intent.putExtra(NEXT_TOWER, nextTowerUniqId);
        startActivity(intent);
    }

    @Override
    public void setMaterialsSpinnerData(List<String> materials, int sel) {
        materialsAdapter.clear();
        materialsAdapter.addAll(materials);
        materialsAdapter.notifyDataSetChanged();
        materialsSpinner.setSelection(sel);
    }

    @Override
    public void setComment(String comment) {
        EditText commentView = (EditText) findViewById(R.id.inspect_line_section_comment);
        commentView.setText(comment);
    }

    @Override
    public String getComment() {
        EditText commentView = (EditText) findViewById(R.id.inspect_line_section_comment);
        return commentView.getText().toString();

    }

    @Override
    public void setInspectionPhotos(List<InspectionPhoto> photos) {
        deffectsPhotoListAdapter.setItems(photos);
        deffectsPhotoListAdapter.notifyDataSetChanged();
    }

    public void onAddLineSectionPhotoBtnClick(View view) {
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


    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

}
