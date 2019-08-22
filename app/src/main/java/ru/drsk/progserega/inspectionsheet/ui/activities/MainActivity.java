package ru.drsk.progserega.inspectionsheet.ui.activities;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.util.Log;
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
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectedLine;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TransformerInspection;
import ru.drsk.progserega.inspectionsheet.services.InspectionService;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.IProgressListener;

/*
http://wiki.rs.int/doku.php/osm:api#список_всех_линий
http://wiki.rs.int/doku.php/osm:api#данные_линии_по_имени
 */
public class MainActivity extends AppCompatActivity implements IProgressListener {

    private InspectionSheetApplication application;
    private ProgressBar progressBar;
    private TextView progressText;

    private static final boolean DEBUG_MODE = true;

    private static final String EXPORT_TRANSFORMERS = "export_transformers";
    private static final String EXPORT_LINES = "export_lines";

    private Queue< String > networkTasksQueue = new LinkedList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.application = (InspectionSheetApplication) this.getApplication();

        progressBar = (ProgressBar) findViewById(R.id.loading_progress);
        progressText = (TextView) findViewById(R.id.loading_progress_text);

        application.getRemoteStorage().setProgressListener(this);
        networkTasksQueue.clear();
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

        application.getRemoteStorage().loadRemoteData();

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

        Log.d("EXPORT ", "EXPORT: " + task);

        switch (task) {
            case EXPORT_TRANSFORMERS:
                exportTransformers();
                return;
            case EXPORT_LINES:
                exportLines();
                return;
        }
    }

    private void exportTransformers() {
        InspectionService inspectionService = application.getInspectionService();
        List< TransformerInspection > inspections = inspectionService.getInspectionByEquipment(EquipmentType.SUBSTATION);

        application.getRemoteStorage().exportTransformersInspections(inspections);
    }

    private void exportLines(){

        List< InspectedLine > inspectedLines = application.getInspectionService().getInspectedLines();
        application.getRemoteStorage().exportLinesInspections(inspectedLines);
    }
}
