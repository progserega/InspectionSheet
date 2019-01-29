package ru.drsk.progserega.inspectionsheet.activities.utility;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static ru.drsk.progserega.inspectionsheet.activities.utility.PermissionsUtility.REQUEST_CODE_WRITE_EXTERNAL_STORAGE;

public class PhotoUtility {

    public interface ChoosedListener {
        void onImageTaken( String photoPath);

    }


    private final int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    private String userChooseTask;
    private String mCurrentPhotoPath;


    private Context context;
    private Activity activity;

    private ChoosedListener choosedListener;

    public PhotoUtility( Activity activity,  ChoosedListener choosedListener){
        this.context = activity.getBaseContext();
        this.activity = activity;
        this.choosedListener = choosedListener;
    }

    public void showPhotoDialog(){
        final CharSequence[] items = {"Камера", "Галерея", "Отмена"};

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Добавить фото");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = PermissionsUtility.getPermissionExternalStorage(activity);

                if (items[item].equals("Камера")) {
                    userChooseTask = "CAMERA";
                    if (result) cameraIntent();

                } else if (items[item].equals("Галерея")) {
                    userChooseTask = "GALERY";
                    if (result) galeryIntent();
                } else if (items[item].equals("Отмена")) {
                    userChooseTask = "NONE";
                    dialog.dismiss();
                }

            }
        });
        builder.show();
    }

    private void cameraIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(context,
                        "ru.drsk.progserega.inspectionsheet.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                /* ----------- Фикс для старых андроидов ---------------*/
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        takePictureIntent.setClipData(ClipData.newRawUri("", photoURI));
                    }
                    takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
                /*------------------------------------------*/
                activity.startActivityForResult(takePictureIntent, REQUEST_CAMERA);
            }
        }
    }

    private void galeryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(Intent.createChooser(intent, "Select file"), SELECT_FILE);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == SELECT_FILE) {
            if (data == null || data.getData() == null) {
                return;
            }

            String realPath = ImageFilePath.getPath(this.context, data.getData());
            choosedListener.onImageTaken( realPath);
        }

        if (requestCode == REQUEST_CAMERA) {
            //Toast.makeText(this, "IMAGE FROM CAMERA", Toast.LENGTH_SHORT).show();
            choosedListener.onImageTaken(mCurrentPhotoPath);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_WRITE_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChooseTask.equals("CAMERA")) {
                        cameraIntent();
                    }
                    if (userChooseTask.equals("GALERY")) {
                        galeryIntent();
                    }
                } else {
                    // Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
        }
    }
}
