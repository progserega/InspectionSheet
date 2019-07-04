package ru.drsk.progserega.inspectionsheet.storages.http.tasks;

import android.util.Log;

import java.io.IOException;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import retrofit2.Response;
import ru.drsk.progserega.inspectionsheet.storages.http.IApiInspectionSheet;
import ru.drsk.progserega.inspectionsheet.storages.http.IApiSTE;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.SteTPResponse;

public class LoadTpTask implements ObservableOnSubscribe<SteTPResponse> {
    private static final int PAGE_SIZE = 200;
    private IApiSTE apiSTE;

    public LoadTpTask( IApiSTE apiSTE){
        this.apiSTE = apiSTE;
    }
    @Override
    public void subscribe(ObservableEmitter<SteTPResponse> emitter) throws Exception {

        int page = 0;
        int total = 0;
        int offset = 0;
        do {
            offset = page * PAGE_SIZE;
            SteTPResponse tpResponse = null;
            try {
                Response response = apiSTE.getTPbyRange("api/all-tp-by-range", offset, PAGE_SIZE).execute();
                if (response.body() == null) {
                    break;
                }

                tpResponse = (SteTPResponse) response.body();
                total = tpResponse.getTotalRecords();

                if (tpResponse.getData() != null && !tpResponse.getData().isEmpty()) {
                    //передаем данные на обработку
                    emitter.onNext(tpResponse);
                }

            } catch (IOException e) {
                Log.e("SYNC ERROR", String.valueOf(offset));
                e.printStackTrace();
                emitter.onError(e);
            }

            page++;


        } while ((offset + PAGE_SIZE) < total);
        emitter.onComplete();
    }
}
