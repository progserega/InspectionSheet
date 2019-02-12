package ru.drsk.progserega.inspectionsheet.ui.activities;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.ui.adapters.FullScreenImageAdapter;


public class FullscreenImageActivity extends Activity {

    public static final String IMAGE_IDX= "image_idx";

    private InspectionSheetApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.application = (InspectionSheetApplication) this.getApplication();

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen_image);

        FullScreenImageAdapter fullScreenImageAdapter = new FullScreenImageAdapter(this, application.getPhotosForFullscreen());
        ViewPager pager =  findViewById(R.id.fullscreen_image_pager);
        pager.setAdapter(fullScreenImageAdapter);

        Intent intent = getIntent();
        int imageIdx = intent.getIntExtra(IMAGE_IDX, 0);
        pager.setCurrentItem(imageIdx);
    }


}
