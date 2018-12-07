package ru.drsk.progserega.inspectionsheet.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;
import ru.drsk.progserega.inspectionsheet.entities.Voltage;

import static ru.drsk.progserega.inspectionsheet.activities.SearchObject.LINE_TYPE;
import static ru.drsk.progserega.inspectionsheet.activities.SearchObject.OBJECT_TYPE;

public class SelectTypeLine extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_type_line);
    }



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // получим идентификатор выбранного пункта меню
//
//        int id = item.getItemId();
//
//        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
////
////        TextView infoTextView = (TextView) findViewById(R.id.textView);
////
////        // Операции для выбранного пункта меню
////        switch (id) {
////            case R.id.action_cat1:
////                infoTextView.setText("Вы выбрали кота!");
////                return true;
////            case R.id.action_cat2:
////                infoTextView.setText("Вы выбрали кошку!");
////                return true;
////            case R.id.action_cat3:
////                infoTextView.setText("Вы выбрали котёнка!");
////                return true;
////            default:
////                return super.onOptionsItemSelected(item);
////        }
//
//        return true;
//    }

    public void defectInVl04(View view) {

        Intent intent = new Intent(this, SearchObject.class);
        intent.putExtra(OBJECT_TYPE, EquipmentType.LINE);
        intent.putExtra(SearchObject.LINE_TYPE, Voltage.VL_04KV);

        startActivity(intent);
    }

    public void defectInVl6_10(View view) {

        Intent intent = new Intent(this, SearchObject.class);
        intent.putExtra(OBJECT_TYPE, EquipmentType.LINE);
        intent.putExtra(LINE_TYPE, Voltage.VL_6_10KV);


        startActivity(intent);
    }
    public void defectInVl35_110(View view) {
        Intent intent = new Intent(this, SearchObject.class);
        intent.putExtra(OBJECT_TYPE, EquipmentType.LINE);
        intent.putExtra(LINE_TYPE, Voltage.VL_35_110KV);

        startActivity(intent);
    }

    public void defectInPs35_110(View view) {

        Intent intent = new Intent(this, SearchObject.class);
        intent.putExtra(OBJECT_TYPE, EquipmentType.SUBSTATION);

        startActivity(intent);
    }

    public void defectInTp6_10_04(View view) {

        Intent intent = new Intent(this, SearchObject.class);
        intent.putExtra(OBJECT_TYPE, EquipmentType.TRANS_SUBSTATION);

        startActivity(intent);
    }

}
