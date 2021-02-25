package ru.drsk.progserega.inspectionsheet.utility;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.transform.Source;

import ru.drsk.progserega.inspectionsheet.galleryselect.ImagesSelectorActivity;
import ru.drsk.progserega.inspectionsheet.galleryselect.SelectorSettings;

import static ru.drsk.progserega.inspectionsheet.utility.PermissionsUtility.REQUEST_CODE_WRITE_EXTERNAL_STORAGE;

public class PhotoUtility {

    public interface ChoosedListener {
        void onImageTaken(String photoPath, String source);

    }


    public static final int REQUEST_CAMERA = 100, SELECT_FILE = 101;

    public static final int REQ_IMAGE_WIDTH = 1200;
    public static final int REQ_IMAGE_HEIGHT = 1200;
    public static final int JPEG_QUALITY = 75;

    private static final String TASK_CAMERA = "camera";
    private static final String TASK_GALLERY = "gallery";
    private static final String TASK_INSPECTION = "inspection";

    private String userChooseTask;
    private String mCurrentPhotoPath;

    private long mCurrentEquipmentID = 0;
    private Context context;

    public String getmCurrentPhotoPath() {
        return mCurrentPhotoPath;
    }

    public void setmCurrentPhotoPath(String mCurrentPhotoPath) {
        this.mCurrentPhotoPath = mCurrentPhotoPath;
    }

    private Activity activity;

    private ChoosedListener choosedListener;

    private ArrayList< String > mResults = new ArrayList<>();

    public PhotoUtility(Activity activity, ChoosedListener choosedListener) {
        this.context = activity.getBaseContext();
        this.activity = activity;
        this.choosedListener = new onImageReady(choosedListener);
    }

    public void showPhotoDialog(long equipmentUID) {
        mCurrentEquipmentID = equipmentUID;

        final CharSequence[] items = {"Камера", "Галерея", "Текущий осмотр", "Отмена"};

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Добавить фото");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = PermissionsUtility.getPermissionExternalStorage(activity);

                if (items[item].equals("Камера")) {
                    userChooseTask = TASK_CAMERA;
                    if (result) cameraIntent();

                } else if (items[item].equals("Галерея")) {
                    userChooseTask = TASK_GALLERY;
                    if (result) galeryIntent(false);

                } else if (items[item].equals("Текущий осмотр")) {
                    userChooseTask = TASK_INSPECTION;
                    if (result) galeryIntent(true);
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

    private void galeryIntent(boolean isShowInspectionPhotos) {
        // start multiple photos selecto
        Intent intent = new Intent(this.activity, ImagesSelectorActivity.class);
        // max number of images to be selected
        intent.putExtra(SelectorSettings.SELECTOR_MAX_IMAGE_NUMBER, 10);
        // min size of image which will be shown; to filter tiny images (mainly icons)
        intent.putExtra(SelectorSettings.SELECTOR_MIN_IMAGE_SIZE, 100);
        // show camera or not
        intent.putExtra(SelectorSettings.SELECTOR_SHOW_CAMERA, false);
        // pass current selected images as the initial value
        intent.putStringArrayListExtra(SelectorSettings.SELECTOR_INITIAL_SELECTED_LIST, mResults);
        if (isShowInspectionPhotos) {
            String inspectionPath = getInspectionStorageDir();
            intent.putExtra(SelectorSettings.EXTRA_MEDIA_PATH, inspectionPath);
        } else {
            intent.putExtra(SelectorSettings.EXTRA_MEDIA_PATH, "");
        }
        // start the selector
        activity.startActivityForResult(intent, SELECT_FILE);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES + "/inspections/" + String.valueOf(mCurrentEquipmentID));
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

            mResults = data.getStringArrayListExtra(SelectorSettings.SELECTOR_RESULTS);
            assert mResults != null;

            for (String result : mResults) {

                String imagePath = copyImageInsideAppPicturesFolder(result);
                if (imagePath != null) {
                    choosedListener.onImageTaken(imagePath, userChooseTask);
                } else {
                    Toast.makeText(this.context, "Ошибка копирования файла из галереи!" + result, Toast.LENGTH_SHORT).show();
                }
            }

            mResults = null;

        }

        if (requestCode == REQUEST_CAMERA) {
            //Toast.makeText(this, "IMAGE FROM CAMERA", Toast.LENGTH_SHORT).show();
            choosedListener.onImageTaken(mCurrentPhotoPath, userChooseTask);
        }
    }

