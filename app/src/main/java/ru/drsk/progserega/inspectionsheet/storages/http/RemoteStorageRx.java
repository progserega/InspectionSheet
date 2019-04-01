package ru.drsk.progserega.inspectionsheet.storages.http;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;


import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.activities.IProgressListener;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TransformerInspection;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.UploadRes;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.GeoSubstation;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.GeoSubstationsResponse;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.SteTPResponse;
import ru.drsk.progserega.inspectionsheet.storages.http.tasks.LoadTpTask;
import ru.drsk.progserega.inspectionsheet.storages.http.tasks.UploadTransformerInspectionResults;
import ru.drsk.progserega.inspectionsheet.storages.json.SubstationReader;
import ru.drsk.progserega.inspectionsheet.storages.json.SubstationTransformersReader;
import ru.drsk.progserega.inspectionsheet.storages.json.models.SubstationTransformerJson;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.DBDataImporter;

public class RemoteStorageRx implements IRemoteStorage {

    private IApiSTE apiSTE;
    private IApiInspectionSheet apiArmIs;
    private DBDataImporter dbDataImporter;
    private IProgressListener progressListener;
    private Context context;

    public RemoteStorageRx(DBDataImporter dbDataImporter, Context context) {
        this.dbDataImporter = dbDataImporter;
        this.context = context;

        RetrofitApiSTEServiceFactory apiSTEServiceFactory = new RetrofitApiSTEServiceFactory();
        apiSTE = apiSTEServiceFactory.create();

        RetrofitApiArmISServiceFactory armISServiceFactory = new RetrofitApiArmISServiceFactory();
        apiArmIs = armISServiceFactory.create();

    }

    @Override
    public void loadRemoteData() {

        dbDataImporter.ClearDB();
        dbDataImporter.initEnterpriseCache();

        Observable.create(new LoadTpTask(apiSTE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SteTPResponse>() {
                               private int cnt = 0;

                               @Override
                               public void onSubscribe(Disposable d) {

                               }

                               @Override
                               public void onNext(SteTPResponse steTPResponse) {

                                   dbDataImporter.loadSteTpModel(steTPResponse.getData());
                                   cnt += steTPResponse.getData().size();
                                   progressListener.progressUpdate((int) ((cnt / (float) steTPResponse.getTotalRecords()) * 100));
                               }

                               @Override
                               public void onError(Throwable e) {
                                   e.printStackTrace();
                               }

                               @Override
                               public void onComplete() {
                                   loadSubstations();
                                   loadSubstationTransformers();
                                   progressListener.progressComplete();


                               }
                           }

                );

    }

    private void loadSubstations() {

        //Загрузим из JSON потомучто из сети тоже надо таскать частями иначе не хочет пролазить
        SubstationReader reader = new SubstationReader();
        try {
            GeoSubstationsResponse response = reader.readSubstations(context.getResources().openRawResource(R.raw.substations));
            List<GeoSubstation> list = new ArrayList<GeoSubstation>(response.getData().values());
           // dataArrivedListener.GeoSubstationsArrived(list);
            dbDataImporter.loadGeoSubstations(list);
        } catch (IOException e) {
            e.printStackTrace();
            progressListener.progressError(e);
        }
    }

    private void loadSubstationTransformers(){

        SubstationTransformersReader reader = new SubstationTransformersReader();
        try {
            List<SubstationTransformerJson> transformersJson = reader.readSubstationTransformers(context.getResources().openRawResource(R.raw.substation_transformers));
             dbDataImporter.loadSubstationTransformers(transformersJson);
        } catch (IOException e) {
            e.printStackTrace();
            progressListener.progressError(e);
        }
    }

    @Override
    public void setProgressListener(IProgressListener progressListener) {
        this.progressListener = progressListener;
    }

    @Override
    public void uploadTransformersInspections(List<TransformerInspection> transformerInspections) {
        Observable.create(new UploadTransformerInspectionResults(apiArmIs, transformerInspections ))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UploadRes>() {
                               private int cnt = 0;

                               @Override
                               public void onSubscribe(Disposable d) {

                               }

                               @Override
                               public void onNext(UploadRes uploadRes) {

//                                   dbDataImporter.loadSteTpModel(steTPResponse.getData());
//                                   cnt += steTPResponse.getData().size();
//                                   progressListener.progressUpdate((int) ((cnt / (float) steTPResponse.getTotalRecords()) * 100));
                               }

                               @Override
                               public void onError(Throwable e) {
                                   e.printStackTrace();
                               }

                               @Override
                               public void onComplete() {
                                   progressListener.progressComplete();
                               }
                           }

                );

    }


}
