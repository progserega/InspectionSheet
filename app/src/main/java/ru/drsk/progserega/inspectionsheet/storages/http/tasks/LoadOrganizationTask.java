package ru.drsk.progserega.inspectionsheet.storages.http.tasks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import retrofit2.Response;
import ru.drsk.progserega.inspectionsheet.storages.http.IApiGeo;
import ru.drsk.progserega.inspectionsheet.storages.http.IApiInspectionSheet;
import ru.drsk.progserega.inspectionsheet.storages.http.IApiSTE;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.ResModel;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.SpModel;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.SubstationsResponse;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.TransformerType;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.GeoLine;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.GeoLineDetail;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.GeoLineDetailResponse;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.GeoLinesResponse;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.SteTPResponse;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.DBDataImporter;

public class LoadOrganizationTask implements ObservableOnSubscribe< String > {
    private static final int PAGE_SIZE = 200;
    private static final int ATTEMPT_COUNT = 5;
    private IApiInspectionSheet apiInspectionSheet;

    private DBDataImporter dbDataImporter;


    public LoadOrganizationTask(IApiInspectionSheet apiInspectionSheet, DBDataImporter dbDataImporter) {
        this.apiInspectionSheet = apiInspectionSheet;
        this.dbDataImporter = dbDataImporter;
    }

    @Override
    public void subscribe(ObservableEmitter< String > emitter) throws Exception {

        try {
            emitter.onNext("Загружаем список СП");
            loadSp(emitter);

            emitter.onNext("Загружаем список РЭС");
            loadRes(emitter);

           // dbDataImporter.initEnterpriseCache();

        } catch (IOException e) {

            e.printStackTrace();
            emitter.onError(e);
        }

        emitter.onComplete();
    }

    private void loadSp(ObservableEmitter< String > emitter) throws IOException {
        Response response = apiInspectionSheet.getAllSp().execute();
        if (response.body() == null) {
            throw new IOException("Данные по СП не получены. response.body is null");
        }

        List< SpModel > spModels = (List< SpModel >) response.body();

        if (spModels == null || spModels.isEmpty()) {
            throw new IOException("Данные по СП не получены");
        }

        for (SpModel sp : spModels) {
            //android.os.SystemClock.sleep(2000);
            dbDataImporter.addSp(sp.getId(), sp.getName());
            emitter.onNext(sp.getName());
        }
    }

    private void loadRes(ObservableEmitter< String > emitter) throws IOException {
        Response response = apiInspectionSheet.getAllRes().execute();
        if (response.body() == null) {
            throw new IOException("Данные по РЭС не получены. response.body is null");
        }

        List< ResModel > resModels = (List< ResModel >) response.body();

        if (resModels == null || resModels.isEmpty()) {
            throw new IOException("Данные по РЭС не получены. response.body is null");
        }

        for (ResModel res : resModels) {
            //android.os.SystemClock.sleep(500);
            dbDataImporter.addRes(res.getId(), res.getSpId(), res.getName(), res.getAlterName());
            emitter.onNext(res.getName() + " " + res.getAlterName());
        }
    }

}
