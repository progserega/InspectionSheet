package ru.drsk.progserega.inspectionsheet.ui.activities;

import android.app.Activity;
import android.content.ClipData;
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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.activities.DeffectValuesView;
import ru.drsk.progserega.inspectionsheet.activities.ExpandableHeightGridView;
import ru.drsk.progserega.inspectionsheet.activities.adapters.ImageAdapter;
import ru.drsk.progserega.inspectionsheet.activities.utility.ButtonUtils;
import ru.drsk.progserega.inspectionsheet.activities.utility.ImageFilePath;
import ru.drsk.progserega.inspectionsheet.activities.utility.MetricsUtils;
import ru.drsk.progserega.inspectionsheet.activities.utility.PermissionsUtility;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItemResult;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;

import static ru.drsk.progserega.inspectionsheet.activities.utility.PermissionsUtility.REQUEST_CODE_WRITE_EXTERNAL_STORAGE;
import static ru.drsk.progserega.inspectionsheet.ui.activities.FullscreenImageActivity.IMAGE_IDX;

public class AddDefect extends AppCompatActivity {

    public static final String DEFFECT_NAME = "deffect_name";
    private final int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    private String userChooseTask;
    private InspectionSheetApplication application;

    private ImageAdapter imageAdapter;
    private InspectionItemResult deffect;

    private List<InspectionPhoto> inspectionPhotos;

    private TextView deffectDescription;

    private String mCurrentPhotoPath;

    private DeffectValuesView valuesView;

    private DeffectValuesView subValuesView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_defect);

        //  this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        ButtonUtils.initSaveBtnImg((ImageButton) findViewById(R.id.save_btn));

        this.application = (InspectionSheetApplication) this.getApplication();
        Intent intent = getIntent();
        String inspectionName = (String) intent.getStringExtra(DEFFECT_NAME);

        TextView deffectNameTextView = (TextView) findViewById(R.id.add_defect_inspection_name);
        deffectNameTextView.setText(inspectionName);

        deffect = application.getCurrentDeffect();

        deffectDescription = (TextView) findViewById(R.id.add_defect_description);
        deffectDescription.setText(deffect.getComment());

        if (deffect.getPossibleResult() != null) {
            //   addDinamicContent((LinearLayout) findViewById(R.id.add_defect_result_layout), deffect.getPossibleResult());;
            valuesView = new DeffectValuesView(
                    (LinearLayout) findViewById(R.id.add_defect_result_layout),
                    deffect.getPossibleResult(),
                    deffect.getValues(),
                    this, new DeffectValuesView.OnValueChangeListener() {
                @Override
                public void valuesChange(List<String> values) {

                }
            });
            valuesView.build();
        }

        if (deffect.getPossibleSubresult() != null) {
            //addDinamicContent((LinearLayout) findViewById(R.id.add_defect_subresult_layout), deffect.getPossibleSubresult());
            subValuesView = new DeffectValuesView(
                    (LinearLayout) findViewById(R.id.add_defect_result_layout),
                    deffect.getPossibleSubresult(),
                    deffect.getSubValues(),
                    this, new DeffectValuesView.OnValueChangeListener() {
                @Override
                public void valuesChange(List<String> values) {

                }
            });
            subValuesView.build();
        }

        ExpandableHeightGridView gridview = (ExpandableHeightGridView) findViewById(R.id.add_defect_photos);
        gridview.setExpanded(true);

//        float scalefactor = getResources().getDisplayMetrics().density;
//        int imageWidth = (int) (115 * scalefactor);
        int imageWidth = MetricsUtils.dpToPx(115, this);

        saveDeffectPhotos(deffect.getPhotos());

        imageAdapter = new ImageAdapter(this, inspectionPhotos, imageWidth);
        gridview.setAdapter(imageAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                showFullscreenPhoto(position);
            }
        });


    }

    private void saveDeffectPhotos(List<InspectionPhoto> photos) {
        inspectionPhotos = new ArrayList<>();
        for (InspectionPhoto photo : photos) {
            inspectionPhotos.add(photo);
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

        inspectionPhotos.add(new InspectionPhoto(0, mCurrentPhotoPath, this));
        imageAdapter.notifyDataSetChanged();

    }

    private void onSelectFromGaleryResult(Intent data) {

        if (data == null || data.getData() == null) {
            return;
        }

        String realPath = ImageFilePath.getPath(this, data.getData());

        inspectionPhotos.add(new InspectionPhoto(0, realPath, this));
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
//                        deffect.getPhotos().add(new InspectionPhoto(bitmap));
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
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        takePictureIntent.setClipData(ClipData.newRawUri("", photoURI));
                    }
                    takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
                /*------------------------------------------*/
                startActivityForResult(takePictureIntent, REQUEST_CAMERA);
            }
        }
    }


    public void onSaveBtnPress(View view) {

        deffect.setPhotos(inspectionPhotos);

        deffect.setComment(deffectDescription.getText().toString());


        if (valuesView != null) {
            deffect.getValues().clear();
            deffect.getValues().addAll(valuesView.getResult());
        }

        if (subValuesView != null) {
            deffect.getSubValues().clear();
            deffect.getSubValues().addAll(subValuesView.getResult());
        }

        Intent returnIntent = getIntent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private void showFullscreenPhoto(int position) {
        Intent intent = new Intent(AddDefect.this, FullscreenImageActivity.class);
        intent.putExtra(IMAGE_IDX, position);
        application.setPhotosForFullscreen(inspectionPhotos);
        startActivity(intent);
    }
}
