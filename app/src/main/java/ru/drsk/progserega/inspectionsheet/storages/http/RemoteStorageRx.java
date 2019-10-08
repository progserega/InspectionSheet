package ru.drsk.progserega.inspectionsheet.storages.http;

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;


import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectedLine;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineInspection;
import ru.drsk.progserega.inspectionsheet.storages.http.tasks.ExportLineInspectionTask;
import ru.drsk.progserega.inspectionsheet.storages.http.tasks.LoadLinesTask;
import ru.drsk.progserega.inspectionsheet.storages.http.tasks.LoadOrganizationTask;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.IProgressListener;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TransformerInspection;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.UploadRes;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.GeoSubstation;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.GeoSubstationsResponse;
import ru.drsk.progserega.inspectionsheet.storages.http.tasks.LoadAllDataTask;
import ru.drsk.progserega.inspectionsheet.storages.http.tasks.ExportTransformerInspectionTask;
import ru.drsk.progserega.inspectionsheet.storages.json.SubstationReader;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.DBDataImporter;

public class RemoteStorageRx implements IRemoteStorage {

    private IApiSTE apiSTE;
    private IApiInspectionSheet apiArmIs;
    private IApiGeo apiGeo;
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

        RetrofitApiGeoServiceFactory apiGeoServiceFactory = new RetrofitApiGeoServiceFactory();
        apiGeo = apiGeoServiceFactory.create();
    }

    @Override
    public void setProgressListener(IProgressListener progressListener) {
        this.progressListener = progressListener;
    }

    @Override
    public void clearStorage() {

        Observable.fromCallable(new Callable< String >() {
                    @Override
                    public String call() throws Exception {
                        dbDataImporter.ClearDB();
                        return "БД Очищена";
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultObserver());
    }

    @Override
    public void loadOrganization() {
        Observable.create(new LoadOrganizationTask(apiArmIs, dbDataImporter))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultObserver());
    }

    @Override
    public void loadLines(long resId) {
        Observable.create(new LoadLinesTask(apiArmIs, dbDataImporter, resId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultObserver());
    }

    @Override
    public void loadRemoteData() {

        Observable.create(new LoadAllDataTask(apiArmIs, apiSTE, apiGeo, dbDataImporter))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultObserver());

    }


    @Override
    public void exportTransformersInspections(List< TransformerInspection > transformerInspections) {
        Observable.create(new ExportTransformerInspectionTask(apiArmIs, transformerInspections))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer< UploadRes >() {
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
                                   progressListener.progressComplete();
                               }

                               @Override
                               public void onComplete() {
                                   progressListener.progressComplete();
                               }
                           }

                );

    }

    @Override
    public void exportLinesInspections(List< InspectedLine > inspectedLines) {

        Observable.create(new ExportLineInspectionTask(apiArmIs, inspectedLines))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultObserver() );

    }

    private class ResultObserver implements Observer< String > {

        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(String s) {
            progressListener.progressUpdate(s);
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            progressListener.progressError((Exception) e);

        }

        @Override
        public void onComplete() {
            progressListener.progressComplete();
        }
    }
}
