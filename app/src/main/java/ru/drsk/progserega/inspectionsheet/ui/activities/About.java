package ru.drsk.progserega.inspectionsheet.ui.activities;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.AboutContract;
import ru.drsk.progserega.inspectionsheet.ui.presenters.AboutPresenter;


public class About extends AppCompatActivity implements AboutContract.View {

    private AboutContract.Presenter presenter;
    private InspectionSheetApplication application;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        this.application = (InspectionSheetApplication) this.getApplication();
        this.presenter = new AboutPresenter(this, application);

        setTitle("О программе");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setAboutText();

        initCheckUpdateTextView();

        presenter.onViewCreated();
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

    private void setAboutText() {
        TextView textView = (TextView) findViewById(R.id.about_text);
        // textView.setText(Html.fromHtml(getString(R.string.text_about), Html.FROM_HTML_MODE_COMPACT));
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(getString(R.string.text_about), Html.FROM_HTML_MODE_COMPACT));
        } else {
            textView.setText(Html.fromHtml(getString(R.string.text_about)));
        }
    }

    @Override
    public PackageInfo getPackageInfo() {
        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return pInfo;
    }

    @Override
    public void setVersionName(String versionName) {
        TextView versionTextView = (TextView) findViewById(R.id.about_version);
        versionTextView.setText(versionName);
    }

    private void initCheckUpdateTextView(){
        TextView checkUpdateTextView = (TextView) findViewById(R.id.about__check_update);
        checkUpdateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onCheckUpdatePressed();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

}
