package ru.drsk.progserega.inspectionsheet.storages.http.tasks;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import retrofit2.Response;
import ru.drsk.progserega.inspectionsheet.storages.http.IApiInspectionSheet;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.ResModel;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.SpModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.DBDataImporter;

public class PingServerTask  implements ObservableOnSubscribe<Map<String, Boolean>> {
    private static final int PAGE_SIZE = 200;
    private static final int ATTEMPT_COUNT = 5;
    private IApiInspectionSheet apiInspectionSheet;


    private String serverUrl;

    public PingServerTask(IApiInspectionSheet apiInspectionSheet, String serverUrl) {
        this.apiInspectionSheet = apiInspectionSheet;
        this.serverUrl = serverUrl;
    }

    @Override
    public void subscribe(ObservableEmitter< Map<String, Boolean> > emitter) throws Exception {

        Map<String, Boolean> res = new HashMap<>();
        try {
            loadSp();
            res.put(serverUrl, true);
            emitter.onNext(res);
        } catch (IOException e) {
            e.printStackTrace();

            res.put(serverUrl, false);
            emitter.onNext(res);
        }


        emitter.onComplete();
    }

    private void loadSp() throws IOException {
        Response response = apiInspectionSheet.getAllSp().execute();
        if (response.body() == null) {
            throw new IOException("Данные по СП не получены. response.body is null");
        }

        List< SpModel > spModels = (List< SpModel >) response.body();

        if (spModels == null || spModels.isEmpty()) {
            throw new IOException("Данные по СП не получены");
        }


    }


}
