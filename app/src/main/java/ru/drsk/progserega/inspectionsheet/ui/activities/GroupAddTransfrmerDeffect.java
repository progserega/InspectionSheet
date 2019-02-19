package ru.drsk.progserega.inspectionsheet.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.activities.utility.ButtonUtils;
import ru.drsk.progserega.inspectionsheet.activities.utility.PhotoUtility;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.ui.adapters.TransformerDeffectGroupListAdapter;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.GroupAddTransfDeffectContract;
import ru.drsk.progserega.inspectionsheet.ui.presenters.GroupAddTransfDeffectPresenter;

import static ru.drsk.progserega.inspectionsheet.ui.activities.FullscreenImageActivity.IMAGE_IDX;

public class GroupAddTransfrmerDeffect extends Activity implements
        TransformerDeffectGroupListAdapter.photoEventsListener,
        PhotoUtility.ChoosedListener,
        GroupAddTransfDeffectContract.View {

    private InspectionSheetApplication application;
    private PhotoUtility photoUtility;

    private TransformerDeffectGroupListAdapter groupListAdapter;
    private GroupAddTransfDeffectContract.Presenter presenter;
    private ListView deffectsList;

    private int currentDeffectPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_add_transfrmer_deffect);

        this.application = (InspectionSheetApplication) this.getApplication();

        this.presenter = new GroupAddTransfDeffectPresenter(this, this.application);

        presenter.initViewData();

        photoUtility = new PhotoUtility(this, this);

        ButtonUtils.initSaveBtnImg((ImageButton) findViewById(R.id.tower_deffect_group_save_btn));
    }

    @Override
    public void setHeaderNumber(String number) {
        TextView headerNum = (TextView) findViewById(R.id.transf_inspection_header_number);
        headerNum.setText(number);
    }

    @Override
    public void setHeaderTitle(String title) {
        TextView headerTitle = (TextView) findViewById(R.id.transf_inspection_header_title);
        headerTitle.setText(title);
    }

    public void initListAdapter(List<InspectionItem> inspectionItems) {
        groupListAdapter = new TransformerDeffectGroupListAdapter(this, inspectionItems, this);
        deffectsList = (ListView) findViewById(R.id.deffect_group_list);
        deffectsList.setAdapter(groupListAdapter);
    }


    @Override
    public void addPhotoBtnPressed(int deffectPosition) {
        currentDeffectPosition = deffectPosition;
        showSelectPhotoDialog();
    }

    @Override
    public void showSelectPhotoDialog() {
        photoUtility.showPhotoDialog();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        photoUtility.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        photoUtility.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void onImageTaken(String photoPath) {
        groupListAdapter.addPhoto(currentDeffectPosition, photoPath);
        groupListAdapter.notifyDataSetChanged();
    }

    @Override
    public void refreshDeffectsList() {
        groupListAdapter.notifyDataSetChanged();
    }


    public void onSaveBtnPress(View view) {

        presenter.onSaveBtnPressed(
                groupListAdapter.getValues(),
                groupListAdapter.getSubValues(),
                groupListAdapter.getComments(),
                groupListAdapter.getPhotos()
        );
    }

    @Override
    public void finishView() {
        Intent returnIntent = getIntent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void photoItemClick(int position, List<InspectionPhoto> photos) {
        application.setPhotosForFullscreen(photos);
        Intent intent = new Intent(this, FullscreenImageActivity.class);
        intent.putExtra(IMAGE_IDX, position);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }


}
