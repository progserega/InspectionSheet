package ru.drsk.progserega.inspectionsheet.ui.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;

public class DeffectDescription extends AppCompatActivity {

    private InspectionSheetApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deffect_description);

        this.application = (InspectionSheetApplication) this.getApplication();

        setTitle("Описание дефекта");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ru.drsk.progserega.inspectionsheet.entities.inspections.DeffectDescription description = application.getState().getDeffectDescription();

        TextView titleText = (TextView) findViewById(R.id.deffect_description__title);
        titleText.setText(description.getTitle());

        TextView descriptionText = (TextView) findViewById(R.id.deffect_description__descr);
        descriptionText.setText(description.getDescription());

        ImageView imageView = (ImageView) findViewById(R.id.deffect_description__image);
        if (description.getPhoto() != null && description.getPhoto().getPath() != null && !description.getPhoto().getPath().isEmpty()) {
            imageView.setImageURI(Uri.fromFile(new File(description.getPhoto().getPath())));
        }

//        File imgFile = new File(inspectionItem.getDescription().getPhoto().getPath());
//
//       // imageView.setImageBitmap(inspectionItem.getDescription().getPhoto().getThumbnail());
//        if (imgFile.exists()) {
//
//            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//            imageView.setImageBitmap(myBitmap);
//        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // получим идентификатор выбранного пункта меню
        int id = item.getItemId();
        // Операции для выбранного пункта меню
        switch (id) {

            case android.R.id.home:
                onBackPressed();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }

    }
}