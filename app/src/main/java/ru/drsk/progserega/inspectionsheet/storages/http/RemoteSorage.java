package ru.drsk.progserega.inspectionsheet.storages.http;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.drsk.progserega.inspectionsheet.activities.IProgressListener;
import ru.drsk.progserega.inspectionsheet.storages.IOrganizationStorage;
import ru.drsk.progserega.inspectionsheet.storages.ITransformerStorage;
import ru.drsk.progserega.inspectionsheet.storages.ITransformerSubstationStorage;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.SteTPModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.DBDataImporter;

public class RemoteSorage implements IRemoteStorage, IRemoteDataArrivedListener {

    private SteAsyncLoader steAsyncLoader;
    private DBDataImporter dbDataImporter;
    public RemoteSorage(DBDataImporter dbDataImporter) {
        this.dbDataImporter = dbDataImporter;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api-ste.rs.int") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();

        IApiSTE apiSTE = retrofit.create(IApiSTE.class); //Создаем объект, при помощи которого будем выполнять запросы

        steAsyncLoader = new SteAsyncLoader(apiSTE, this);

    }


    @Override
    public void setProgressListener(IProgressListener progressListener) {
        steAsyncLoader.setProgressListener(progressListener);
    }

    @Override
    public void loadTrasformerSubstations() {
        steAsyncLoader.execute(new Void[0]);
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
}
