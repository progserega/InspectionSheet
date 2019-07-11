package ru.drsk.progserega.inspectionsheet.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.Tower;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineSectionDeffect;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TowerDeffect;
import ru.drsk.progserega.inspectionsheet.services.ILocationChangeListener;
import ru.drsk.progserega.inspectionsheet.ui.adapters.LineSectionDeffectsListAdapter;
import ru.drsk.progserega.inspectionsheet.ui.adapters.LineTowerDeffectsListAdapter;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.InspectLineSectionContract;
import ru.drsk.progserega.inspectionsheet.ui.presenters.InspectLineSectionPresenter;
import ru.drsk.progserega.inspectionsheet.ui.presenters.InspectLineTowerPresenter;
import ru.drsk.progserega.inspectionsheet.utility.MetricsUtils;
import ru.drsk.progserega.inspectionsheet.utility.PhotoUtility;

public class InspectLineSection extends AppCompatActivity implements
        InspectLineSectionContract.View,   PhotoUtility.ChoosedListener
{
    private InspectionSheetApplication application;
    private InspectLineSectionContract.Presenter presenter;
    private PhotoUtility photoUtility;

    private ArrayAdapter materialsAdapter;
    private Spinner materialsSpinner;

    private ListView inspectionList;
    private LineSectionDeffectsListAdapter deffectsListAdapter;

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


    public void onAddLineSectionPhotoBtnClick(View view) {
    }

    public void onNextBtnClick(View view) {
        presenter.nextButtonPressed();
    }

    public void onFinishBtnClick(View view) {
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
    public void gotoSectionInspection(long nextSectionId) {

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

    }

    @Override
    public String getComment() {
        return null;
    }

    @Override
    public void setTowerPhotos(List<InspectionPhoto> photos) {

    }

    @Override
    public void showNextSectionSelectorDialog(String[] selectionItems) {

    }

    @Override
    public void showEndOfLineDialog() {

    }

    @Override
    public void onImageTaken(String photoPath) {

    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

//    private  void justifyListViewHeightBasedOnChildren(ListView listView, Context context) {
//
//        LineSectionDeffectsListAdapter adapter = (LineSectionDeffectsListAdapter) listView.getAdapter();
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
}
