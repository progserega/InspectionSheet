package ru.drsk.progserega.inspectionsheet.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.activities.adapters.TransformatorInspectionAdapter;
import ru.drsk.progserega.inspectionsheet.entities.inspections.Deffect;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TransformatorInspection;
import ru.drsk.progserega.inspectionsheet.storages.json.TransfInspectionListReader;

import static ru.drsk.progserega.inspectionsheet.activities.AddDefect.DEFFECT_NAME;

public class InspectTransformator extends AppCompatActivity {

    static final int GET_DEFFECT_VALUE_REQUEST = 1;

    private TransformatorInspectionAdapter transformatorInspectionAdapter;
    private TransformatorInspection inspection;
    private InspectionSheetApplication application;
    TransfInspectionListReader inspectionListReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect_transformator);
        this.application = (InspectionSheetApplication) this.getApplication();


        inspectionListReader = new TransfInspectionListReader(getBaseContext().getResources().openRawResource(R.raw.transormator_inspection_list));
        inspection = new TransformatorInspection();
        inspection.loadList(inspectionListReader);

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
}
