package ru.drsk.progserega.inspectionsheet.storages.http.tasks;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import retrofit2.Response;
import ru.drsk.progserega.inspectionsheet.storages.http.IApiInspectionSheet;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.LinesResponseJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.ResModel;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.SpModel;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.SteTPResponse;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.DBDataImporter;

public class LoadLinesTask implements ObservableOnSubscribe< String > {
    private static final int PAGE_SIZE = 1;
    private static final int ATTEMPT_COUNT = 5;
    private IApiInspectionSheet apiInspectionSheet;

    private DBDataImporter dbDataImporter;

    private long resId;


    public LoadLinesTask(IApiInspectionSheet apiInspectionSheet, DBDataImporter dbDataImporter, long resId) {
        this.apiInspectionSheet = apiInspectionSheet;
        this.dbDataImporter = dbDataImporter;
        this.resId = resId;
    }

    @Override
    public void subscribe(ObservableEmitter< String > emitter) throws Exception {

        emitter.onNext("Загрузка Линий... ");
        try {

            int page = 0;
            long total = 0;
            int offset = 0;
            int cnt = 0;
            do {
                offset = page * PAGE_SIZE;
                LinesResponseJson linesResponse = null;
                Response response = null;

                int attempt = 1;
                while (attempt < ATTEMPT_COUNT) {
                    try {
                        response = apiInspectionSheet.getLines(resId, offset, PAGE_SIZE).execute();
                        break;
                    } catch (java.net.ProtocolException ex) {
                        if (attempt == ATTEMPT_COUNT-1 ) {
                            throw ex;
                        }
                    } catch (Exception ex) {
                        if (attempt == ATTEMPT_COUNT -1) {
                            throw ex;
                        }
                    }
                    emitter.onNext("Ошибка при загрузке Линий. Попытка " + String.valueOf(attempt+1));
                    attempt++;

                }
                if (response == null || response.body() == null) {
                    break;
                }

                linesResponse = (LinesResponseJson) response.body();
                total = linesResponse.getTotal();

                if (linesResponse.getData() != null && !linesResponse.getData().isEmpty()) {
                    //передаем данные на обработку
                    cnt += linesResponse.getData().size();
                    int percent = (int) ((cnt / (float) total) * 100);
                    dbDataImporter.loadISLines(linesResponse.getData());
                    emitter.onNext("Загрузка Линий " + String.valueOf(percent) + "%");
                }


                page++;

//                if(page == 5){
//                    break;
//                }

            } while ((offset + PAGE_SIZE) < total);
            emitter.onComplete();
        } catch (Exception e) {

            e.printStackTrace();
            emitter.onError(e);
        }

        emitter.onComplete();
    }


}
