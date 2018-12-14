package ru.drsk.progserega.inspectionsheet.storages.http;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.activities.IProgressListener;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.GeoSubstation;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.GeoSubstationsResponse;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.SteTPResponse;
import ru.drsk.progserega.inspectionsheet.storages.json.SubstationReader;

public class SteAsyncLoader extends AsyncTask<Void, Integer, Void> {


    private IApiSTE apiSTE;

    private IProgressListener progressListener;
    private IRemoteDataArrivedListener dataArrivedListener;

    private static final int PAGE_SIZE = 200;
    private Context context;

    private Exception ex;

    public SteAsyncLoader(IApiSTE apiSTE, IRemoteDataArrivedListener dataArrivedListener, Context context) {
        this.apiSTE = apiSTE;
        this.dataArrivedListener = dataArrivedListener;
        this.context = context;
        ex = null;
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
                //break;
                ex = e;
                return null;
            }

            page++;

            //считаем процент загруженных данных
            int totalPages = (int) Math.ceil((float) total / PAGE_SIZE);
            publishProgress((int) ((page / (float) totalPages) * 100));

        } while ((offset + PAGE_SIZE) < total);

        publishProgress(0);
        loadSubstations();
        publishProgress(100);
        return null;
    }


    public void loadSubstations() {


        //Загрузим из JSON потомучто из сети тоже надо таскать частями иначе не хочет пролазить
        SubstationReader reader = new SubstationReader();
        try {
            GeoSubstationsResponse response = reader.readSubstations(context.getResources().openRawResource(R.raw.substations));
            List<GeoSubstation> list = new ArrayList<GeoSubstation>(response.getData().values());
            dataArrivedListener.GeoSubstationsArrived(list);
        } catch (IOException e) {
            e.printStackTrace();
            //progressListener.progressError(e);
            ex = e;
        }

//        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .connectTimeout(120, TimeUnit.SECONDS)
//                .writeTimeout(120, TimeUnit.SECONDS)
//                .readTimeout(120, TimeUnit.SECONDS)
//                .build();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://api-geo.rs.int") //Базовая часть адреса
//                .client(okHttpClient)
//                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
//                .build();
//
//        IApiGeo apiGeo = retrofit.create(IApiGeo.class); //Создаем объект, при помощи которого будем выполнять запросы
//
//        apiGeo.getAllSubstations("api/get-all-ps").enqueue(new Callback<GeoSubstationsResponse>() {
//            @Override
//            public void onResponse(Call<GeoSubstationsResponse> call, Response<GeoSubstationsResponse> response) {
//                //Данные успешно пришли, но надо проверить response.body() на null
//                int a = 0;
//            }
//            @Override
//            public void onFailure(Call<GeoSubstationsResponse> call, Throwable t) {
//                //Произошла ошибка
//                int a = 0;
//            }
//        });

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
        if (ex != null) {
            progressListener.progressError(ex);
        }
    }

    public void setProgressListener(IProgressListener progressListener) {
        this.progressListener = progressListener;
    }


}
