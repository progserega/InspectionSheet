package ru.drsk.progserega.inspectionsheet.activities;

import android.Manifest;
import android.app.Application;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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

    public void syncData(View view){
       // showGPSPermission();
    }
//    public void getApplication(View vie){
//        InspectionSheetApplication application = (InspectionSheetApplication) this.getApplication();
//        Log.i("main activity", application.test);
//    }

//    private void showGPSPermission() {
//        int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
//
//        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
//                showExplanation("Permission Needed", "Rationale", Manifest.permission.ACCESS_FINE_LOCATION, REQUEST_CODE_ACCESS_FINE_LOCATION);
//            } else {
//                requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, REQUEST_CODE_ACCESS_FINE_LOCATION);
//            }
//        } else {
//            Toast.makeText(this, "Permission (already) Granted!", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(
//            int requestCode,
//            String permissions[],
//            int[] grantResults) {
//        switch (requestCode) {
//            case REQUEST_CODE_ACCESS_FINE_LOCATION:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
//                }
//        }
//    }
//
//    private void showExplanation(String title,
//                                 String message,
//                                 final String permission,
//                                 final int permissionRequestCode) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(title)
//                .setMessage(message)
//                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        requestPermission(permission, permissionRequestCode);
//                    }
//                });
//        builder.create().show();
//    }
//
//    private void requestPermission(String permissionName, int permissionRequestCode) {
//        ActivityCompat.requestPermissions(MainActivity.this,
//                new String[]{permissionName}, permissionRequestCode);
//    }
}
