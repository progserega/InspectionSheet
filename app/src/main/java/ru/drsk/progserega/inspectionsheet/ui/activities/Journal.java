package ru.drsk.progserega.inspectionsheet.ui.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.LogModel;
import ru.drsk.progserega.inspectionsheet.ui.adapters.EquipmentRCListAdapter;
import ru.drsk.progserega.inspectionsheet.ui.adapters.JournalListAdapter;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.JournalContract;
import ru.drsk.progserega.inspectionsheet.ui.presenters.JournalPresenter;
import ru.drsk.progserega.inspectionsheet.utility.PermissionsUtility;

import static ru.drsk.progserega.inspectionsheet.utility.PermissionsUtility.REQUEST_CODE_WRITE_EXTERNAL_STORAGE;

public class Journal extends AppCompatActivity implements JournalContract.View {

    private InspectionSheetApplication application;
    private JournalContract.Presenter presenter;

    private JournalListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);

        this.application = (InspectionSheetApplication) this.getApplication();
        this.presenter = new JournalPresenter(this, application);


        setTitle("Журнал событий");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        createJournalView();

        presenter.onViewCreated();
    }


    private void createJournalView(){
        listAdapter = new JournalListAdapter(new ArrayList<>());
        RecyclerView journalList = (RecyclerView) findViewById(R.id.journal_list);
        journalList.setAdapter(listAdapter);
        journalList.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        journalList.addItemDecoration(itemDecoration);
    }

    @Override
    public void setLogMessages(List<LogModel> messages) {
        listAdapter.setLogRecords(messages);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.journal_menu, menu);
        return true;
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

            case R.id.journal_menu_send:
                presenter.sendJournalMenuSelected();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean requestStoragePermissions() {
       return PermissionsUtility.getPermissionExternalStorage(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_WRITE_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        presenter.onStorageAccessGaranted();
                } else {
                     Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    public void sendFileWiaEmail(String fileName) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"nazarov_ag@zes.prim.drsk.ru"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Листы осмотра. Лог");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Листы осмотра. Лог");
        File root = Environment.getExternalStorageDirectory();
        File file = new File(root, fileName);
        if (!file.exists() || !file.canRead()) {
            return;
        }
        //Uri uri = Uri.fromFile(file);

        Uri fileUri = FileProvider.getUriForFile(
                Journal.this,
                "ru.drsk.progserega.inspectionsheet.fileprovider", //(use your app signature + ".provider" )
                file);

        emailIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
        startActivity(Intent.createChooser(emailIntent, "Pick an Email provider"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