    private String getInspectionStorageDir() {
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES + "/inspections/" + String.valueOf(mCurrentEquipmentID));
        ///String targetpath = storageDir.getAbsolutePath() + "/" + System.currentTimeMillis() + "_" + imageName;
        return storageDir.getAbsolutePath();

    }

    private String copyImageInsideAppPicturesFolder(String galeryFilePath) {

        String[] parts = galeryFilePath.split("/");
        if (parts.length <= 1) {
            return null;
        }
        String imageName = parts[parts.length - 1];
        if (imageName.isEmpty()) {
            return null;
        }

        if (galeryFilePath.contains("inspectionsheet/files/Pictures/inspections/") && userChooseTask.equals(TASK_INSPECTION)) {
            return galeryFilePath;
        }
        ///storage/emulated/0/Android/data/ru.drsk.progserega.inspectionsheet/files/Pictures/inspections/47205/1611105930018_zf0ag4yxvf4vsw9xoftd_acnr38.png

        // File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES + "/inspections");
        //String targetpath = storageDir.getAbsolutePath() + "/" + System.currentTimeMillis() + "_" + imageName;

        String targetpath = getInspectionStorageDir() + "/" + System.currentTimeMillis() + "_" + imageName;

        File sourceLocation = new File(galeryFilePath);
        File targetLocation = new File(targetpath);

        InputStream in = null;
        try {
            in = new FileInputStream(sourceLocation);

            OutputStream out = new FileOutputStream(targetLocation);

            // Copy the bits from instream to outstream
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return targetpath;
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_WRITE_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChooseTask.equals(TASK_CAMERA)) {
                        cameraIntent();
                    }
                    if (userChooseTask.equals(TASK_GALLERY)) {
                        galeryIntent(false);
                    }
                    if (userChooseTask.equals(TASK_INSPECTION)) {
                        galeryIntent(true);
                    }
                } else {
                    // Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
        }
    }

    class onImageReady implements ChoosedListener {
        private ChoosedListener listener;

        public onImageReady(ChoosedListener listener) {
            this.listener = listener;
        }

        @Override
        public void onImageTaken(String photoPath, String source) {
            if(!source.equals(TASK_INSPECTION)) {
                compressImage(photoPath, REQ_IMAGE_WIDTH, REQ_IMAGE_HEIGHT);
            }

            if (listener != null) {
                listener.onImageTaken(photoPath, source);
            }
        }

        public int calculateInSampleSize(
                BitmapFactory.Options options, int reqWidth, int reqHeight) {
            // Raw height and width of image
            final int height = options.outHeight;
            final int width = options.outWidth;
            int inSampleSize = 1;

            if (height > reqHeight || width > reqWidth) {

                // Calculate ratios of height and width to requested height and width
                final int heightRatio = Math.round((float) height / (float) reqHeight);
                final int widthRatio = Math.round((float) width / (float) reqWidth);

                // Choose the smallest ratio as inSampleSize value, this will guarantee
                // a final image with both dimensions larger than or equal to the
                // requested height and width.
                inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
            }

            return inSampleSize;
        }

        public Bitmap decodeSampledBitmapFromFile(String imagePath, int reqWidth, int reqHeight) {

            // First decode with inJustDecodeBounds=true to check dimensions
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(imagePath, options);

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeFile(imagePath, options);
        }

        public  Bitmap rotateImage(Bitmap bitmap, String path) throws IOException {
            int rotate = 0;
            ExifInterface exif;
            exif = new ExifInterface(path);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
            Matrix matrix = new Matrix();
            matrix.postRotate(rotate);
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), matrix, true);
        }


        public void compressImage(String imagePath, int reqWidth, int reqHeight) {

            try {
                Bitmap img = decodeSampledBitmapFromFile(imagePath, reqWidth, reqHeight);

                //поворачиваем
                Bitmap rotatedImg = rotateImage(img, imagePath);

                //convert array of bytes into file
                FileOutputStream fileOuputStream = new FileOutputStream(imagePath);
                rotatedImg.compress(Bitmap.CompressFormat.JPEG, JPEG_QUALITY, fileOuputStream);

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }
}
