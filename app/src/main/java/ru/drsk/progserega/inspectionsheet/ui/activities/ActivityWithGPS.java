package ru.drsk.progserega.inspectionsheet.ui.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import ru.drsk.progserega.inspectionsheet.services.ILocationChangeListener;

public abstract class ActivityWithGPS extends AppCompatActivity implements ILocationChangeListener {

    protected abstract void onGPSRequestGaranted();
    protected abstract void onGPSRequestDenied();

    private static final int REQUEST_CODE_ACCESS_FINE_LOCATION = 5;

    protected boolean checkGPSPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            //Toast.makeText(this, "Permission (already) Granted!", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    protected void requestGPSAccess() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

            showExplanation(
                    "Необходимо разрешение",
                    "Для получения координат местоположения, необходим доступ к модулю GPS",
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    REQUEST_CODE_ACCESS_FINE_LOCATION);

        } else {
            requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, REQUEST_CODE_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ACCESS_FINE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();
                    onGPSRequestGaranted();
                } else {
                    Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                    onGPSRequestDenied();
                }
        }
    }

    private void showExplanation(String title, String message, final String permission, final int permissionRequestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        requestPermission(permission, permissionRequestCode);
                    }
                });
        builder.create().show();
    }

    private void requestPermission(String permissionName, int permissionRequestCode) {
        ActivityCompat.requestPermissions(this,
                new String[]{permissionName}, permissionRequestCode);
    }


}
