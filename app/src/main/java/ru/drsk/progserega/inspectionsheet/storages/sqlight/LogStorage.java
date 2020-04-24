package ru.drsk.progserega.inspectionsheet.storages.sqlight;

import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.Date;
import java.util.List;

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

    public interface IDataFetchedListener {
        void onDataFetched(List<LogModel> messages);
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

    @Override
    public void getMessages(final IDataFetchedListener listener) {
        //return db.logDao().fetchAllMessages();

        FetchDataTask task = new FetchDataTask();
        task.setListener(listener);
        task.execute();

    }

    @Override
    public List<LogModel> getAllMessages() {
        return db.logDao().allMessages();
    }

    private class FetchDataTask extends AsyncTask<Void, Void, List<LogModel>>
    {
        private IDataFetchedListener listener;

        public void setListener(IDataFetchedListener listener) {
            this.listener = listener;
        }

        @Override
        protected List<LogModel> doInBackground(Void... voids) {
            return db.logDao().fetchAllMessages();
        }

        @Override
        protected void onPostExecute(List<LogModel> logModels) {
            super.onPostExecute(logModels);
            listener.onDataFetched(logModels);
        }
    }
}
