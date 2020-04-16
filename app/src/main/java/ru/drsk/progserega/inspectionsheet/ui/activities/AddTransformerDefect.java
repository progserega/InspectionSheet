package ru.drsk.progserega.inspectionsheet.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.ui.adapters.ImageAdapter;
import ru.drsk.progserega.inspectionsheet.utility.ButtonUtils;
import ru.drsk.progserega.inspectionsheet.utility.MetricsUtils;
import ru.drsk.progserega.inspectionsheet.utility.PhotoUtility;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItemResult;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;
import ru.drsk.progserega.inspectionsheet.services.PhotoFullscreenManager;

import static ru.drsk.progserega.inspectionsheet.ui.activities.FullscreenImageActivity.IMAGE_IDX;

public class AddTransformerDefect extends AppCompatActivity
    implements PhotoUtility.ChoosedListener {

    public static final String DEFFECT_NAME = "deffect_name";

    private String userChooseTask;
    private InspectionSheetApplication application;

    private ImageAdapter imageAdapter;
    private InspectionItemResult deffect;

    private List<InspectionPhoto> inspectionPhotos;

    private TextView deffectDescription;

    private DeffectValuesView valuesView;

    private DeffectValuesView subValuesView;

    private PhotoUtility photoUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_defect);

        //  this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        ButtonUtils.initSaveBtnImg((ImageButton) findViewById(R.id.save_btn));

        this.application = (InspectionSheetApplication) this.getApplication();
        Intent intent = getIntent();
        String inspectionName = (String) intent.getStringExtra(DEFFECT_NAME);

        TextView deffectNameTextView = (TextView) findViewById(R.id.add_defect_inspection_name);
        deffectNameTextView.setText(inspectionName);

        deffect = application.getCurrentDeffect();

        deffectDescription = (TextView) findViewById(R.id.add_defect_description);
        deffectDescription.setText(deffect.getComment());

        if (deffect.getPossibleResult() != null) {
            //   addDinamicContent((LinearLayout) findViewById(R.id.add_defect_result_layout), deffect.getPossibleResult());;
            valuesView = new DeffectValuesView(
                    (LinearLayout) findViewById(R.id.add_defect_result_layout),
                    deffect.getPossibleResult(),
                    deffect.getValues(),
                    this, new DeffectValuesView.OnValueChangeListener() {
                @Override
                public void valuesChange(List<String> values) {

                }
            });
            valuesView.build();
        }

        if (deffect.getPossibleSubresult() != null) {
            //addDinamicContent((LinearLayout) findViewById(R.id.add_defect_subresult_layout), deffect.getPossibleSubresult());
            subValuesView = new DeffectValuesView(
                    (LinearLayout) findViewById(R.id.add_defect_result_layout),
                    deffect.getPossibleSubresult(),
                    deffect.getSubValues(),
                    this, new DeffectValuesView.OnValueChangeListener() {
                @Override
                public void valuesChange(List<String> values) {

                }
            });
            subValuesView.build();
        }

        ExpandableHeightGridView gridview = (ExpandableHeightGridView) findViewById(R.id.add_defect_photos);
        gridview.setExpanded(true);

//        float scalefactor = getResources().getDisplayMetrics().density;
//        int imageWidth = (int) (115 * scalefactor);
        int imageWidth = MetricsUtils.dpToPx(115, this);

        saveDeffectPhotos(deffect.getPhotos());

        imageAdapter = new ImageAdapter(this, inspectionPhotos, imageWidth);
        gridview.setAdapter(imageAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                showFullscreenPhoto(position);
            }
        });


        photoUtility = new PhotoUtility(this, this);
    }

    private void saveDeffectPhotos(List<InspectionPhoto> photos) {
        inspectionPhotos = new ArrayList<>();
        for (InspectionPhoto photo : photos) {
            inspectionPhotos.add(photo);
        }

        //inspectionPhotos =  photos;
    }

    public void onAddPhotoBtnClick(View view) {
        photoUtility.showPhotoDialog();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if(requestCode == PhotoUtility.REQUEST_CAMERA || requestCode == PhotoUtility.SELECT_FILE) {
            photoUtility.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void onSaveBtnPress(View view) {

        saveData();

        Intent returnIntent = getIntent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private  void saveData(){
        deffect.setPhotos(inspectionPhotos);

        deffect.setComment(deffectDescription.getText().toString());


        if (valuesView != null) {
            deffect.getValues().clear();
            deffect.getValues().addAll(valuesView.getResult());
        }

        if (subValuesView != null) {
            deffect.getSubValues().clear();
            deffect.getSubValues().addAll(subValuesView.getResult());
        }

    }

    private void showFullscreenPhoto(int position) {
        Intent intent = new Intent(AddTransformerDefect.this, FullscreenImageActivity.class);
        intent.putExtra(IMAGE_IDX, position);
        //application.setPhotosForFullscreen(inspectionPhotos);

        application.getPhotoFullscreenManager().setPhotos(inspectionPhotos);
        application.getPhotoFullscreenManager().setPhotoOwner(PhotoFullscreenManager.INSPECTION_ITEM_PHOTO);
        application.getPhotoFullscreenManager().setDeletePhotoCompleteListener(new PhotoFullscreenManager.DeletePhotoCompleteListener() {
            @Override
            public void onPhotoDeleted() {
                imageAdapter.notifyDataSetChanged();
            }
        });
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        photoUtility.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void onImageTaken(String photoPath) {

        inspectionPhotos.add(new InspectionPhoto(0, photoPath, this));
        imageAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("image_path", photoUtility.getmCurrentPhotoPath());
       // saveData();
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedState){
        super.onRestoreInstanceState(savedState);

        photoUtility.setmCurrentPhotoPath(savedState.getString("image_path"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
