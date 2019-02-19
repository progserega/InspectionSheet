package ru.drsk.progserega.inspectionsheet.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.activities.utility.PhotoUtility;
import ru.drsk.progserega.inspectionsheet.entities.catalogs.TowerDeffectType;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TowerDeffect;
import ru.drsk.progserega.inspectionsheet.ui.adapters.TowerDeffectsListAdapter;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.TowerDeffectsContract;
import ru.drsk.progserega.inspectionsheet.ui.presenters.TowerDefectsPresenter;

public class TowerDeffectsActivity extends AppCompatActivity  implements TowerDeffectsContract.View,
        SelectTowerDeffectDialog.AddTowerDeffectListener,
        TowerDeffectsListAdapter.AddPhotoBtnListener,
        PhotoUtility.ChoosedListener {

    private TowerDeffectsContract.Presenter presenter;
    private SelectTowerDeffectDialog selectTowerDeffectDialog;
    private TowerDeffectsListAdapter deffectsListAdapter;

    private PhotoUtility photoUtility;
    private TowerDeffect currentDeffect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tower_deffects);


        ImageButton imageButton = (ImageButton) findViewById(R.id.tower_deffect_save_btn);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            imageButton.setImageResource(R.drawable.ic_baseline_save_24px);
        } else {
            /* старые версии не поддерживают векторные рисунки */
            imageButton.setImageResource(R.drawable.ic_save_balack_png);
        }
        imageButton.invalidate();

        presenter = new TowerDefectsPresenter(this, (InspectionSheetApplication) this.getApplication());



        deffectsListAdapter = new TowerDeffectsListAdapter(this, new ArrayList<TowerDeffect>(), this);
        ListView deffectsList = (ListView) findViewById(R.id.tower_deffects_list);
        deffectsList.setAdapter(deffectsListAdapter);

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> list, View itemView, int position, long id) {
               // onListItemClick(list, itemView, position, id);
            }
        };
        //transfInspectionList.setOnItemClickListener(itemClickListener);

        photoUtility = new PhotoUtility(this, this);



    }

    public void onSaveBtnPress(View view) {

    }


    public void onAddDeffectBtnPress(View view) {
        presenter.onAddDeffectBtnPress();
    }

    @Override
    public void showSelectDeffectDialog(List<TowerDeffectType> types) {
        FragmentManager fm = getSupportFragmentManager();
        if (selectTowerDeffectDialog == null) {
            selectTowerDeffectDialog = SelectTowerDeffectDialog.newInstance(types);
        }

        selectTowerDeffectDialog.show(fm, "select_tower_deffect_dialog");

    }

    @Override
    public void onAddDeffect(TowerDeffectType deffectType) {
        presenter.addDeffect(deffectType);
    }

    @Override
    public void updateDeffectsList(List<TowerDeffect> deffects) {
        deffectsListAdapter.setDeffects(deffects);
        deffectsListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        photoUtility.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void addPhotoBtnPressed(TowerDeffect deffect) {
        currentDeffect = deffect;
        photoUtility.showPhotoDialog();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        photoUtility.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void onImageTaken(String photoPath) {
        currentDeffect.addPhoto(new InspectionPhoto(0, photoPath, this));
        deffectsListAdapter.notifyDataSetChanged();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

}
