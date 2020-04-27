package ru.drsk.progserega.inspectionsheet.storages.http.tasks;

import android.text.TextUtils;


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
import ru.drsk.progserega.inspectionsheet.services.DBLog;
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

    private final String EXPORT_TAG = "EXPORT TRANSFORMERS:";

    public ExportTransformerInspectionTask(IApiInspectionSheet apiArmIS, List<TransformerInspection> transformerInspections, ISettingsStorage settingsStorage) {
        this.apiArmIS = apiArmIS;
        this.transformerInspections = transformerInspections;
        this.settingsStorage = settingsStorage;
    }

    @Override
    public void subscribe(ObservableEmitter<UploadRes> emitter) {

        Map<Long, Long> stationInspectionsMap = new HashMap<>();

        long unixTime = System.currentTimeMillis() / 1000L;
        DBLog.d(EXPORT_TAG, "Экспорт осмотров трансформаторов (" + transformerInspections.size() + ") шт.");
        for (TransformerInspection inspection : transformerInspections) {


            long substationId = inspection.getSubstation().getUniqId();
            int substationType = inspection.getSubstation().getType().getValue();
            // long transformerId = inspection.getTransformator().getId();

            //грузим инфу о трансформаторе
            long transformerIdInArm = uploadTransformerInfo(inspection);
            if (transformerIdInArm == 0) {
                continue;
            }
//
//            if (true) {
//                continue;
//                //emitter.onComplete();
//                //return;
//            }

            Long inspectionId = stationInspectionsMap.get(inspection.getSubstation().getUniqId());
            if (inspectionId == null) {
                inspectionId = uploadStationInspectionInfo(inspection, 0L);
                stationInspectionsMap.put(inspection.getSubstation().getUniqId(), inspectionId);
            } else {
                uploadStationInspectionInfo(inspection, inspectionId);
            }

            if (inspectionId == 0) {
                DBLog.e(EXPORT_TAG, "Ошибка при экспорте осмотра подстанции/ТП.  inspectionId = 0");
                continue;
            }


            //грузим общие фото
            uploadTransformersPhotos(inspection.getTransformator().getPhotoList(), transformerIdInArm, substationType, unixTime, inspectionId);

            //грузим осмотры
            DBLog.d(EXPORT_TAG, "Экспорт элементов осмотра");
            for (InspectionItem inspectionRes : inspection.getInspectionItems()) {

                //пропускаем не заполненные
                if (inspectionRes.isEmpty()) {
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

                Response response = null;
                try {
                    DBLog.d(EXPORT_TAG, "apiArmIS.uploadInspection... ");
                    response = apiArmIS.uploadInspection(inpectionResult).execute();
                } catch (Exception e) {
                    DBLog.e(EXPORT_TAG, "Error while upload transformer inspection result");
                    e.printStackTrace();
                    continue;
                }
                if (response.body() == null) {
                    DBLog.e(EXPORT_TAG, "Error while upload transformer inspection result. Response.body() is null");
                    continue;
                }

                UploadRes uploadRes = (UploadRes) response.body();
                if (uploadRes.getStatus() != 200) {
                    DBLog.e(EXPORT_TAG, "Error while upload transformer inspection result. status = " + uploadRes.getStatus());
                    continue;
                }

                inspectionRes.setArmId(uploadRes.getId());
                //upload photo
                DBLog.d(EXPORT_TAG, "Экспорт фотографий элемента осмотра");
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

        DBLog.d(EXPORT_TAG, "Upload station inspection info....");

        long inspectionDate = 0;
        if (inspection.getTransformator().getInspectionDate() != null) {
            inspectionDate = inspection.getTransformator().getInspectionDate().getTime() / 1000L;

        }

        String inspectorName = inspection.getInspectorName();
        if (inspectorName.equals("")) {
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
            DBLog.e(EXPORT_TAG, "Ошибка экспорта информации о осмотре подстанции (ТП)");
            e.printStackTrace();
            return 0;
        }

        if (response.body() == null) {
            return 0;
        }

        UploadRes uploadRes = (UploadRes) response.body();
        DBLog.d(EXPORT_TAG, "result " + uploadRes.getStatus());
        if (uploadRes.getStatus() != 200) {
            DBLog.e(EXPORT_TAG, "Ошибка экспорта информации о осмотре подстанции (ТП) status = " + uploadRes.getStatus());
            return 0;
        }

        return uploadRes.getId();
    }

    private long uploadTransformerInfo(TransformerInspection inspection) {

        DBLog.d(EXPORT_TAG, "Upload transformer info....");
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
            DBLog.d(EXPORT_TAG, "Ошибка экспорта информации о трансформаторе...");
            e.printStackTrace();
            return 0;
        }

        if (response.body() == null) {
            DBLog.d(EXPORT_TAG, "Ошибка экспорта информации о трансформаторе...");
            return 0;
        }

        UploadRes uploadRes = (UploadRes) response.body();
        DBLog.d(EXPORT_TAG, "result " + uploadRes.getStatus());
        if (uploadRes.getStatus() != 200) {
            DBLog.e(EXPORT_TAG, "Ошибка экспорта информации о трансформаторе. статус = " + uploadRes.getStatus() + " " + uploadRes.getMessage());
            return 0;
        }

        return uploadRes.getId();
    }

    private boolean uploadPhoto(InspectionPhoto photo, long armInspectionId) {

        File file = new File(photo.getPath());
        DBLog.d("UPLOAD FILE:", "Start upload file: " + file.getName());
        if (!file.exists()) {
            DBLog.d("UPLOAD FILE:", "File does not exist: " + file.getName());
            return false;
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
            DBLog.e("UPLOAD FILE:", e);
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            DBLog.e("UPLOAD FILE:", e);
            e.printStackTrace();
            return false;
        }

        if (response.body() == null) {
            return false;
        }

        UploadRes uploadRes = (UploadRes) response.body();
        DBLog.d("UPLOAD FILE:", "result " + uploadRes.getStatus());
        if (uploadRes.getStatus() != 200) {
            DBLog.d("UPLOAD FILE:", "result " + uploadRes.getStatus() + "  " + uploadRes.getMessage());
            return false;
        }

        return true;
    }

    private void uploadTransformersPhotos(List<InspectionPhoto> photos, long transformerId, int substationType, long date, long inspectionId) {
        DBLog.d(EXPORT_TAG, "uploadTransformersPhotos...");
        if (photos == null || photos.isEmpty()) {
            return;
        }
        DBLog.d(EXPORT_TAG, "Экспорт общих фотографий " + photos.size() + " шт.");
        for (InspectionPhoto photo : photos) {
            uploadTransformerPhoto(photo, transformerId, substationType, date, inspectionId);
        }
    }

    private boolean uploadTransformerPhoto(InspectionPhoto photo, long transformerId, int substationType, long date, long inspectionId) {

        File file = new File(photo.getPath());
        DBLog.d("UPLOAD FILE:", "Start upload file: " + file.getName());

        if (!file.exists()) {
            DBLog.d("UPLOAD FILE:", "File does not exist: " + file.getName());
            return false;
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
            DBLog.e("UPLOAD FILE:", e);
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            DBLog.e("UPLOAD FILE:", e);
            e.printStackTrace();
            return false;
        }

        if (response.body() == null) {
            return false;
        }

        UploadRes uploadRes = (UploadRes) response.body();
        DBLog.d("UPLOAD FILE:", "result " + uploadRes.getStatus());
        if (uploadRes.getStatus() != 200) {
            DBLog.e("UPLOAD FILE:", "result " + uploadRes.getStatus() + "  " + uploadRes.getMessage());
            return false;
        }

        return true;
    }
}

