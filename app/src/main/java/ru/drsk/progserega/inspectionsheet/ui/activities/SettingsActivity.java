package ru.drsk.progserega.inspectionsheet.ui.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.SettingsContract;
import ru.drsk.progserega.inspectionsheet.ui.presenters.SettingsPresenter;
import ru.drsk.progserega.inspectionsheet.utility.ButtonUtils;

public class SettingsActivity extends AppCompatActivity implements SettingsContract.View, SelectOrganizationDialogFragment.ISelectOrganizationListener {

    private InspectionSheetApplication application;
    private SettingsContract.Presenter presenter;
    private SelectOrganizationDialogFragment selectOrganizationDlog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        this.application = (InspectionSheetApplication) this.getApplication();
        this.presenter = new SettingsPresenter(this, application);

        setTitle("Настройки");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButtonUtils.initSaveBtnImg((ImageButton) findViewById(R.id.settings_save_btn));

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

    @Override
    public String getFio() {
        EditText textView = (EditText) findViewById(R.id.settings_fio);
        return textView.getText().toString();
    }

    @Override
    public String getUserPosition() {
        EditText textView = (EditText) findViewById(R.id.settings_position);
        return textView.getText().toString();
    }

    @Override
    public void setFio(String fio) {
        EditText textView = (EditText) findViewById(R.id.settings_fio);
        textView.setText(fio);
    }

    @Override
    public void setUserPosition(String position) {
        EditText textView = (EditText) findViewById(R.id.settings_position);
        textView.setText(position);
    }

    @Override
    public void setSpResName(String spResName) {
        TextView textView = (TextView) findViewById(R.id.settings_RES);
        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        textView.setText(spResName);
    }

    @Override
    public void setServerUrl(String serverUrl) {
        EditText textView = (EditText) findViewById(R.id.settings_server_url);
//        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        textView.setText(serverUrl);
    }

    @Override
    public String getServerUrl() {
        EditText textView = (EditText) findViewById(R.id.settings_server_url);
        return textView.getText().toString();
    }

    @Override
    public String getServerAltUrl() {
        EditText textView = (EditText) findViewById(R.id.settings_server_alt_url);
        return textView.getText().toString();
    }

    @Override
    public void setServerAltUrl(String serverAltUrl) {
        EditText textView = (EditText) findViewById(R.id.settings_server_alt_url);
        textView.setText(serverAltUrl);
    }

    public void onSaveBtnPress(View view) {
        presenter.SaveBtnPressed();
        Toast.makeText(this, "Сохранено!", Toast.LENGTH_LONG).show();
    }

    public void onSelectResClick(View view) {
        //    Toast.makeText(this, "Выбор РЭС", Toast.LENGTH_LONG).show();
        presenter.SelectResBtnPressed();
    }

    @Override
    public void showSelectOrganizationDialog(long enterpriseId, long areaId) {
        FragmentManager fm = getSupportFragmentManager();
        if (selectOrganizationDlog == null) {
            selectOrganizationDlog = SelectOrganizationDialogFragment.newInstance(this.application.getOrganizationService());
        }
        selectOrganizationDlog.Show(fm, 0, 0);
    }

    @Override
    public void onSelectOrganization(long enterpriseId, long areaId) {
        presenter.OrganizationSelected(enterpriseId, areaId);
    }

    public void onRefreshOrganizationClick(View view) {
        presenter.RefreshOrganizationsBtnPressed();
    }

    @Override
    public void showError(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.create().show();
    }

    public void ShowProgressBar(){
        LinearLayout progress = (LinearLayout) findViewById(R.id.settingss_progress_bar);
        progress.setVisibility(View.VISIBLE);
    }

    public void HideProgressBar(){
        LinearLayout progress = (LinearLayout) findViewById(R.id.settingss_progress_bar);
        progress.setVisibility(View.GONE);
    }

    @Override
    public void finishView() {
        Intent returnIntent = getIntent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }


}
