package ru.drsk.progserega.inspectionsheet.storages.http;

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.activities.IProgressListener;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.GeoSubstation;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.GeoSubstationsResponse;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.SteTPModel;
import ru.drsk.progserega.inspectionsheet.storages.json.SubstationReader;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.DBDataImporter;

public class RemoteSorage implements IRemoteStorage, IRemoteDataArrivedListener {

   // private SteAsyncLoader steAsyncLoader;
    private IApiSTE apiSTE;
    private DBDataImporter dbDataImporter;
    private IProgressListener progressListener;
    private Context context;

    public RemoteSorage(DBDataImporter dbDataImporter, Context context) {
        this.dbDataImporter = dbDataImporter;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api-ste.rs.int") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();

        apiSTE = retrofit.create(IApiSTE.class); //Создаем объект, при помощи которого будем выполнять запросы

        this.context = context;
    }


    @Override
    public void setProgressListener(IProgressListener progressListener) {
        this.progressListener = progressListener;
    }

    @Override
    public void loadRemoteData() {
        //Надо каждый раз создавать новую асинхронную задачу
        SteAsyncLoader steAsyncLoader = new SteAsyncLoader(apiSTE, this, context);
        steAsyncLoader.setProgressListener(this.progressListener);
        steAsyncLoader.execute();
    }

    /**
     * Этот метод вызывается когда прилетел кусок данных
     * от апи с сервера
     *
     * @param tpModels список моделей трансформаторной подстанции
     */
    @Override
    public void SteTPModelsArrived(List<SteTPModel> tpModels) {
        dbDataImporter.loadSteTpModelWithClean(tpModels);
    }

    @Override
    public void GeoSubstationsArrived(List<GeoSubstation> substations) {
       dbDataImporter.loadGeoSubstationsWithClean(substations);
    }
}
