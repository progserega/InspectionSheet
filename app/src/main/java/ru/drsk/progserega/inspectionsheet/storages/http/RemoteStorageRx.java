package ru.drsk.progserega.inspectionsheet.storages.http;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;


import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import ru.drsk.progserega.inspectionsheet.entities.Settings;
import ru.drsk.progserega.inspectionsheet.entities.inspections.IStationInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectedLine;
import ru.drsk.progserega.inspectionsheet.services.InspectionService;
import ru.drsk.progserega.inspectionsheet.storages.ISettingsStorage;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.AppVersionJson;
import ru.drsk.progserega.inspectionsheet.storages.http.tasks.ExportLineInspectionTask;
import ru.drsk.progserega.inspectionsheet.storages.http.tasks.ExportStationInspectionTask;
import ru.drsk.progserega.inspectionsheet.storages.http.tasks.GetAppVersionTask;
import ru.drsk.progserega.inspectionsheet.storages.http.tasks.LoadDeffectTypesTask;
import ru.drsk.progserega.inspectionsheet.storages.http.tasks.LoadLinesTask;
import ru.drsk.progserega.inspectionsheet.storages.http.tasks.LoadOrganizationTask;
import ru.drsk.progserega.inspectionsheet.storages.http.tasks.LoadSubstationsTask;
import ru.drsk.progserega.inspectionsheet.storages.http.tasks.LoadTPTask;
import ru.drsk.progserega.inspectionsheet.storages.http.tasks.PingServerTask;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.Res;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.IProgressListener;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TransformerInspection;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.UploadRes;
import ru.drsk.progserega.inspectionsheet.storages.http.tasks.ExportTransformerInspectionTask;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.DBDataImporter;

public class RemoteStorageRx implements IRemoteStorage {

    private IApiSTE apiSTE;
    private IApiInspectionSheet apiArmIs;
    private IApiGeo apiGeo;
    private DBDataImporter dbDataImporter;
    private IProgressListener progressListener;
    private Context context;

    private RetrofitApiArmISServiceFactory armISServiceFactory;
    private ISettingsStorage settingsStorage;

    private List< String > serverUrls;

    private InspectionService inspectionService;

    public RemoteStorageRx(DBDataImporter dbDataImporter, Context context, ISettingsStorage settingsStorage, InspectionService inspectionService) {
        this.dbDataImporter = dbDataImporter;
        this.context = context;

        this.inspectionService = inspectionService;

        RetrofitApiSTEServiceFactory apiSTEServiceFactory = new RetrofitApiSTEServiceFactory();
        apiSTE = apiSTEServiceFactory.create();

        this.settingsStorage = settingsStorage;
        armISServiceFactory = new RetrofitApiArmISServiceFactory(settingsStorage);
        apiArmIs = armISServiceFactory.create();

        RetrofitApiGeoServiceFactory apiGeoServiceFactory = new RetrofitApiGeoServiceFactory();
        apiGeo = apiGeoServiceFactory.create();

        serverUrls = new ArrayList<>();
        Settings settings = settingsStorage.loadSettings();
        serverUrls.add(settings.getServerUrl());
        serverUrls.add(settings.getServerAltUrl());

    }

    @Override
    public void setServerUrls(List< String > serverUrls) {
//        armISServiceFactory.setBaseUrl(serverUrl);
//        apiArmIs = armISServiceFactory.create();
        this.serverUrls = serverUrls;
    }

    private void setServerUrl(String serverUrl) {
        armISServiceFactory.setBaseUrl(serverUrl);
        apiArmIs = armISServiceFactory.create();
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
    public void loadDeffectTypes() {
        Observable.create(new LoadDeffectTypesTask(apiArmIs, dbDataImporter, context))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultObserver());
    }


    @Override
    public void loadTP(long spId, long resId) {

        Observable.create(new LoadTPTask(apiArmIs, dbDataImporter, spId, resId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultObserver());

    }

    @Override
    public void loadSubstations(long spId, long resId) {

        Observable.create(new LoadSubstationsTask(apiArmIs, dbDataImporter, spId, resId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultObserver());

    }


    @Override
    public void exportTransformersInspections(List< TransformerInspection > transformerInspections) {
        Observable.create(new ExportTransformerInspectionTask(apiArmIs, transformerInspections, settingsStorage))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultObserver());
    }

    @Override
    public void exportStationsInspections(List< IStationInspection > stationInspections) {
        Observable.create(new ExportStationInspectionTask(apiArmIs, stationInspections, settingsStorage, inspectionService))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultObserver());
    }

    @Override
    public void exportLinesInspections(List< InspectedLine > inspectedLines, long resId) {

        Log.d("EXPORT", "Start export lines inspection");
        Observable.create(new ExportLineInspectionTask(apiArmIs, inspectedLines, resId, inspectionService))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultObserver());

    }

    @Override
    public void getAppVersion(Observer< AppVersionJson > observer ) {
        Observable.create(new GetAppVersionTask(apiArmIs))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void selectActiveServer() {
        progressListener.progressUpdate("Проверка доступности сервера ");
        final Map< String, Boolean > serversAccessMap = new HashMap<>();
        Observable.fromIterable(this.serverUrls).
                flatMap(new Function< String, ObservableSource< Map< String, Boolean > > >() {
                    @Override
                    public ObservableSource< Map< String, Boolean > > apply(String s) throws Exception {
                        setServerUrl(s);
                        return Observable.create(new PingServerTask(apiArmIs, s));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer< Map< String, Boolean > >() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Map< String, Boolean > stringBooleanMap) {
                        serversAccessMap.putAll(stringBooleanMap);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (progressListener != null) {
                            progressListener.progressError((Exception) e);
                        }
                    }

                    @Override
                    public void onComplete() {
                        Log.d("ON COMPLETE", "COMPLETE");
                        boolean isServerAvailable = false;
                        for (Map.Entry< String, Boolean > entry : serversAccessMap.entrySet()) {
                            if (entry.getValue().equals(true)) {
                                setServerUrl(entry.getKey());
                                isServerAvailable = true;
                                break;
                            }
                        }

                        if (isServerAvailable) {
                            progressListener.progressComplete();
                        } else {
                            progressListener.progressError(new Exception("Сервен не доступен"));
                        }
                    }
                });


    }


    private class ResultObserver implements Observer< String > {

        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(String s) {
            if (progressListener != null) {
                progressListener.progressUpdate(s);
            }
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            if (progressListener != null) {
                progressListener.progressError((Exception) e);
            }

        }

        @Override
        public void onComplete() {
            if (progressListener != null) {
                progressListener.progressComplete();
            }
        }
    }
}
