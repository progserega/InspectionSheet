package ru.drsk.progserega.inspectionsheet.storages.http.tasks;

import android.util.Pair;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import retrofit2.Response;
import ru.drsk.progserega.inspectionsheet.storages.http.IApiInspectionSheet;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.AppVersionJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.SpModel;

public class GetAppVersionTask implements ObservableOnSubscribe< AppVersionJson > {
    private static final int ATTEMPT_COUNT = 5;
    private IApiInspectionSheet apiInspectionSheet;

    public GetAppVersionTask(IApiInspectionSheet apiInspectionSheet) {
        this.apiInspectionSheet = apiInspectionSheet;


    }

    @Override
    public void subscribe(ObservableEmitter< AppVersionJson > emitter) throws Exception {

        try {
            AppVersionJson info = requestVersion();
            emitter.onNext(info);
        } catch (IOException e) {
            emitter.onError(e);
        }

        emitter.onComplete();

    }

    private AppVersionJson requestVersion() throws IOException {
        Response response = apiInspectionSheet.getVersion().execute();
        if (response.body() == null) {

            /*
             * response.errorBody().string() так можно посмотреть что прилетело от сервера
             */
            throw new IOException("Данные о текущей версии не получены.\n" + response.message());
        }

        AppVersionJson versionInfo = (AppVersionJson) response.body();

        if (versionInfo == null) {
            throw new IOException("Данные о текущей версии не получены");
        }

        return versionInfo;

    }


}
