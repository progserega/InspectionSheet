package ru.drsk.progserega.inspectionsheet.storages.http.tasks;

import java.io.IOException;
import java.util.List;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import retrofit2.Response;
import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;
import ru.drsk.progserega.inspectionsheet.storages.http.IApiGeo;
import ru.drsk.progserega.inspectionsheet.storages.http.IApiInspectionSheet;
import ru.drsk.progserega.inspectionsheet.storages.http.IApiSTE;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.SubstationsResponse;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.TPResponse;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.TransformerType;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.DBDataImporter;

public class LoadTPTask implements ObservableOnSubscribe< String > {
    private static final int PAGE_SIZE = 200;
    private static final int ATTEMPT_COUNT = 5;
    private IApiInspectionSheet apiInspectionSheet;

    private DBDataImporter dbDataImporter;
    private long spId = 0;
    private long resId = 0;


    public LoadTPTask(IApiInspectionSheet apiInspectionSheet, DBDataImporter dbDataImporter, long spId, long resId) {
        this.apiInspectionSheet = apiInspectionSheet;
        this.dbDataImporter = dbDataImporter;
        this.spId = spId;
        this.resId = resId;
    }

    @Override
    public void subscribe(ObservableEmitter< String > emitter) throws Exception {

        try {
            emitter.onNext("Загружаем список типов трансформаторов ТП");
            loadTPTransformersTypes(emitter);

            emitter.onNext("Загружаем список ТП");
            loadTP(emitter);



        } catch (IOException e) {

            e.printStackTrace();
            emitter.onError(e);
        }

        emitter.onComplete();
    }


    private void loadTPTransformersTypes(ObservableEmitter< String > emitter) throws IOException {
        Response response = apiInspectionSheet.geTpTransformersTypes().execute();
        if (response.body() == null) {
            throw new IOException("Данные по Типам трансформаторов не получены. response.body is null");
        }

        List< TransformerType > types = (List< TransformerType >) response.body();

        if (types == null || types.isEmpty()) {
            throw new IOException("Данные по СП не получены");
        }
        //dbDataImporter.loadSubstationTransformers(types, "transformer_substation");
        dbDataImporter.importStationEquipmentModels(types, EquipmentType.TP_TRANSFORMER);
    }


    private void loadTP(ObservableEmitter< String > emitter) throws IOException {
        int page = 0;
        int total = 0;
        int offset = 0;
        int cnt = 0;
        do {
            offset = page * PAGE_SIZE;
            TPResponse substationsResponse = null;

            Response response = null;
            int attempt = 1;
            while (attempt < ATTEMPT_COUNT) {
                try {
                    response = apiInspectionSheet.getTP(spId, resId, offset, PAGE_SIZE).execute();
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
                emitter.onNext("Ошибка при загрузке ТП. Попытка " +attempt);
                attempt++;

            }
            if (response == null || response.body() == null) {
                break;
            }

            substationsResponse = (TPResponse) response.body();
            total = substationsResponse.getTotal();

            if (substationsResponse.getData() != null && !substationsResponse.getData().isEmpty()) {
                //передаем данные на обработку
                cnt += substationsResponse.getData().size();
                int percent = (int) ((cnt / (float) substationsResponse.getTotal()) * 100);
                dbDataImporter.loadTps(substationsResponse.getData());
                emitter.onNext("Загрузка ТП " + percent + "%");
            }


            page++;


        } while ((offset + PAGE_SIZE) < total);

    }
}
