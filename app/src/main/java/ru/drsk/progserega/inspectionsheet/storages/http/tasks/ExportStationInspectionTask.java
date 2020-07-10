package ru.drsk.progserega.inspectionsheet.storages.http.tasks;

import android.text.TextUtils;

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
import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.Transformer;
import ru.drsk.progserega.inspectionsheet.entities.inspections.IStationInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;
import ru.drsk.progserega.inspectionsheet.entities.inspections.StationEquipmentInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TransformerInspection;
import ru.drsk.progserega.inspectionsheet.services.DBLog;
import ru.drsk.progserega.inspectionsheet.storages.ISettingsStorage;
import ru.drsk.progserega.inspectionsheet.storages.http.IApiInspectionSheet;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.StationInspectionJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.TransformerInfo;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.TransformerInspectionResult;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.UploadInspectionImageInfo;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.UploadRes;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.UploadStationImageInfo;

public class ExportStationInspectionTask implements ObservableOnSubscribe< UploadRes > {

    private IApiInspectionSheet apiArmIS;
    private List< IStationInspection > stationInspections;
    private ISettingsStorage settingsStorage;

    private final String EXPORT_TAG = "EXPORT STATIONS:";


    long uploadTime = 0;

    private ObservableEmitter< UploadRes > emitter;

    public ExportStationInspectionTask(IApiInspectionSheet apiArmIS, List< IStationInspection > stationInspections, ISettingsStorage settingsStorage) {
        this.apiArmIS = apiArmIS;
        this.stationInspections = stationInspections;
        this.settingsStorage = settingsStorage;
    }

    @Override
    public void subscribe(ObservableEmitter< UploadRes > emitter) {
        uploadTime = System.currentTimeMillis() / 1000L;
        this.emitter = emitter;

        DBLog.d(EXPORT_TAG, "Экспорт осмотров подстанций (ТП) (" + stationInspections.size() + ") шт.");
        for (IStationInspection inspection : stationInspections) {


            Equipment station = inspection.getStation();
            long stationUid = station.getUniqId();
            int stationType = station.getType().getValue();
            String stationTypeName = inspection.getStation().getType().name();

            DBLog.d(EXPORT_TAG, "Начало экспорта осмотра %s [%s] uid = %d", stationTypeName, station.getName(), stationUid);

            long inspectionArmId = uploadStationInspectionInfo(inspection, 0L);

            if (inspectionArmId == 0) {
                DBLog.e(EXPORT_TAG, "Ошибка при экспорте осмотра.  inspectionArmId = 0");
                continue;
            }

            //грузим общие фото
            uploadCommonPhotos(inspection.getCommonPhotos(), stationUid, stationType, uploadTime, inspectionArmId);

            //отправка осмотра подстанции (ТП)
            DBLog.d(EXPORT_TAG, "отправка осмотра подстанции (ТП)..");
            uploadStationInspectionItems(stationUid, stationType, inspectionArmId, inspection.getStationInspectionItems());

            //отправка осмотров оборудования
            DBLog.d(EXPORT_TAG, "отправка осмотров оборудования..");
            for (StationEquipmentInspection equipmentInspection : inspection.getStationEquipmentInspections()) {

                long transformerIdInArm = uploadEquipmentInfo(equipmentInspection);
                if (transformerIdInArm == 0) {
                    continue;
                }

                uplodEquipmentInspectionItems(stationUid, stationType, inspectionArmId, transformerIdInArm, equipmentInspection.getInspectionItems());

            }
        }

        emitter.onComplete();
    }

