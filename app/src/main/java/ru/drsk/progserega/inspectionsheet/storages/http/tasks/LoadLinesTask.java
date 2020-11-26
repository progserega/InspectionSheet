package ru.drsk.progserega.inspectionsheet.storages.http.tasks;

import android.text.TextUtils;

import java.io.IOException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import retrofit2.Response;
import ru.drsk.progserega.inspectionsheet.storages.http.IApiInspectionSheet;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.ApiGetResponce;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.LineData;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.LineInfo;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.ResLine;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.DBDataImporter;

public class LoadLinesTask implements ObservableOnSubscribe< String > {
    private static final int PAGE_SIZE = 10;
    private static final int ATTEMPT_COUNT = 10;
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
//        try {
//
//            int page = 0;
//            long total = 0;
//            int offset = 0;
//            int cnt = 0;
//            do {
//                offset = page * PAGE_SIZE;
//                LinesResponseJson linesResponse = null;
//                Response response = null;
//
//                int attempt = 1;
//                while (attempt <= ATTEMPT_COUNT) {
//                    try {
//                        response = apiInspectionSheet.getLines(resId, offset, PAGE_SIZE).execute();
//                        break;
//                    } catch (java.net.ProtocolException ex) {
//                        if (attempt == ATTEMPT_COUNT ) {
//                            throw ex;
//                        }
//                    } catch (Exception ex) {
//                        if (attempt == ATTEMPT_COUNT) {
//                            throw ex;
//                        }
//                    }
//                    emitter.onNext("Ошибка при загрузке Линий. Попытка " + String.valueOf(attempt+1));
//                    attempt++;
//
//                }
//                if (response == null || response.body() == null) {
//                    break;
//                }
//
//                linesResponse = (LinesResponseJson) response.body();
//                total = linesResponse.getTotal();
//
//                if (linesResponse.getData() != null && !linesResponse.getData().isEmpty()) {
//                    //передаем данные на обработку
//                    cnt += linesResponse.getData().size();
//                    int percent = (int) ((cnt / (float) total) * 100);
//                    dbDataImporter.loadISLines(linesResponse.getData());
//                    emitter.onNext("Загрузка Линий " + String.valueOf(percent) + "%");
//                }
//
//
//                page++;
//
////                if(page == 5){
////                    break;
////                }
//
//            } while ((offset + PAGE_SIZE) < total);
//            emitter.onComplete();
//        } catch (Exception e) {
//
//            e.printStackTrace();
//            emitter.onError(e);
//        }


        List< ResLine > resLines = new ArrayList<>();
        try {
            resLines = getResLines(resId, emitter);

            batchLoadLines(resLines, emitter);

        } catch (Exception e) {
            e.printStackTrace();
            emitter.onError(e);
            return;
        }



        emitter.onComplete();
    }

    private List< ResLine > getResLines(long resId, ObservableEmitter< String > emitter) throws IOException {
        int attempt = 1;
        Response response = null;
        while (attempt <= ATTEMPT_COUNT) {
            try {
                response = apiInspectionSheet.getResLines(new ArrayList< Long >(Arrays.asList(resId))).execute();
                break;
            } catch (java.net.ProtocolException ex) {
                if (attempt == ATTEMPT_COUNT) {
                    throw ex;
                }
            } catch (Exception ex) {
                if (attempt == ATTEMPT_COUNT) {
                    throw ex;
                }
            }
            emitter.onNext("Ошибка при загрузке Линий района. Попытка " + String.valueOf(attempt + 1));
            attempt++;

        }
        if (response == null || response.body() == null) {
            return new ArrayList<>();
        }

        ApiGetResponce< ResLine > linesResponse = (ApiGetResponce< ResLine >) response.body();
        long total = linesResponse.getTotal();

        if (linesResponse.getData() != null && !linesResponse.getData().isEmpty()) {
            return linesResponse.getData();
        }
        return new ArrayList<>();
    }

    private void batchLoadLines(List< ResLine > lines, ObservableEmitter< String > emitter) throws IOException {

        //List<Long> uids = new ArrayList<>();
        int start = 0;
        int max = lines.size();

        int cnt = 0;

        while (start < max) {
            List< Long > uids = new ArrayList<>();

            for (int i = start; i < start + PAGE_SIZE && i < max; i++) {
                uids.add(lines.get(i).getLineUid());
                cnt++;
            }

            loadLinesByUids(uids, emitter);
            int percent = (int) ((cnt / (float) max) * 100);
            emitter.onNext("Загрузка Линий " + String.valueOf(percent) + "%");

            start += PAGE_SIZE;
        }
    }

    private void loadLinesByUids(List<Long> uids,  ObservableEmitter< String > emitter) throws IOException {
        int attempt = 1;
        Response response = null;
        while (attempt <= ATTEMPT_COUNT) {
            try {
                response = apiInspectionSheet.getLines(TextUtils.join(",", uids)).execute();
                break;
            } catch (java.net.ProtocolException ex) {
                if (attempt == ATTEMPT_COUNT) {
                    throw ex;
                }
            } catch (Exception ex) {
                if (attempt == ATTEMPT_COUNT) {
                    throw ex;
                }
            }
            emitter.onNext("Ошибка при загрузке Линий. Попытка " + String.valueOf(attempt + 1));
            attempt++;

        }
        if (response == null || response.body() == null) {
            return ;
        }

        ApiGetResponce< LineData > linesResponse = (ApiGetResponce< LineData >) response.body();
        long total = linesResponse.getTotal();

        if (linesResponse.getData() != null && !linesResponse.getData().isEmpty()) {

            dbDataImporter.loadISLines(linesResponse.getData());
        }


    }


}
