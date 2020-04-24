package ru.drsk.progserega.inspectionsheet.ui.presenters;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.InspectionSheetApplication;
import ru.drsk.progserega.inspectionsheet.entities.Settings;
import ru.drsk.progserega.inspectionsheet.entities.organization.ElectricNetworkArea;
import ru.drsk.progserega.inspectionsheet.entities.organization.NetworkEnterprise;
import ru.drsk.progserega.inspectionsheet.storages.IOrganizationStorage;
import ru.drsk.progserega.inspectionsheet.storages.ISettingsStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.LogStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.LogModel;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.IProgressListener;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.JournalContract;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.SettingsContract;

public class JournalPresenter implements JournalContract.Presenter {

    private JournalContract.View view;
    private InspectionSheetApplication application;

    boolean wantExportLog = false;

    public JournalPresenter(JournalContract.View view, InspectionSheetApplication application) {
        this.view = view;
        this.application = application;

    }

    @Override
    public void onViewCreated() {

        wantExportLog = false;

        application.getLogStorage().getMessages(new LogStorage.IDataFetchedListener() {
            @Override
            public void onDataFetched(List<LogModel> messages) {
                view.setLogMessages(messages);
            }
        });

    }

    @Override
    public void sendJournalMenuSelected() {
        wantExportLog = true;
        if (!view.requestStoragePermissions()) {
            Log.d("JOURNAL", "Can NOT WRITE FILE");
            return;
        }
        exportLog();
    }

    @Override
    public void onStorageAccessGaranted() {
        Log.d("JOURNAL", "PERMISSIONS NOW GARANTED. Can WRITE FILE");
        if (!wantExportLog) {
            return;
        }
        exportLog();
    }

    private void exportLog() {
        wantExportLog = false;

        List<LogModel> messages = application.getLogStorage().getAllMessages();
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        String messagesJson = gson.toJson(messages);
        Log.d("JOURNAL", messagesJson);

        try {
            generateNoteOnSD(application.getApplicationContext(), "InspectionSheetLogs.json", messagesJson);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        view.sendFileWiaEmail("tmp/InspectionSheetLogs.json");
    }

    public void generateNoteOnSD(Context context, String sFileName, String sBody) throws IOException {

        File root = new File(Environment.getExternalStorageDirectory(), "tmp");
        if (!root.exists()) {
            root.mkdirs();
        }
        File gpxfile = new File(root, sFileName);
        FileWriter writer = new FileWriter(gpxfile);
        writer.append(sBody);
        writer.flush();
        writer.close();
        Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDestroy() {
        this.view = null;
    }
}
