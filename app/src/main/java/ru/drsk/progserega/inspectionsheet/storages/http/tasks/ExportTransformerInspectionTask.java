package ru.drsk.progserega.inspectionsheet.storages.http.tasks;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TransformerInspection;
import ru.drsk.progserega.inspectionsheet.storages.ISettingsStorage;
import ru.drsk.progserega.inspectionsheet.storages.http.IApiInspectionSheet;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.StationInspectionJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.TransformerInspectionResult;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.UploadInspectionImageInfo;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.UploadRes;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.UploadTransformerImageInfo;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.TransformerInfo;

public class ExportTransformerInspectionTask implements ObservableOnSubscribe<UploadRes> {

    private IApiInspectionSheet apiArmIS;
    private List<TransformerInspection> transformerInspections;
    private ISettingsStorage settingsStorage;
    public ExportTransformerInspectionTask(IApiInspectionSheet apiArmIS, List<TransformerInspection> transformerInspections, ISettingsStorage settingsStorage) {
        this.apiArmIS = apiArmIS;
        this.transformerInspections = transformerInspections;
        this.settingsStorage = settingsStorage;
    }

    @Override
    public void subscribe(ObservableEmitter<UploadRes> emitter) throws Exception {

        Map<Long, Long> stationInspectionsMap = new HashMap<>();

        long unixTime = System.currentTimeMillis() / 1000L;
        for (TransformerInspection inspection : transformerInspections) {

            long substationId = inspection.getSubstation().getUniqId();
            int substationType = inspection.getSubstation().getType().getValue();
           // long transformerId = inspection.getTransformator().getId();

            //грузим инфу о трансформаторе
            long transformerIdInArm = uploadTransformerInfo(inspection);
            if(transformerIdInArm == 0){
                continue;
            }
//
//            if (true) {
//                continue;
//                //emitter.onComplete();
//                //return;
//            }

            Long inspectionId = stationInspectionsMap.get(inspection.getSubstation().getUniqId());
            if(inspectionId == null) {
                inspectionId = uploadStationInspectionInfo(inspection, 0L);
                stationInspectionsMap.put(inspection.getSubstation().getUniqId(), inspectionId);
            }
            else{
                uploadStationInspectionInfo(inspection, inspectionId);
            }

            if(inspectionId == 0){
                Log.e("[INSPECTION UPLOAD]", "Ошибка при экспорте осмотра подстанции/ТП.  inspectionId = 0");
                continue;
            }


            //грузим общие фото
            uploadTransformersPhotos(inspection.getTransformator().getPhotoList(), transformerIdInArm, substationType, unixTime, inspectionId);

            //грузим осмотры
            for (InspectionItem inspectionRes : inspection.getInspectionItems()) {

                //пропускаем не заполненные
                if(inspectionRes.isEmpty()){
                    continue;
                }

                TransformerInspectionResult inpectionResult = new TransformerInspectionResult(
                        substationId,
                        substationType,
                        transformerIdInArm,
                        inspectionRes.getValueId(),
                        TextUtils.join(",", inspectionRes.getResult().getValues()),
                        TextUtils.join(",", inspectionRes.getResult().getSubValues()),
                        inspectionRes.getResult().getComment(),
                        unixTime,
                        inspectionId
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

                    if (!uploadPhoto(photo, inspectionRes.getArmId())) {
                        //return; //<--для отладки
                    }
                }

                emitter.onNext(uploadRes);
            }
        }

        emitter.onComplete();
    }

    private long uploadStationInspectionInfo(TransformerInspection inspection, Long inspectinIdARM) {

        Log.d("UPLOAD:", "Upload station inspection info....");

        long inspectionDate = 0;
        if (inspection.getTransformator().getInspectionDate() != null) {
            inspectionDate = inspection.getTransformator().getInspectionDate().getTime() / 1000L;

        }

        String inspectorName =inspection.getInspectorName();
        if(inspectorName.equals("")){
            inspectorName = settingsStorage.loadSettings().getFio();
        }
        StationInspectionJson inspectionJson = new StationInspectionJson(
                inspectinIdARM,
                inspection.getSubstation().getUniqId(),
                inspection.getSubstation().getType().getValue(),
                inspectorName,
                0, //пока не используется
                inspectionDate,
                inspection.calcInspectionPercent(),
                inspection.getTransformator().getSlot()
        );
        Response response = null;
        try {
            response = apiArmIS.uploadStationInspection(inspectionJson).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }

        if (response.body() == null) {
            return 0;
        }

        UploadRes uploadRes = (UploadRes) response.body();
        Log.d("UPLOAD :", "result " + uploadRes.getStatus());
        if (uploadRes.getStatus() != 200) {
            return 0;
        }

        return uploadRes.getId();
    }

