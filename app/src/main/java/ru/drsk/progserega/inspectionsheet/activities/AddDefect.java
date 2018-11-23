package ru.drsk.progserega.inspectionsheet.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.activities.adapters.ImageAdapter;
import ru.drsk.progserega.inspectionsheet.activities.utility.PermissionsUtility;
import ru.drsk.progserega.inspectionsheet.entities.inspections.Deffect;
import ru.drsk.progserega.inspectionsheet.entities.inspections.DeffectPhoto;

import static ru.drsk.progserega.inspectionsheet.activities.utility.PermissionsUtility.REQUEST_CODE_READ_EXTERNAL_STORAGE;

public class AddDefect extends AppCompatActivity {

    public static final String DEFFECT_NAME = "deffect_name";
    private final int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    private String userChooseTask;
    private InspectionSheetApplication application;

    private ImageAdapter imageAdapter;
    private Deffect deffect;

    private List<DeffectPhoto> deffectPhotos;

    private  TextView deffectDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_defect);

        this.application = (InspectionSheetApplication) this.getApplication();
        Intent intent = getIntent();
        String inspectionName = (String) intent.getStringExtra(DEFFECT_NAME);

        TextView deffectNameTextView = (TextView) findViewById(R.id.add_defect_inspection_name);
        deffectNameTextView.setText(inspectionName);

        deffect = application.getDeffect();

        deffectDescription = (TextView) findViewById(R.id.add_defect_description);
        deffectDescription.setText(deffect.getDescription());


        GridView gridview = (GridView) findViewById(R.id.add_defect_photos);
        float scalefactor = getResources().getDisplayMetrics().density;
        int imageWidth = (int) (115 * scalefactor);

        saveDeffectPhotos(deffect.getPhotos());

        imageAdapter = new ImageAdapter(this, deffectPhotos, imageWidth);
        gridview.setAdapter(imageAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(AddDefect.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void saveDeffectPhotos(List<DeffectPhoto> photos){
        deffectPhotos = new ArrayList<>();
        for(DeffectPhoto photo: photos){
            deffectPhotos.add(photo);
        }
    }

    public void onAddPhotoBtnClick(View view) {

        final CharSequence[] items = {"Камера", "Галерея", "Отмена"};

        AlertDialog.Builder builder = new AlertDialog.Builder(AddDefect.this);
        builder.setTitle("Добавить фото");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = PermissionsUtility.getPermissionExternalStorage(AddDefect.this);

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
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galeryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select file"), SELECT_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == SELECT_FILE) {
            onSelectFromGaleryResult(data);
        }

        if (requestCode == REQUEST_CAMERA) {
            Toast.makeText(this, "IMAGE FROM CAMERA", Toast.LENGTH_SHORT).show();
            onCaptureImageResult(data);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChooseTask.equals("CAMERA")) {
                        cameraIntent();
                    }
                    if (userChooseTask.equals("GALERY")) {
                        galeryIntent();
                    }
                } else {
                    Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void onCaptureImageResult(Intent data) {

        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 9, outputStream);

        File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(outputStream.toByteArray());
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        deffectPhotos.add(new DeffectPhoto(thumbnail));
        imageAdapter.notifyDataSetChanged();

    }

    private void onSelectFromGaleryResult(Intent data) {

        if(data == null){
            return;
        }

        Bitmap bitmap = null;

        try {
            bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
        } catch (IOException e) {
            e.printStackTrace();
        }

        deffectPhotos.add(new DeffectPhoto(bitmap));
        imageAdapter.notifyDataSetChanged();
//        String[] filePathColumn = {MediaStore.Images.Media.DATA};
//        List<String> imagesEncodedList = new ArrayList<String>();
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            if (data.getClipData() != null) {
//                int count = data.getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
//                for (int i = 0; i < count; i++) {
//                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
//                    //do something with the image (save it to some directory or whatever you need to do with it here)
//                    try {
//                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
//                        deffect.getPhotos().add(new DeffectPhoto(bitmap));
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }
//        } else if (data.getData() != null) {
//            //для савсем старых версий
////            Uri mImageUri = data.getData();
////
////            // Get the cursor
////            Cursor cursor = getContentResolver().query(mImageUri,
////                    filePathColumn, null, null, null);
////            // Move to first row
////            cursor.moveToFirst();
////
////            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
////            String imageEncoded = cursor.getString(columnIndex);
////            cursor.close();
//        }
//
//        imageAdapter.notifyDataSetChanged();

    }

    public void onSaveBtnPress(View view){

        deffect.setPhotos(deffectPhotos);

        deffect.setDescription(deffectDescription.getText().toString());

        Intent returnIntent = getIntent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
