package ru.drsk.progserega.inspectionsheet.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;

import java.lang.ref.WeakReference;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.InspectStationContract;
import ru.drsk.progserega.inspectionsheet.ui.presenters.InspectStationPresenter;

public class InspectStation extends AppCompatActivity implements InspectStationContract.View {

    private InspectionSheetApplication application;
    private InspectStationContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect_station);

        this.application = (InspectionSheetApplication) this.getApplication();
        this.presenter = new InspectStationPresenter(this, application);

        setTitle("Осмотр ПС ТП <NAME>");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initAddPhotoBtnImg();

        this.presenter.onViewCreated();
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

    private void initAddPhotoBtnImg() {
        ImageButton addPhotoBtn = (ImageButton) findViewById(R.id.inspect_station_add_common_photo);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            addPhotoBtn.setImageResource(R.drawable.ic_baseline_photo_camera_24px);
        } else {
            /* старые версии не поддерживают векторные рисунки */
            addPhotoBtn.setImageResource(R.drawable.ic_camera_png);
        }
        addPhotoBtn.invalidate();
    }
}
