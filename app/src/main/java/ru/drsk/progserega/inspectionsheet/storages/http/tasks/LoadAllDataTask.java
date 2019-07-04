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
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.GeoLine;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.GeoLineDetail;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.GeoLineDetailResponse;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.GeoLinesResponse;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.SteTPResponse;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.DBDataImporter;

public class LoadAllDataTask implements ObservableOnSubscribe<String> {
    private static final int PAGE_SIZE = 200;
    private static final int ATTEMPT_COUNT = 5;
    private IApiInspectionSheet apiInspectionSheet;
    private IApiSTE apiSTE;
    private IApiGeo apiGeo;

    private DBDataImporter dbDataImporter;


    public LoadAllDataTask(IApiInspectionSheet apiInspectionSheet, IApiSTE apiSTE, IApiGeo apiGeo, DBDataImporter dbDataImporter) {
        this.apiInspectionSheet = apiInspectionSheet;
        this.apiSTE = apiSTE;
        this.apiGeo = apiGeo;
        this.dbDataImporter = dbDataImporter;
    }

    @Override
    public void subscribe(ObservableEmitter<String> emitter) throws Exception {

        try {
            emitter.onNext("Загружаем список СП");
            //loadSp(emitter);

            emitter.onNext("Загружаем список РЭС");
            //loadRes(emitter);

            //dbDataImporter.initEnterpriseCache();

            emitter.onNext("Загружаем список ТП");
            //  loadTP(emitter);


            emitter.onNext("Загружаем список ВЛ");
            List<GeoLine> lines = loadGeoLines(emitter);
            dbDataImporter.loadLines(lines);

            int cnt = 1;
            GeoLineDetail lineDetail = null;
            for (GeoLine line : lines) {
                try {
                    int percent = (int) ((cnt / (float) lines.size()) * 100);
                    emitter.onNext("Загружаем данные для линии\n" + line.getName()+"\n"+String.valueOf(percent)+"% ("+String.valueOf(cnt)+")");
                    lineDetail = loadGeoLineDetails(emitter, line.getName());
                } catch (Exception ex) {
                    continue;
                }
                if (lineDetail != null) {
                    dbDataImporter.loadLineDetail(line, lineDetail);
                }
                if(cnt>=200){
                    break;
                }
                cnt++;
            }

        } catch (IOException e) {

            e.printStackTrace();
            emitter.onError(e);
        }

        emitter.onComplete();
    }

    private void loadSp(ObservableEmitter<String> emitter) throws IOException {
        Response response = apiInspectionSheet.getAllSp().execute();
        if (response.body() == null) {
            throw new IOException("Данные по СП не получены. response.body is null");
        }

        List<SpModel> spModels = (List<SpModel>) response.body();

        if (spModels == null || spModels.isEmpty()) {
            throw new IOException("Данные по СП не получены");
        }

        for (SpModel sp : spModels) {
            //android.os.SystemClock.sleep(2000);
            dbDataImporter.addSp(sp.getId(), sp.getName());
            emitter.onNext(sp.getName());
        }
    }

    private void loadRes(ObservableEmitter<String> emitter) throws IOException {
        Response response = apiInspectionSheet.getAllRes().execute();
        if (response.body() == null) {
            throw new IOException("Данные по РЭС не получены. response.body is null");
        }

        List<ResModel> resModels = (List<ResModel>) response.body();

        if (resModels == null || resModels.isEmpty()) {
            throw new IOException("Данные по РЭС не получены. response.body is null");
        }

        for (ResModel res : resModels) {
            //android.os.SystemClock.sleep(500);
            dbDataImporter.addRes(res.getId(), res.getSpId(), res.getName(), res.getAlterName());
            emitter.onNext(res.getName() + " " + res.getAlterName());
        }
    }

    private void loadTP(ObservableEmitter<String> emitter) throws IOException {
        int page = 0;
        int total = 0;
        int offset = 0;
        int cnt = 0;
        do {
            offset = page * PAGE_SIZE;
            SteTPResponse tpResponse = null;

            Response response = null;
            int attempt = 1;
            while (attempt < ATTEMPT_COUNT) {
                try {
                    response = apiSTE.getTPbyRange("api/all-tp-by-range", offset, PAGE_SIZE).execute();
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
                emitter.onNext("Ошибка при загрузке ТП. Попытка " + String.valueOf(attempt));
                attempt++;

            }
            if (response == null || response.body() == null) {
                break;
            }

            tpResponse = (SteTPResponse) response.body();
            total = tpResponse.getTotalRecords();

            if (tpResponse.getData() != null && !tpResponse.getData().isEmpty()) {
                //передаем данные на обработку
                cnt += tpResponse.getData().size();
                int percent = (int) ((cnt / (float) tpResponse.getTotalRecords()) * 100);
                dbDataImporter.loadSteTpModel(tpResponse.getData());
                emitter.onNext("Загрузка ТП " + String.valueOf(percent) + "%");
            }


            page++;


        } while ((offset + PAGE_SIZE) < total);

    }

    private List<GeoLine> loadGeoLines(ObservableEmitter<String> emitter) throws IOException {
        Response response = null;

        response = apiGeo.getAllLines("api/get-all-lines").execute();
        if (response == null || response.body() == null) {
            emitter.onNext("Ошибка при загрузке списка линий. Отсутствуют данные");
            return new ArrayList<>();
        }

        GeoLinesResponse linesResponse = (GeoLinesResponse) response.body();
        if (linesResponse.getData() == null || linesResponse.getData().isEmpty()) {
            emitter.onNext("Ошибка при загрузке списка линий. Отсутствуют данные");
            return new ArrayList<>();
        }


//            for(GeoLine line: linesResponse.getData()){
//                emitter.onNext("Загрузка Линии " + line.getName() );
//            }
        List<GeoLine> lines = linesResponse.getData();
        long cnt = 1l;
        for (GeoLine line : lines) {
            line.setUniqId(cnt);
            cnt++;
        }
        return lines;
    }

    private GeoLineDetail loadGeoLineDetails(ObservableEmitter<String> emitter, String name) throws IOException {
        Response response = null;

        response = apiGeo.getLineDetails("api/get-line-by-name", name).execute();
        if (response == null || response.body() == null) {
            emitter.onNext("Ошибка при загрузке линии. Отсутствуют данные");
            return null;// new ArrayList<>();
        }

        GeoLineDetailResponse linesResponse = (GeoLineDetailResponse) response.body();
        if (linesResponse.getStatus().equals("error")) {
            emitter.onNext("Ошибка при загрузке линии. Отсутствуют данные:\n" + linesResponse.getDescription());
            return null; //new ArrayList<>();
        }


        return linesResponse.getData();


    }
}
