package ru.drsk.progserega.inspectionsheet.storages.http.tasks;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.List;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TransformerInspection;
import ru.drsk.progserega.inspectionsheet.storages.http.IApiInspectionSheet;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.TransformerInspectionResult;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.UploadImageInfo;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.UploadRes;

public class UploadTransformerInspectionTask implements ObservableOnSubscribe<UploadRes> {

    private IApiInspectionSheet apiArmIS;
    private List<TransformerInspection> transformerInspections;

    public UploadTransformerInspectionTask(IApiInspectionSheet apiArmIS, List<TransformerInspection> transformerInspections) {
        this.apiArmIS = apiArmIS;
        this.transformerInspections = transformerInspections;
    }

    @Override
    public void subscribe(ObservableEmitter<UploadRes> emitter) throws Exception {

        long unixTime = System.currentTimeMillis() / 1000L;
        for (TransformerInspection inspection : transformerInspections) {

            long substationId = inspection.getSubstation().getId();
            int substationType = inspection.getSubstation().getType().getValue();
            long transformerId = inspection.getTransformator().getId();
            for (InspectionItem inspectionRes : inspection.getInspectionItems()) {
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
                if (uploadRes.getStatus() != 200) {
                    continue;
                }

                inspectionRes.setArmId(uploadRes.getId());
                //upload photo
                for (InspectionPhoto photo : inspectionRes.getResult().getPhotos()) {

                    uploadPhoto(photo, inspectionRes.getArmId() );
                }

                emitter.onNext(uploadRes);
            }


            emitter.onComplete();
        }
    }

    private void uploadPhoto(InspectionPhoto photo, long armInspectionId) {

        File file = new File(photo.getPath());
        Log.d("UPLOAD FILE:", "Filename " + file.getName());
        //RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
        Response response = null;
        try {
            //Не работает с кирилическими именами файлов
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);

            UploadImageInfo imageInfo = new UploadImageInfo(file.getName(), armInspectionId);
            Gson gson = new Gson();
            String fileInfoJson = gson.toJson(imageInfo);
            RequestBody fileInfo = RequestBody.create(MediaType.parse("text/plain"), fileInfoJson);


            response = apiArmIS.uploadFile(fileToUpload, fileInfo).execute();


        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        if (response.body() == null) {
            return;
        }

        UploadRes uploadRes = (UploadRes) response.body();
        Log.d("UPLOAD FILE:", "result " + uploadRes.getStatus());
    }
}

