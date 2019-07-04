package ru.drsk.progserega.inspectionsheet.storages.http;

import android.content.Context;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.IProgressListener;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TransformerInspection;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.GeoSubstation;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.SteTPModel;
import ru.drsk.progserega.inspectionsheet.storages.json.models.SubstationTransformerJson;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.DBDataImporter;

public class RemoteSorage implements IRemoteStorage, IRemoteDataArrivedListener {

   // private SteAsyncLoader steAsyncLoader;
    private IApiSTE apiSTE;
    private DBDataImporter dbDataImporter;
    private IProgressListener progressListener;
    private Context context;
    private  IApiInspectionSheet apiIS;


    public RemoteSorage(DBDataImporter dbDataImporter, Context context) {
        this.dbDataImporter = dbDataImporter;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api-ste.rs.int") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();

        apiSTE = retrofit.create(IApiSTE.class); //Создаем объект, при помощи которого будем выполнять запросы

        this.context = context;

        Retrofit retrofitInspectionBackend = new Retrofit.Builder()
                .baseUrl("http://172.21.168.71:3010") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();

        apiIS = retrofitInspectionBackend.create(IApiInspectionSheet.class);


    }


    @Override
    public void setProgressListener(IProgressListener progressListener) {
        this.progressListener = progressListener;
    }

    @Override
    public void loadRemoteData() {

       // dbDataImporter.loadInspectionItems(context);
       // if(true) return;
        dbDataImporter.ClearDB();
        dbDataImporter.initEnterpriseCache();

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
        dbDataImporter.loadSteTpModel(tpModels);
    }

    @Override
    public void GeoSubstationsArrived(List<GeoSubstation> substations) {
       dbDataImporter.loadGeoSubstations(substations);
    }

    @Override
    public void SubstationTransformersArrived(List<SubstationTransformerJson> transformers){
        dbDataImporter.loadSubstationTransformers(transformers);
    }

    @Override
    public void uploadTransformersInspections(List<TransformerInspection> transformerInspections){
        //Надо каждый раз создавать новую асинхронную задачу
        InspectionResultsAsyncUploader inspectionResultsAsyncUploader = new InspectionResultsAsyncUploader(apiIS, transformerInspections,this);
        inspectionResultsAsyncUploader.setProgressListener(this.progressListener);
        inspectionResultsAsyncUploader.execute();

    }

}
