package ru.drsk.progserega.inspectionsheet.activities;

import android.app.Activity;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.activities.adapters.ImageAdapter;
import ru.drsk.progserega.inspectionsheet.activities.utility.PermissionsUtility;
import ru.drsk.progserega.inspectionsheet.entities.inspections.Deffect;
import ru.drsk.progserega.inspectionsheet.entities.inspections.DeffectPhoto;

import static ru.drsk.progserega.inspectionsheet.activities.utility.PermissionsUtility.REQUEST_CODE_WRITE_EXTERNAL_STORAGE;

public class AddDefect extends AppCompatActivity {

    public static final String DEFFECT_NAME = "deffect_name";
    private final int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    private String userChooseTask;
    private InspectionSheetApplication application;

    private ImageAdapter imageAdapter;
    private Deffect deffect;

    private List<DeffectPhoto> deffectPhotos;

    private  TextView deffectDescription;

    private String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_defect);


        ImageButton imageButton =  (ImageButton) findViewById(R.id.save_btn);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            imageButton.setImageResource(R.drawable.ic_baseline_save_24px);
        }else {
            /* старые версии не поддерживают векторные рисунки */
            imageButton.setImageResource(R.drawable.ic_save_balack_png);
        }
        imageButton.invalidate();

        this.application = (InspectionSheetApplication) this.getApplication();
        Intent intent = getIntent();
        String inspectionName = (String) intent.getStringExtra(DEFFECT_NAME);

        TextView deffectNameTextView = (TextView) findViewById(R.id.add_defect_inspection_name);
        deffectNameTextView.setText(inspectionName);

        deffect = application.getCurrentDeffect();

        deffectDescription = (TextView) findViewById(R.id.add_defect_description);
        deffectDescription.setText(deffect.getDescription());


        GridView gridview = (GridView) findViewById(R.id.add_defect_photos);
//        float scalefactor = getResources().getDisplayMetrics().density;
//        int imageWidth = (int) (115 * scalefactor);
        int imageWidth = dpToPx(115);

        saveDeffectPhotos(deffect.getPhotos());

        imageAdapter = new ImageAdapter(this, deffectPhotos, imageWidth);
        gridview.setAdapter(imageAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
               // Toast.makeText(AddDefect.this, "" + position, Toast.LENGTH_SHORT).show();
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

//    private void cameraIntent() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent, REQUEST_CAMERA);
//    }

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
            //Toast.makeText(this, "IMAGE FROM CAMERA", Toast.LENGTH_SHORT).show();
            onCaptureImageResult(data);
        }
    }


    @Override
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

    private void onCaptureImageResult(Intent data) {

        // Get the dimensions of the View
        int targetW = dpToPx(115);
        int targetH = dpToPx(115);

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
       // mImageView.setImageBitmap(bitmap);

//        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        thumbnail.compress(Bitmap.CompressFormat.JPEG, 9, outputStream);
//
//        String storageDir = Environment.getExternalStorageDirectory()+"/InspectionSheet/Photo";
//        File destinationDir = new File(storageDir);
//        File destination = new File(storageDir, System.currentTimeMillis() + ".jpg");
//
//        if(!PermissionsUtility.isExternalStorageWritable()){
//           return;
//        }
//        FileOutputStream fo;
//        try {
//            if(!destinationDir.exists()){
//                destinationDir.mkdirs();
//            }
//            destination.createNewFile();
//            fo = new FileOutputStream(destination);
//            fo.write(outputStream.toByteArray());
//            fo.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
        deffectPhotos.add(new DeffectPhoto(bitmap));
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
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void cameraIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "ru.drsk.progserega.inspectionsheet.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                /* ----------- Фикс для старых андроидов ---------------*/
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP ) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        takePictureIntent.setClipData(ClipData.newRawUri("", photoURI));
                    }
                    takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION|Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
                /*------------------------------------------*/
                startActivityForResult(takePictureIntent, REQUEST_CAMERA);
            }
        }
    }
    public void onSaveBtnPress(View view){

        deffect.setPhotos(deffectPhotos);

        deffect.setDescription(deffectDescription.getText().toString());

        Intent returnIntent = getIntent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    public int pxToDp(int px) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }
}
