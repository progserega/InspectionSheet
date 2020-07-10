package ru.drsk.progserega.inspectionsheet.storages.http.tasks;

import java.util.List;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import retrofit2.Response;
import ru.drsk.progserega.inspectionsheet.storages.http.IApiInspectionSheet;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.SectionDeffectTypesJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.TowerDeffectTypesJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.StationDeffectTypesJson;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.DBDataImporter;

public class LoadDeffectTypesTask implements ObservableOnSubscribe< String > {

    private static final int ATTEMPT_COUNT = 5;
    private IApiInspectionSheet apiInspectionSheet;

    private DBDataImporter dbDataImporter;

    public LoadDeffectTypesTask(IApiInspectionSheet apiInspectionSheet, DBDataImporter dbDataImporter) {
        this.apiInspectionSheet = apiInspectionSheet;
        this.dbDataImporter = dbDataImporter;

    }

    @Override
    public void subscribe(ObservableEmitter< String > emitter) throws Exception {

        try {

            loadTowerDeffectTypes(emitter);

            loadSectionDeffectTypes(emitter);

            loadTransformersDeffectTypes(emitter);

            loadStationsDeffectTypes(emitter);

            emitter.onComplete();
        } catch (Exception e) {

            e.printStackTrace();
            emitter.onError(e);
        }

        emitter.onComplete();
    }

    private void loadTowerDeffectTypes(ObservableEmitter< String > emitter) throws Exception {
        Response response = null;

        int attempt = 1;
        while (attempt < ATTEMPT_COUNT) {
            try {
                response = apiInspectionSheet.getTowerDeffectsTypes().execute();
                break;
            } catch (java.net.ProtocolException ex) {
                if (attempt == ATTEMPT_COUNT - 1) {
                    throw ex;
                }
            } catch (Exception ex) {
                if (attempt == ATTEMPT_COUNT - 1) {
                    throw ex;
                }
            }
            emitter.onNext("Ошибка при Загрузке типов деффектов опор. Попытка " + String.valueOf(attempt));
            attempt++;

        }
        if (response == null || response.body() == null) {
            return;
        }

        List< TowerDeffectTypesJson > deffectTypesJson = (List< TowerDeffectTypesJson >) response.body();


        if (!deffectTypesJson.isEmpty()) {
            //передаем данные на обработку
            dbDataImporter.loadTowerDeffectTypes(deffectTypesJson);
            emitter.onNext("Типы деффектов опор загружены");
        }


    }

    private void loadSectionDeffectTypes(ObservableEmitter< String > emitter) throws Exception {
        Response response = null;

        int attempt = 1;
        while (attempt < ATTEMPT_COUNT) {
            try {
                response = apiInspectionSheet.getSectionDeffectsTypes().execute();
                break;
            } catch (java.net.ProtocolException ex) {
                if (attempt == ATTEMPT_COUNT - 1) {
                    throw ex;
                }
            } catch (Exception ex) {
                if (attempt == ATTEMPT_COUNT - 1) {
                    throw ex;
                }
            }
            emitter.onNext("Ошибка при загрузке типов деффектов пролетов. Попытка " + String.valueOf(attempt));
            attempt++;

        }
        if (response == null || response.body() == null) {
            return;
        }

        List< SectionDeffectTypesJson > deffectTypesJson = (List< SectionDeffectTypesJson >) response.body();


        if (!deffectTypesJson.isEmpty()) {
            //передаем данные на обработку
            dbDataImporter.loadSectionDeffectTypes(deffectTypesJson);
            emitter.onNext("Типы деффектов пролетов загружены");
        }


    }

    private void loadTransformersDeffectTypes(ObservableEmitter< String > emitter) throws Exception {
        Response response = null;

        int attempt = 1;
        while (attempt < ATTEMPT_COUNT) {
            try {
                response = apiInspectionSheet.getTransformersDeffectsTypes().execute();
                break;
            } catch (java.net.ProtocolException ex) {
                if (attempt == ATTEMPT_COUNT - 1) {
                    throw ex;
                }
            } catch (Exception ex) {
                if (attempt == ATTEMPT_COUNT - 1) {
                    throw ex;
                }
            }
            emitter.onNext("Ошибка при загрузке типов деффектов подстанций и ктп. Попытка " + String.valueOf(attempt));
            attempt++;

        }
        if (response == null || response.body() == null) {
            return;
        }

        List< StationDeffectTypesJson > deffectTypesJson = (List< StationDeffectTypesJson >) response.body();


        if (!deffectTypesJson.isEmpty()) {
            //передаем данные на обработку
            dbDataImporter.loadTransformersDeffectTypes(deffectTypesJson);
            emitter.onNext("Типы деффектов  подстанций и ктп загружены");
        }


    }

    private void loadStationsDeffectTypes(ObservableEmitter< String > emitter) throws Exception {
        Response response = null;

        int attempt = 1;
        while (attempt < ATTEMPT_COUNT) {
            try {
                response = apiInspectionSheet.getStationDeffectsTypes().execute();
                break;
            } catch (java.net.ProtocolException ex) {
                if (attempt == ATTEMPT_COUNT - 1) {
                    throw ex;
                }
            } catch (Exception ex) {
                if (attempt == ATTEMPT_COUNT - 1) {
                    throw ex;
                }
            }
            emitter.onNext("Ошибка при загрузке типов деффектов подстанций и ктп. Попытка " + String.valueOf(attempt));
            attempt++;

        }
        if (response == null || response.body() == null) {
            return;
        }

        List< StationDeffectTypesJson > deffectTypesJson = (List< StationDeffectTypesJson >) response.body();


        if (!deffectTypesJson.isEmpty()) {
            //передаем данные на обработку
            //dbDataImporter.loadTransformersDeffectTypes(deffectTypesJson);

            dbDataImporter.loadStationsDeffectTypes(deffectTypesJson);
            emitter.onNext("Типы деффектов  подстанций и ктп загружены");
        }


    }


}
