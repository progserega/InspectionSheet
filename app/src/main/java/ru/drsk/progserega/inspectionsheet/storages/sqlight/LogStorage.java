package ru.drsk.progserega.inspectionsheet.storages.sqlight;

import android.os.AsyncTask;

import java.util.Date;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.drsk.progserega.inspectionsheet.storages.ILogStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.LogModel;

//https://android.jlelse.eu/5-steps-to-implement-room-persistence-library-in-android-47b10cd47b24

public class LogStorage implements ILogStorage {

    private InspectionSheetDatabase db;

    public LogStorage(InspectionSheetDatabase db) {
        this.db = db;
    }

    @Override
    public void add(int level, String tag, String message) {

        LogModel logRecord = new LogModel(new Date(), level, tag, message);
       // db.logDao().insert(logRecord);
        insertTask(logRecord);
    }

    private void insertTask(final LogModel logRec) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                db.logDao().insert(logRec);
                return null;
            }
        }.execute();
    }
}
