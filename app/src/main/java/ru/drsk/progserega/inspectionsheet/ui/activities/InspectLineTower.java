package ru.drsk.progserega.inspectionsheet.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TowerDeffect;
import ru.drsk.progserega.inspectionsheet.utility.MetricsUtils;
import ru.drsk.progserega.inspectionsheet.entities.Point;
import ru.drsk.progserega.inspectionsheet.entities.Tower;
import ru.drsk.progserega.inspectionsheet.services.ILocationChangeListener;
import ru.drsk.progserega.inspectionsheet.ui.adapters.LineTowerDeffectsListAdapter;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.InspectLineTowerContract;
import ru.drsk.progserega.inspectionsheet.ui.presenters.InspectLineTowerPresenter;

public class InspectLineTower extends ActivityWithGPS implements InspectLineTowerContract.View, SelectTowerDialog.ISelectTowerListener {

    public static final String NEXT_TOWER = "next_tower";

    private InspectionSheetApplication application;
    private InspectLineTowerContract.Presenter presenter;

    private LineTowerDeffectsListAdapter deffectsListAdapter;
    private ListView inspectionList;

    private SelectTowerDialog selectTowerDialog;

    private Switch GPSSwitch;

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
        initInspectionItemsList();
        initAddPhotoBtnImg();

        Intent intent = getIntent();

        String nextTower = "";
        if (intent.hasExtra(NEXT_TOWER)) {
            nextTower = (String) intent.getSerializableExtra(NEXT_TOWER);
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
        justifyListViewHeightBasedOnChildren(inspectionList, this);
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

    public void onNextBtnClick(View view) {
        presenter.nextButtonPressed();
        Toast.makeText(this, "Данные сохранены!", Toast.LENGTH_SHORT).show();
    }

    public void onFinishBtnClick(View view) {
    }

    public void onAddTowerPhotoBtnClick(View view) {
    }

    //TODO работает криво если высота динамическая
    public static void justifyListViewHeightBasedOnChildren(ListView listView, Context context) {

        LineTowerDeffectsListAdapter adapter = (LineTowerDeffectsListAdapter) listView.getAdapter();

        if (adapter == null) {
            return;
        }
        ViewGroup vg = listView;
        int totalHeight = 0;

        int width = MetricsUtils.getDisplayWidthDp(context);

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
