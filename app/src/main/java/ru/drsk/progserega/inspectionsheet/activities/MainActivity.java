package ru.drsk.progserega.inspectionsheet.activities;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Called when the user clicks the Send button
     */
    public void addDefect(View view) {
        Log.d("addDefect()", "1");

        // Do something in response to button
        //Intent intent = new Intent(this, addStationBug.class);
        Intent intent = new Intent(this, SelectTypeLine.class);
            /*EditText editText = (EditText) findViewById(R.id.edit_message);
            String message = editText.getText().toString();
            intent.putExtra(EXTRA_MESSAGE, message);*/
        Log.d("addDefect()", "2");
        startActivity(intent);
        Log.d("addDefect()", "3");
    }

//    public void getApplication(View vie){
//        InspectionSheetApplication application = (InspectionSheetApplication) this.getApplication();
//        Log.i("main activity", application.test);
//    }
}