    private long uploadStationInspectionInfo(IStationInspection inspection, Long inspectinIdARM) {

        DBLog.d(EXPORT_TAG, "Upload station inspection info....");

        long inspectionDate = 0;
        if (inspection.getStation().getInspectionDate() != null) {
            inspectionDate = inspection.getStation().getInspectionDate().getTime() / 1000L;

        }

        String inspectorName = settingsStorage.loadSettings().getFio();
        StationInspectionJson inspectionJson = new StationInspectionJson(
                inspectinIdARM,
                inspection.getStation().getUniqId(),
                inspection.getStation().getType().getValue(),
                inspectorName,
                0, //пока не используется
                inspectionDate,
                inspection.getStation().getInspectionPercent()
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

    private long uploadEquipmentInfo(StationEquipmentInspection inspection) {

        DBLog.d(EXPORT_TAG, "Upload transformer info....");
        long inspectionDate = 0;
        if (inspection.getEquipment().getInspectionDate() != null) {
            inspectionDate = inspection.getEquipment().getInspectionDate().getTime() / 1000L;
        }

        TransformerInfo info = new TransformerInfo(
                inspection.getStation().getUniqId(),
                inspection.getStation().getType().getValue(),
                inspection.getEquipment().getUniqId(),
                inspection.getEquipment().getYear(),
                inspection.calcInspectionPercent(),
                inspectionDate,
                ((Transformer) inspection.getEquipment()).getModel().getId(),
                inspection.getEquipment().getPlace()
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

    private void uploadCommonPhotos(List< InspectionPhoto > photos, long stationUid, int stationType, long date, long inspectionId) {
        DBLog.d(EXPORT_TAG, "uploadCommonPhotos...");
        if (photos == null || photos.isEmpty()) {
            return;
        }
        DBLog.d(EXPORT_TAG, "Экспорт общих фотографий " + photos.size() + " шт.");
        for (InspectionPhoto photo : photos) {
            uploadCommonPhoto(photo, stationUid, stationType, date, inspectionId);
        }
    }

    private boolean uploadCommonPhoto(InspectionPhoto photo, long stationUid, int substationType, long date, long inspectionId) {

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

            UploadStationImageInfo imageInfo = new UploadStationImageInfo(file.getName(), stationUid, substationType, date, inspectionId);
            Gson gson = new Gson();
            String fileInfoJson = gson.toJson(imageInfo);
            RequestBody fileInfo = RequestBody.create(MediaType.parse("text/plain"), fileInfoJson);


            response = apiArmIS.uploadStationImage(fileToUpload, fileInfo).execute();


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

    private void uploadStationInspectionItems(long stationUid, int stationType, long inspectionId, List< InspectionItem > inspectionItems) {


        for (InspectionItem inspectionRes : inspectionItems) {

            //пропускаем не заполненные
            if (inspectionRes.isEmpty()) {
                continue;
            }

            TransformerInspectionResult inpectionResult = new TransformerInspectionResult(
                    stationUid,
                    stationType,
                    0,
                    inspectionRes.getValueId(),
                    TextUtils.join(",", inspectionRes.getResult().getValues()),
                    TextUtils.join(",", inspectionRes.getResult().getSubValues()),
                    inspectionRes.getResult().getComment(),
                    uploadTime,
                    inspectionId
            );

            Response response = null;
            try {
                DBLog.d(EXPORT_TAG, "[uploadSrationInspection]... ");
                response = apiArmIS.uploadInspection(inpectionResult).execute();
            } catch (Exception e) {
                DBLog.e(EXPORT_TAG, "Error while upload station inspection item result");
                e.printStackTrace();
                continue;
            }
            if (response.body() == null) {
                DBLog.e(EXPORT_TAG, "Error while upload station inspection item result. Response.body() is null");
                continue;
            }

            UploadRes uploadRes = (UploadRes) response.body();
            if (uploadRes.getStatus() != 200) {
                DBLog.e(EXPORT_TAG, "Error while uploadstation inspection item. status = " + uploadRes.getStatus());
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

//                emitter.onNext(uploadRes);
        }
    }

    private void uplodEquipmentInspectionItems(long stationUid, int stationType, long inspectionId, long equipmentArmId, List< InspectionItem > inspectionItems) {
        //грузим осмотры
        DBLog.d(EXPORT_TAG, "Экспорт элементов осмотра");
        for (InspectionItem inspectionRes : inspectionItems) {

            //пропускаем не заполненные
            if (inspectionRes.isEmpty()) {
                continue;
            }

            TransformerInspectionResult inpectionResult = new TransformerInspectionResult(
                    stationUid,
                    stationType,
                    equipmentArmId,
                    inspectionRes.getValueId(),
                    TextUtils.join(",", inspectionRes.getResult().getValues()),
                    TextUtils.join(",", inspectionRes.getResult().getSubValues()),
                    inspectionRes.getResult().getComment(),
                    this.uploadTime,
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
}

