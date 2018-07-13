package ru.drsk.progserega.inspectionsheet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;

public class SelectTypeLine extends AppCompatActivity {
    public final static String LINE_TYPE = "vl04";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_type_line);
    }
       /**
     * Called when the user clicks the vl_04_kv button
     */
    public void defectInVl04(View view) {
        Log.d("defectInVl04()", "1");
        // Do something in response to button
        //Intent intent = new Intent(this, addStationBug.class);
        Intent intent = new Intent(this, searchObject.class);
        intent.putExtra(LINE_TYPE, "vl04");
            /*EditText editText = (EditText) findViewById(R.id.edit_message);
            String message = editText.getText().toString();
            intent.putExtra(EXTRA_MESSAGE, message);*/
        Log.d("defectInVl04()", "2");
        startActivity(intent);
    }
    public void defectInVl6_10(View view) {
        Log.d("defectInVl6_10()", "1");
        // Do something in response to button
        //Intent intent = new Intent(this, addStationBug.class);
        Intent intent = new Intent(this, searchObject.class);
        intent.putExtra(LINE_TYPE, "vl6_10");
            /*EditText editText = (EditText) findViewById(R.id.edit_message);
            String message = editText.getText().toString();
            intent.putExtra(EXTRA_MESSAGE, message);*/
        Log.d("defectInVl6_10()", "2");
        startActivity(intent);
    }
    public void defectInVl35_110(View view) {
        Log.d("defectInVl35_110()", "1");
        // Do something in response to button
        //Intent intent = new Intent(this, addStationBug.class);
        Intent intent = new Intent(this, searchObject.class);
        intent.putExtra(LINE_TYPE, "vl35_110");
            /*EditText editText = (EditText) findViewById(R.id.edit_message);
            String message = editText.getText().toString();
            intent.putExtra(EXTRA_MESSAGE, message);*/
        Log.d("defectInVl35_110()", "2");
        startActivity(intent);
    }
    public void defectInPs35_110(View view) {
        Log.d("defectInPs35_110()", "1");
        // Do something in response to button
        //Intent intent = new Intent(this, addStationBug.class);
        Intent intent = new Intent(this, searchObject.class);
        intent.putExtra(LINE_TYPE, "ps35_110");
            /*EditText editText = (EditText) findViewById(R.id.edit_message);
            String message = editText.getText().toString();
            intent.putExtra(EXTRA_MESSAGE, message);*/
        Log.d("defectInPs35_110()", "2");
        startActivity(intent);
    }
    public void defectInTp6_10_04(View view) {
        Log.d("defectInTp6_10_04()", "1");
        // Do something in response to button
        //Intent intent = new Intent(this, addStationBug.class);
        Intent intent = new Intent(this, searchObject.class);
        intent.putExtra(LINE_TYPE, "tp6_10_04");
            /*EditText editText = (EditText) findViewById(R.id.edit_message);
            String message = editText.getText().toString();
            intent.putExtra(EXTRA_MESSAGE, message);*/
        Log.d("defectInTp6_10_04()", "2");
        startActivity(intent);
    }

}
