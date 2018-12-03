package ru.drsk.progserega.inspectionsheet.storages.http;

import android.os.AsyncTask;

import java.io.IOException;

import retrofit2.Response;
import retrofit2.Retrofit;
import ru.drsk.progserega.inspectionsheet.activities.IProgressListener;
import ru.drsk.progserega.inspectionsheet.storages.IOrganizationStorage;
import ru.drsk.progserega.inspectionsheet.storages.ITransformerStorage;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.SteTPResponse;

public class SteAsyncLoader extends AsyncTask<Void, Integer, Void> {


    private IApiSTE apiSTE;

    private IProgressListener progressListener;
    private IRemoteDataArrivedListener dataArrivedListener;

    private static final int PAGE_SIZE = 200;


    public SteAsyncLoader(IApiSTE apiSTE, IRemoteDataArrivedListener dataArrivedListener) {
        this.apiSTE = apiSTE;
        this.dataArrivedListener = dataArrivedListener;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        int page = 0;
        int total = 0;
        int offset = 0;
        do {
            offset = page * PAGE_SIZE;
            SteTPResponse tpResponse = null;
            try {
                Response response = apiSTE.getTPbyRange("api/all-tp-by-range", offset, PAGE_SIZE).execute();
                if (response.body() == null) {
                    break;
                }

                tpResponse = (SteTPResponse) response.body();
                total = tpResponse.getTotalRecords();

                if (tpResponse.getData() != null && !tpResponse.getData().isEmpty()) {
                    //передаем данные на обработку
                    dataArrivedListener.SteTPModelsArrived(tpResponse.getData());
                }

            } catch (IOException e) {
                e.printStackTrace();
                break;
            }

            page++;

            //считаем процент загруженных данных
            int totalPages = (int) Math.ceil((float) total / PAGE_SIZE);
            publishProgress((int) ((page / (float) totalPages) * 100));

        } while ((offset + PAGE_SIZE) < total);
        return null;
    }

    protected void onProgressUpdate(Integer... progress) {
        if (progressListener != null) {
            progressListener.progressUpdate(progress[0]);
        }
    }

    protected void onPostExecute(Void result) {
        if (progressListener != null) {
            progressListener.progressComplete();
        }
    }

    public void setProgressListener(IProgressListener progressListener) {
        this.progressListener = progressListener;
    }


}
