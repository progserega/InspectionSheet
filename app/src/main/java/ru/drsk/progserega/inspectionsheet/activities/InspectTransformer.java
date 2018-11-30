package ru.drsk.progserega.inspectionsheet.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.activities.adapters.TransformatorInspectionAdapter;
import ru.drsk.progserega.inspectionsheet.entities.Substation;
import ru.drsk.progserega.inspectionsheet.entities.Transformer;
import ru.drsk.progserega.inspectionsheet.entities.inspections.Deffect;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.entities.inspections.SubstationInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TransformerInspection;
import ru.drsk.progserega.inspectionsheet.storages.ITransformerStorage;
import ru.drsk.progserega.inspectionsheet.storages.json.TransfInspectionListReader;
import ru.drsk.progserega.inspectionsheet.storages.stub.TransformerStorageStub;

import static ru.drsk.progserega.inspectionsheet.activities.AddDefect.DEFFECT_NAME;

public class InspectTransformer extends AppCompatActivity {

    static final int GET_DEFFECT_VALUE_REQUEST = 1;

    private TransformatorInspectionAdapter transformatorInspectionAdapter;
    private TransformerInspection inspection;
    private InspectionSheetApplication application;


    private ArrayAdapter<String> transformatorAdapter;
    private Spinner transformatorSpinner;

    SubstationInspection substationInspection;

    private  List<Transformer> transformers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect_transformator);

        ImageButton imageButton =  (ImageButton) findViewById(R.id.inpsect_transformator_save_btn);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            imageButton.setImageResource(R.drawable.ic_baseline_save_24px);
        }else {
            /* старые версии не поддерживают векторные рисунки */
            imageButton.setImageResource(R.drawable.ic_save_balack_png);
        }
        imageButton.invalidate();

        this.application = (InspectionSheetApplication) this.getApplication();

        substationInspection = this.application.getSubstationInspection();

        ITransformerStorage transformatorStorage = new TransformerStorageStub();
        Substation substation = substationInspection.getSubstation();

        TextView substationNameText = (TextView) findViewById(R.id.inspection_transformator_substation);
        substationNameText.setText(substation.getName());

        transformers = transformatorStorage.getBySubstantionId(substation.getId());
        List<String> transfNames = Transformer.getNames(transformers);
        transfNames.add(0, "не выбран");

        transformatorAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, transfNames);
        transformatorAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        transformatorSpinner = (Spinner) findViewById(R.id.select_transformator_spinner);
        transformatorSpinner.setAdapter(transformatorAdapter);
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





        inspection = new TransformerInspection(substation, null);
        //inspection.loadList(inspectionListReader);

        transformatorInspectionAdapter = new TransformatorInspectionAdapter(this, inspection);
        ListView transfInspectionList = (ListView) findViewById(R.id.inspection_transformator_list);
        transfInspectionList.setAdapter(transformatorInspectionAdapter);

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> list, View itemView, int position, long id) {
                onListItemClick(list, itemView, position, id);
            }
        };
        transfInspectionList.setOnItemClickListener(itemClickListener);

    }

    private void onListItemClick(AdapterView<?> list, View v, int position, long id) {
        InspectionItem inspectionItem = (InspectionItem) transformatorInspectionAdapter.getItem(position);

        Deffect deffect = inspectionItem.getDeffect();

        application.setDeffect(deffect);
       // Toast.makeText(this, inspectionItem.getName() + " selected!", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, AddDefect.class);
        intent.putExtra(DEFFECT_NAME, inspectionItem.getName());
        startActivityForResult(intent, GET_DEFFECT_VALUE_REQUEST);

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

    private void onSelectTransormator(int position){

        if(position == 0){

            return;
        }

        Transformer transformator = transformers.get(position - 1);

        TransformerInspection inspection = substationInspection.getInspectionByTransformator(transformator.getId());
        if(inspection == null){
            inspection = new TransformerInspection(substationInspection.getSubstation(), transformator);
            TransfInspectionListReader inspectionListReader = new TransfInspectionListReader(getBaseContext().getResources().openRawResource(R.raw.transormator_inspection_list));
            inspection.loadList(inspectionListReader);
            substationInspection.addInspection(transformator.getId(), inspection);
        }

        transformatorInspectionAdapter.setInspection(inspection);
        transformatorInspectionAdapter.notifyDataSetChanged();
    }
}
