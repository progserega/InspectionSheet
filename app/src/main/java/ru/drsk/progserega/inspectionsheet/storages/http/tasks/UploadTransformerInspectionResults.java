package ru.drsk.progserega.inspectionsheet.storages.http.tasks;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import retrofit2.Response;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TransformerInspection;
import ru.drsk.progserega.inspectionsheet.storages.http.IApiInspectionSheet;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.TransformerInspectionResult;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.UploadRes;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.SteTPResponse;

public class UploadTransformerInspectionResults  implements ObservableOnSubscribe<UploadRes> {
    private static final int PAGE_SIZE = 200;
    private IApiInspectionSheet apiArmIS;
    private List<TransformerInspection> transformerInspections;

    public UploadTransformerInspectionResults( IApiInspectionSheet apiArmIS, List<TransformerInspection> transformerInspections){
        this.apiArmIS = apiArmIS;
        this.transformerInspections = transformerInspections;
    }
    @Override
    public void subscribe(ObservableEmitter<UploadRes> emitter) throws Exception {

        long unixTime = System.currentTimeMillis() / 1000L;
        for (TransformerInspection inspection :transformerInspections ){

            long substationId =  inspection.getSubstation().getId();
            int substationType = inspection.getSubstation().getType().getValue();
            long transformerId = inspection.getTransformator().getId();
            for(InspectionItem inspectionRes: inspection.getInspectionItems()){
                TransformerInspectionResult inpectionResult = new TransformerInspectionResult(
                        substationId,
                        substationType,
                        transformerId,
                        inspectionRes.getValueId(),
                        TextUtils.join(",", inspectionRes.getResult().getValues()),
                        TextUtils.join(",", inspectionRes.getResult().getSubValues()),
                        inspectionRes.getResult().getComment(),
                        unixTime
                );

                Response response = apiArmIS.uploadInspection(inpectionResult).execute();
                if (response.body() == null) {
                    break;
                }

                UploadRes uploadRes = (UploadRes) response.body();
                emitter.onNext(uploadRes);
            }


            emitter.onComplete();
        }
    }
}

