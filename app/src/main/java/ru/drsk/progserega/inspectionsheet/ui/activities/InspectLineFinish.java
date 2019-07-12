package ru.drsk.progserega.inspectionsheet.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;
import ru.drsk.progserega.inspectionsheet.entities.Voltage;

import static ru.drsk.progserega.inspectionsheet.ui.activities.SearchObject.OBJECT_TYPE;

public class InspectLineFinish extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect_line_finish);
    }

    public void onFinishBtnClick(View view) {
        Intent intent = new Intent(this,SearchObject.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(OBJECT_TYPE, EquipmentType.LINE);
        //intent.putExtra(SearchObject.LINE_TYPE, Voltage.V_04KV);
        startActivity(intent);
    }
}