    private long uploadTransformerInfo(TransformerInspection inspection) {

        Log.d("UPLOAD:", "Upload transformer info....");
        long inspectionDate = 0;
        if (inspection.getTransformator().getInspectionDate() != null) {
            inspectionDate = inspection.getTransformator().getInspectionDate().getTime() / 1000L;
        }

        TransformerInfo info = new TransformerInfo(
                inspection.getSubstation().getUniqId(),
                inspection.getSubstation().getType().getValue(),
                inspection.getTransformator().getId(),
                inspection.getTransformator().getYear(),
                inspection.calcInspectionPercent(),
                inspectionDate,
                inspection.getTransformator().getTransformerType().getId(),
                inspection.getTransformator().getSlot()
        );

        Response response = null;
        try {
            response = apiArmIS.uploadTransformerInfo(info).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }

        if (response.body() == null) {
            return 0;
        }

        UploadRes uploadRes = (UploadRes) response.body();
        Log.d("UPLOAD :", "result " + uploadRes.getStatus());
        if (uploadRes.getStatus() != 200) {
            return 0;
        }

        return uploadRes.getId();
    }

    private boolean uploadPhoto(InspectionPhoto photo, long armInspectionId) {

        File file = new File(photo.getPath());
        Log.d("UPLOAD FILE:", "Start upload file: " + file.getName());
        if(!file.exists()){
            Log.d("UPLOAD FILE:", "File does not exist: " + file.getName());
            return  false;
        }
        //RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
        Response response = null;
        try {
            //Не работает с кирилическими именами файлов
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);

            UploadInspectionImageInfo imageInfo = new UploadInspectionImageInfo(file.getName(), armInspectionId);
            Gson gson = new Gson();
            String fileInfoJson = gson.toJson(imageInfo);
            RequestBody fileInfo = RequestBody.create(MediaType.parse("text/plain"), fileInfoJson);


            response = apiArmIS.uploadInspectionImage(fileToUpload, fileInfo).execute();


        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        if (response.body() == null) {
            return false;
        }

        UploadRes uploadRes = (UploadRes) response.body();
        Log.d("UPLOAD FILE:", "result " + uploadRes.getStatus());
        if (uploadRes.getStatus() != 200) {
            return false;
        }

        return true;
    }

    private void uploadTransformersPhotos(List<InspectionPhoto> photos, long transformerId, int substationType, long date, long inspectionId) {
        if (photos == null || photos.isEmpty()) {
            return;
        }

        for (InspectionPhoto photo : photos) {
            uploadTransformerPhoto(photo, transformerId, substationType, date, inspectionId);
        }
    }

    private boolean uploadTransformerPhoto(InspectionPhoto photo, long transformerId, int substationType, long date, long inspectionId) {

        File file = new File(photo.getPath());
        Log.d("UPLOAD FILE:", "Start upload file: " + file.getName());

        if(!file.exists()){
            Log.d("UPLOAD FILE:", "File does not exist: " + file.getName());
            return  false;
        }
        //RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
        Response response = null;
        try {
            //Не работает с кирилическими именами файлов
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);

            UploadTransformerImageInfo imageInfo = new UploadTransformerImageInfo(file.getName(), transformerId, substationType, date, inspectionId);
            Gson gson = new Gson();
            String fileInfoJson = gson.toJson(imageInfo);
            RequestBody fileInfo = RequestBody.create(MediaType.parse("text/plain"), fileInfoJson);


            response = apiArmIS.uploadTransformerImage(fileToUpload, fileInfo).execute();


        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        if (response.body() == null) {
            return false;
        }

        UploadRes uploadRes = (UploadRes) response.body();
        Log.d("UPLOAD FILE:", "result " + uploadRes.getStatus());
        if (uploadRes.getStatus() != 200) {
            return false;
        }

        return true;
    }
}

