package ru.drsk.progserega.inspectionsheet.ui.activities;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;
import ru.drsk.progserega.inspectionsheet.entities.Settings;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectedLine;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TransformerInspection;
import ru.drsk.progserega.inspectionsheet.services.InspectionService;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.IProgressListener;

/*
http://wiki.rs.int/doku.php/osm:api#список_всех_линий
http://wiki.rs.int/doku.php/osm:api#данные_линии_по_имени
 */
public class MainActivity extends AppCompatActivity implements IProgressListener, SelectOrganizationDialogFragment.ISelectOrganizationListener {

    private InspectionSheetApplication application;
    private ProgressBar progressBar;
    private TextView progressText;
    private SelectOrganizationDialogFragment selectOrganizationDlog;
    private long enterpriseId = 0;
    private long areaId = 0;

    private static final boolean DEBUG_MODE = true;


    private static final String EXPORT_TRANSFORMERS = "export_transformers";
    private static final String EXPORT_LINES = "export_lines";
    private static final String CLEAR_DB = "clear_db";
    private static final String LOAD_ORGANIZATION = "load_organization";
    private static final String SELECT_RES = "select_res";
    private static final String LOAD_LINES = "load_lines";
    private static final String LOAD_DATA = "load_data";

    private Queue< String > networkTasksQueue = new LinkedList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.application = (InspectionSheetApplication) this.getApplication();
        setTitle("Листы осмотров");


        progressBar = (ProgressBar) findViewById(R.id.loading_progress);
        progressText = (TextView) findViewById(R.id.loading_progress_text);

        //application.getRemoteStorage().setProgressListener(this);
        networkTasksQueue.clear();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // получим идентификатор выбранного пункта меню
        int id = item.getItemId();
        // Операции для выбранного пункта меню
        switch (id) {
            case R.id.menu_settings:
                // Toast.makeText(this, "SettingsActivity", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_about:
                //   Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    public void addDefect(View view) {
        Intent intent = new Intent(this, SelectTypeLine.class);
        startActivity(intent);
    }

    public void syncData(View view) {
        showQuestion("Загрузить данные с сервера?", "Важно! Все предыдущие данные будут очищены");
    }

    private void showQuestion(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        loadData();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        builder.create().show();
    }


    private void loadData() {

        showProgress();

        application.getRemoteStorage().setProgressListener(this);

        networkTasksQueue.clear();

        networkTasksQueue.add(SELECT_RES);
        networkTasksQueue.add(CLEAR_DB);
        // networkTasksQueue.add(LOAD_ORGANIZATION);
        networkTasksQueue.add(LOAD_LINES);
        // networkTasksQueue.add(LOAD_DATA);

        nextTask();
    }

    @Override
    public void progressUpdate(String progress) {
        Log.d("PROGRESS", "PROGRESS: " + progress);
        progressText.setText(progress);
    }

    @Override
    public void progressComplete() {
        hideProgress();

        networkTasksQueue.poll();
        nextTask();
    }

    private void showProgress() {
        progressText.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progressText.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

    }

    @Override
    public void progressError(Exception ex) {
        String s = ex.getLocalizedMessage();
        if (DEBUG_MODE) {
            Writer writer = new StringWriter();
            ex.printStackTrace(new PrintWriter(writer));
            s = writer.toString();
        }
        showError("Ошибка", s);
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void showError(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.create().show();
    }

    public void exportInspections(View view) {

        //networkTasksQueue.add(EXPORT_TRANSFORMERS);
        networkTasksQueue.add(EXPORT_LINES);


        nextTask();
    }

    private void nextTask() {
        showProgress();

        String task = networkTasksQueue.peek();
        if (task == null) {
            hideProgress();
            return;
        }

        Log.d("NETWORK_TASK ", "TASK IS: " + task);

        switch (task) {
            case EXPORT_TRANSFORMERS:
                exportTransformers();
                return;
            case EXPORT_LINES:
                exportLines();
                return;
            case CLEAR_DB:
                application.getRemoteStorage().clearStorage();
                return;
            case LOAD_ORGANIZATION:
                application.getRemoteStorage().loadOrganization();
                return;
            case SELECT_RES:
                selectOrganization();
                return;
            case LOAD_DATA:
                application.getRemoteStorage().loadRemoteData();
                return;
            case LOAD_LINES:
                application.getRemoteStorage().loadLines(this.areaId);
                return;

        }
    }

    private void exportTransformers() {
        InspectionService inspectionService = application.getInspectionService();
        List< TransformerInspection > inspections = inspectionService.getInspectionByEquipment(EquipmentType.SUBSTATION);

        application.getRemoteStorage().exportTransformersInspections(inspections);
    }

    private void exportLines() {

        List< InspectedLine > inspectedLines = application.getInspectionService().getInspectedLines();
        application.getRemoteStorage().exportLinesInspections(inspectedLines);
    }


    public void selectOrganization() {
        Settings settings = application.getSettingsStorage().loadSettings();
       // settings.setResId(0);
        if (settings.getResId() == 0) {
            hideProgress();
            showError("Не выбран Район Электрических Сетей", "Перейдите в Меню -> Насройки и укажите район");
            return;
        }

        this.areaId = settings.getResId();
        networkTasksQueue.poll();
        nextTask();
//        FragmentManager fm = getSupportFragmentManager();
//        if (selectOrganizationDlog == null) {
//            selectOrganizationDlog = SelectOrganizationDialogFragment.newInstance(this.application.getOrganizationService());
//        }
//        selectOrganizationDlog.Show(fm, enterpriseId, areaId);
    }

    @Override
    public void onSelectOrganization(long enterpriseId, long areaId) {

        this.enterpriseId = enterpriseId;
        this.areaId = areaId;

        //Toast.makeText(this, "Enterprise is =" + enterpriseId + " RES ID = " + areaId, Toast.LENGTH_LONG).show();

        if (this.areaId == 0) {
            showError("Ошибка", "Не выбран РЭС");
            return;
        }

        networkTasksQueue.poll();
        nextTask();
    }
}
