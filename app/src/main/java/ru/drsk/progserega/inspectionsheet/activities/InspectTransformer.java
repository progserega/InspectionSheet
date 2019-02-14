package ru.drsk.progserega.inspectionsheet.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.activities.adapters.TransformatorInspectionAdapter;
import ru.drsk.progserega.inspectionsheet.activities.adapters.TransformerSpinnerAdapter;
import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.Transformer;
import ru.drsk.progserega.inspectionsheet.entities.TransformerInSlot;
import ru.drsk.progserega.inspectionsheet.entities.inspections.DeffectPhoto;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItemResult;
import ru.drsk.progserega.inspectionsheet.entities.inspections.ISubstationInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItemType;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TransformerInspection;
import ru.drsk.progserega.inspectionsheet.services.InspectionService;
import ru.drsk.progserega.inspectionsheet.storages.IInspectionStorage;
import ru.drsk.progserega.inspectionsheet.storages.ITransformerStorage;
import ru.drsk.progserega.inspectionsheet.ui.activities.FullscreenImageActivity;
import ru.drsk.progserega.inspectionsheet.ui.activities.GroupAddTransfrmerDeffect;
import ru.drsk.progserega.inspectionsheet.ui.activities.SwitchTransformerInspectionsDialog;

import static ru.drsk.progserega.inspectionsheet.activities.AddDefect.DEFFECT_NAME;
import static ru.drsk.progserega.inspectionsheet.ui.activities.FullscreenImageActivity.IMAGE_IDX;

public class InspectTransformer extends AppCompatActivity implements
        SelectTransformerDialog.AddTransformerListener,
        TransformatorInspectionAdapter.OnItemPhotoClickListener {

    static final int GET_DEFFECT_VALUE_REQUEST = 1;

    private TransformatorInspectionAdapter transformatorInspectionAdapter;
    private InspectionSheetApplication application;
    private ITransformerStorage transformerStorage;

    // private ArrayAdapter<String> transformerSpinnerAdapter;
    private TransformerSpinnerAdapter transformerSpinnerAdapter;
    private Spinner transformatorSpinner;

    private ISubstationInspection substationInspection;

    //private List<TransformerInSlot> transformers;

    private List<TransformerInspection> transformerInspections;

    private SelectTransformerDialog selectTransformerDialog;

    private InspectionService inspectionService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect_transformator);

        ImageButton imageButton = (ImageButton) findViewById(R.id.inpsect_transformator_save_btn);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            imageButton.setImageResource(R.drawable.ic_baseline_save_24px);
        } else {
            /* старые версии не поддерживают векторные рисунки */
            imageButton.setImageResource(R.drawable.ic_save_balack_png);
        }
        imageButton.invalidate();

        this.application = (InspectionSheetApplication) this.getApplication();

        substationInspection = this.application.getCurrentSubstationInspection();
        inspectionService = this.application.getInspectionService();

        TextView substationNameText = (TextView) findViewById(R.id.inspection_transformator_substation);
        Equipment substation = substationInspection.getEquipment();
        substationNameText.setText(substation.getName());

        transformerStorage = this.application.getTransformerStorage();

        transformerInspections = substationInspection.getTransformerInspections();
        if (transformerInspections == null) {
            transformerInspections = inspectionService.getSubstationTransformersWithInspections(substation);
            substationInspection.setInspection(transformerInspections);
        }


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
        transformatorInspectionAdapter = new TransformatorInspectionAdapter(this, inspection, this);
        ListView transfInspectionList = (ListView) findViewById(R.id.inspection_transformator_list);
        transfInspectionList.setAdapter(transformatorInspectionAdapter);

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> list, View itemView, int position, long id) {
                onListItemClick(list, itemView, position, id);
            }
        };
        transfInspectionList.setOnItemClickListener(itemClickListener);

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
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    private void onListItemClick(AdapterView<?> list, View v, int position, long id) {
        InspectionItem inspectionItem = (InspectionItem) transformatorInspectionAdapter.getItem(position);

        if (inspectionItem.getType().equals(InspectionItemType.HEADER)) {
            List<InspectionItem> allItems = transformatorInspectionAdapter.getInspectionItems();
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

            Intent intent = new Intent(this, AddDefect.class);
            intent.putExtra(DEFFECT_NAME, inspectionItem.getName());
            startActivityForResult(intent, GET_DEFFECT_VALUE_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        //Toast.makeText(this, "GET DEFFECT RESULT!!!", Toast.LENGTH_LONG).show();
        transformatorInspectionAdapter.notifyDataSetChanged();
    }

    private void onSelectTransormator(int position) {

        TransformerInspection inspection = transformerInspections.get(position);
        transformatorInspectionAdapter.setInspection(inspection);
        transformatorInspectionAdapter.notifyDataSetChanged();
    }

    public void onSaveBtnPress(View view) {

        List<TransformerInspection> allInspections = substationInspection.getTransformerInspections();
        IInspectionStorage inspectionStorage = application.getInspectionStorage();
        float sum = 0;
        for (TransformerInspection transformerInspection : allInspections) {

            inspectionStorage.saveInspection(transformerInspection);
            transformerInspection.setDone(true);

            sum += transformerInspection.calcInspectionPercent();
        }
        float middlePercent = sum / allInspections.size();

        TransformerInspection inspection = (TransformerInspection) transformatorSpinner.getSelectedItem();
        inspection.getSubstation().setInspectionDate(new Date());
        inspection.getSubstation().setInspectionPercent(middlePercent);

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
        Transformer transformer = transformerStorage.getById(transformerTypeId);
        TransformerInSlot transformerInSlot = new TransformerInSlot(insertedId, slot, transformer);

        //Создаем новый объект для осмотра
        TransformerInspection inspection = new TransformerInspection(substationInspection.getEquipment(), transformerInSlot);
        inspection.setInspectionItems(inspectionService.loadInspectionTemplates());

        //Добавляем к списку осмотров
        substationInspection.getTransformerInspections().add(inspection);


        transformerSpinnerAdapter.notifyDataSetChanged();

    }

    @Override
    public void onItemPhotoClick(InspectionItem inspectionItem, DeffectPhoto photo, int position) {
        // Toast.makeText(this, "TAP ON PHOTO  "+ photo.getPath(), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, FullscreenImageActivity.class);
        intent.putExtra(IMAGE_IDX, position);
        application.setPhotosForFullscreen(inspectionItem.getResult().getPhotos());
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

    private void switchInspections(long sourcePos, long destPos){
        if(sourcePos == destPos){
            return;
        }

        TransformerInspection sourceInspection = transformerInspections.get((int)sourcePos);
        TransformerInspection destInspection = transformerInspections.get((int) destPos);

        List<InspectionItem> tmp = sourceInspection.getInspectionItems();

        sourceInspection.setInspectionItems(destInspection.getInspectionItems());
        destInspection.setInspectionItems(tmp);


        transformerSpinnerAdapter.notifyDataSetChanged();
        transformatorInspectionAdapter.notifyDataSetChanged();
    }
}
