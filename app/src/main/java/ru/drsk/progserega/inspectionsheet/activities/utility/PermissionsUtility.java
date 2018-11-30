package ru.drsk.progserega.inspectionsheet.activities.utility;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import ru.drsk.progserega.inspectionsheet.activities.SearchObject;

public class PermissionsUtility {

    public static final int REQUEST_CODE_ACCESS_FINE_LOCATION = 0;
    public static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 1;



    public static boolean getPermissionExternalStorage (final Activity activity){

        int currentAPIVersion = Build.VERSION.SDK_INT;

        if(checkPermission(activity,  Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            return true;
        }

        requestStorageAccess(activity);


        return false;
    }

    private static boolean checkPermission(Context context, String permission) {
        int permissionCheck = ContextCompat.checkSelfPermission(context, permission);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
           // Toast.makeText(context, "Permission (already) Granted!", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

//    private void requestGPSAccess() {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
//
//            showExplanation(
//                    "Необходимо разрешение",
//                    "Для получения координат местоположения, необходим доступ к модулю GPS",
//                    Manifest.permission.ACCESS_FINE_LOCATION,
//                    REQUEST_CODE_ACCESS_FINE_LOCATION);
//
//        } else {
//            requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, REQUEST_CODE_ACCESS_FINE_LOCATION);
//        }
//    }

    private static void requestStorageAccess(Activity activity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            showExplanation(
                    "Необходимо разрешение",
                    "Для доступа к фотографиям необходимо разрешение",
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    REQUEST_CODE_WRITE_EXTERNAL_STORAGE,
                    activity);

        } else {
            requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_CODE_WRITE_EXTERNAL_STORAGE, activity);
        }
    }



    private static void showExplanation(String title, String message, final String permission, final int permissionRequestCode, final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        requestPermission(permission, permissionRequestCode, activity);
                    }
                });
        builder.create().show();
    }

    private static void requestPermission(String permissionName, int permissionRequestCode, Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{permissionName}, permissionRequestCode);
    }

    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
}
