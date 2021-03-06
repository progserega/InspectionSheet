package ru.drsk.progserega.inspectionsheet.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;

import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;
import ru.drsk.progserega.inspectionsheet.entities.Voltage;

import static ru.drsk.progserega.inspectionsheet.ui.activities.SearchObject.OBJECT_TYPE;

public class SelectTypeLine extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_type_line);

        setTitle("Тип осмотра");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

    public void defectInVl04(View view) {

        Intent intent = new Intent(this, SearchObject.class);
        intent.putExtra(OBJECT_TYPE, EquipmentType.LINE);
        intent.putExtra(SearchObject.LINE_TYPE, Voltage.V_04KV);

        startActivity(intent);
    }

//    public void defectInVl6_10(View view) {
//
//        Intent intent = new Intent(this, SearchObject.class);
//        intent.putExtra(OBJECT_TYPE, EquipmentType.LINE);
//        intent.putExtra(LINE_TYPE, Voltage.V_6_10KV);
//
//
//        startActivity(intent);
//    }
//    public void defectInVl35_110(View view) {
//        Intent intent = new Intent(this, SearchObject.class);
//        intent.putExtra(OBJECT_TYPE, EquipmentType.LINE);
//        intent.putExtra(LINE_TYPE, Voltage.V_35_110KV);
//
//        startActivity(intent);
//    }

    public void defectInPs35_110(View view) {

        Intent intent = new Intent(this, SearchObject.class);
        intent.putExtra(OBJECT_TYPE, EquipmentType.SUBSTATION);

        startActivity(intent);
    }

    public void defectInTp6_10_04(View view) {

        Intent intent = new Intent(this, SearchObject.class);
        intent.putExtra(OBJECT_TYPE, EquipmentType.TP);

        startActivity(intent);
    }

}
