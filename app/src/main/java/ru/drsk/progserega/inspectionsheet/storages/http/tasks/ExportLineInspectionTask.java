package ru.drsk.progserega.inspectionsheet.storages.http.tasks;


import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;
import ru.drsk.progserega.inspectionsheet.entities.LineSection;
import ru.drsk.progserega.inspectionsheet.entities.Tower;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectedLine;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectedSection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectedTower;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineSectionDeffect;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TowerDeffect;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TowerInspection;
import ru.drsk.progserega.inspectionsheet.services.DBLog;
import ru.drsk.progserega.inspectionsheet.services.InspectionService;
import ru.drsk.progserega.inspectionsheet.storages.http.IApiInspectionSheet;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.LineInspectionJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.SectionDeffectJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.SectionDeffectsJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.SectionInspectionJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.SectionJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.TowerDeffectJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.TowerDeffectsJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.TowerInspectionJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.TowerJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.UploadInspectionImageInfo;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.UploadRes;

public class ExportLineInspectionTask implements ObservableOnSubscribe<String> {

    private IApiInspectionSheet apiArmIS;
    private List<InspectedLine> inspectedLines;
    private InspectionService inspectionService;
    private long resId;

    public ExportLineInspectionTask(IApiInspectionSheet apiArmIS, List<InspectedLine> inspectedLines, long resId, InspectionService inspectionService) {
        this.apiArmIS = apiArmIS;
        this.inspectedLines = inspectedLines;
        this.resId = resId;
        this.inspectionService = inspectionService;
    }

    @Override
    public void subscribe(ObservableEmitter<String> emitter) {

        long unixTime = System.currentTimeMillis() / 1000L;
        for (InspectedLine line : inspectedLines) {

            emitter.onNext("Экспорт данных линии: " + line.getLineInspection().getLine().getName());
            long inspectionId = exportInspection(line.getLineInspection(), resId);
            if (inspectionId == 0) {
                //что-то пошло не так
                emitter.onError(new Exception("Ошибка передачи результатов осмотра на сервер.\nОтправка отменена.\nПопробуйте позже или обратитесь в службу поддержки"));
                return;
            }

            for (InspectedTower inspectedTower : line.getInspectedTowers()) {

                emitter.onNext("Обновление данных опоры: " + inspectedTower.getTower().getName());
                exportTowerInfo(emitter, inspectedTower.getTower());

                emitter.onNext("Экспорт данных осмотра опоры: " + inspectedTower.getTower().getName());
                long towerInspectionId = exportTowerInspection(inspectionId, inspectedTower.getInspection());
                if (towerInspectionId == 0) {
                    emitter.onError(new Exception("Ошибка передачи результатов осмотра опоры на сервер.\nОтправка отменена.\nПопробуйте позже или обратитесь в службу поддержки"));
                    return;
                }

                emitter.onNext("Экспорт деффектов опоры: " + inspectedTower.getTower().getName());
                long res = exportTowerDeffects(inspectedTower.getDeffects(), towerInspectionId);
                if(res == 0){
                    emitter.onError(new Exception("Ошибка передачи дефектов опоры на сервер.\nОтправка отменена.\nПопробуйте позже или обратитесь в службу поддержки"));
                    return;
                }

                emitter.onNext("Экспорт фотографий деффектов опоры: " + inspectedTower.getTower().getName());
                exportTowerDeffectsPhotos(inspectedTower.getInspection().getPhotos(), towerInspectionId);
            }

            for (InspectedSection inspectedSection : line.getInspectedSections()) {

                emitter.onNext("Обновление данных пролета: " + inspectedSection.getSection().getName());
                exportSectionInfo(emitter, inspectedSection.getSection());

                emitter.onNext("Экспорт данных осмотра пролета: " + inspectedSection.getSection().getName());
                long sectionInspectionId = exportSectionInspection(inspectionId, inspectedSection);
                if (sectionInspectionId == 0) {
                    //что-то пошло не так
                    emitter.onError(new Exception("Ошибка передачи результатов осмотра пролета на сервер.\nОтправка отменена.\nПопробуйте позже или обратитесь в службу поддержки"));
                    return;
                }

                emitter.onNext("Экспорт деффектов пролета: " + inspectedSection.getSection().getName());
                long res  = exportSectionDeffects(inspectedSection.getSection(), inspectedSection.getDeffects(), sectionInspectionId);
                if(res == 0){
                    emitter.onError(new Exception("Ошибка передачи дефектов пролета на сервер.\nОтправка отменена.\nПопробуйте позже или обратитесь в службу поддержки"));
                    return;
                }
                emitter.onNext("Экспорт фотографий деффектов пролета: " + inspectedSection.getSection().getName());
                exportSectionDeffectsPhotos(inspectedSection.getInspection().getPhotos(), sectionInspectionId);


            }

            emitter.onNext("Очистка данных осмотра линии: " + line.getLineInspection().getLine().getName());

            this.inspectionService.deleteLineInspection(line);

            emitter.onNext("Выполнено "+ line.getLineInspection().getLine().getName());
        }

        emitter.onComplete();
    }

    private long exportInspection(LineInspection inspection, long resId)  {

        long timestamp = 0;
        if (inspection.getInspectionDate() != null) {
            timestamp = inspection.getInspectionDate().getTime() / 1000L;
        }

        LineInspectionJson inspectionJson = new LineInspectionJson(
                inspection.getLine().getUniqId(),
                inspection.getInspectorName(),
                inspection.getInspectionType().getId(),
                timestamp,
                inspection.getInspectionPercent(),
                resId
        );

        Response response = null;
        try {
            response = apiArmIS.uploadLineInspection(inspectionJson).execute();
        } catch (java.net.ConnectException e) {
            DBLog.e("UPLOAD LINE INSPECTION", "export line inspection connection exception ");
            return 0;
        } catch (IOException e) {
            DBLog.e("UPLOAD LINE INSPECTION", e);
            e.printStackTrace();
            return 0;
        }

        return processResult(response);
    }

    private long exportTowerInspection(long lineInspectionId, TowerInspection inspection) {

        long timestamp = 0;
        if (inspection.getInspectionDate() != null) {
            timestamp = inspection.getInspectionDate().getTime() / 1000L;
        }

        TowerInspectionJson inspectionJson = new TowerInspectionJson(
                lineInspectionId,
                inspection.getTowerUniqId(),
                inspection.getComment(),
                timestamp
        );

        Response response = null;
        try {
            response = apiArmIS.uploadLineTowerInspection(inspectionJson).execute();
        } catch (IOException e) {
            DBLog.e("UPLOAD TOWER INSPECTION", e);
            return 0;
        }

        return processResult(response);

    }

    private void exportTowerInfo(ObservableEmitter<String> emitter, Tower tower) {

        TowerJson towerJson = new TowerJson(
                tower.getUniqId(),
                tower.getName(),
                (tower.getMaterial() != null) ? tower.getMaterial().getId() : 0,
                (tower.getTowerType() != null) ? tower.getTowerType().getId() : 0,
                tower.getMapPoint().getLat(),
                tower.getMapPoint().getLon(),
                tower.getMapPoint().getEle()
        );


        Response response = null;
        try {
            response = apiArmIS.uploadLineTowerInfo(towerJson).execute();
        } catch (IOException e) {
            DBLog.e("UPLOAD TOWER INFO", e);
            e.printStackTrace();
            emitter.onNext("Ошибка обновления информации о Опоре");
            return;
        }

        if (response.body() == null) {
            emitter.onNext("Ошибка обновления информации о Опоре");
            return;
        }

        UploadRes uploadRes = (UploadRes) response.body();
        DBLog.d("UPLOAD :", "result " + uploadRes.getStatus());
        if (uploadRes.getStatus() != 200) {
            emitter.onNext("Ошибка обновления информации о Опоре");
        }


    }

    private long exportTowerDeffects(List<TowerDeffect> deffectList, long towerInspectionId) {

        List<TowerDeffectJson> deffectsJsonList = new ArrayList<>();
        for (TowerDeffect deffect : deffectList) {
            if(deffect.getValue() == 0){
                continue;
            }
            deffectsJsonList.add(new TowerDeffectJson(
                    deffect.getTowerUniqId(),
                    (int) deffect.getDeffectType().getId(),
                    deffect.getValue()
            ));
        }

        TowerDeffectsJson deffectsJson = new TowerDeffectsJson(towerInspectionId, deffectsJsonList);

        Response response = null;
        try {
            response = apiArmIS.uploadLineTowerDeffects(deffectsJson).execute();
        } catch (IOException e) {
            DBLog.e("TOWER DEFECTS", "Ошибка отправки дефектов опоры");
            DBLog.e("TOWER DEFECTS", e);
            e.printStackTrace();
            return 0;
        }

        return processResult(response);

    }

    private long exportTowerDeffectsPhotos(List<InspectionPhoto> photos, long towerInspectionId) {

        if (photos == null || photos.size() == 0) {
            return 0;
        }

        for (InspectionPhoto photo : photos) {
            uploadPhoto(photo, towerInspectionId, EquipmentType.TOWER);
        }

        return 0;
    }

    private boolean uploadPhoto(InspectionPhoto photo, long inspectionId, EquipmentType equipmentType) {

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

            UploadInspectionImageInfo imageInfo = new UploadInspectionImageInfo(file.getName(), inspectionId);
            Gson gson = new Gson();
            String fileInfoJson = gson.toJson(imageInfo);
            RequestBody fileInfo = RequestBody.create(MediaType.parse("text/plain"), fileInfoJson);

            if (equipmentType.equals(EquipmentType.TOWER)) {
                response = apiArmIS.uploadTowerInspectionImage(fileToUpload, fileInfo).execute();
            }

            if (equipmentType.equals(EquipmentType.LINE_SECTION)) {
                response = apiArmIS.uploadSectionInspectionImage(fileToUpload, fileInfo).execute();
            }


        } catch (IOException e) {
            DBLog.e("UPLOAD FILE", "Ошибка отправки фотографии осмотра");
            DBLog.e("UPLOAD FILE", e);

            e.printStackTrace();
            return false;
        } catch (Exception e) {
            DBLog.e("UPLOAD FILE", "Ошибка отправки фотографии осмотра");
            DBLog.e("UPLOAD FILE", e);
            e.printStackTrace();
            return false;
        }

        if (response.body() == null) {
            return false;
        }

        UploadRes uploadRes = (UploadRes) response.body();
        DBLog.d("UPLOAD FILE:", "result " + uploadRes.getStatus());
        if (uploadRes.getStatus() != 200) {
            return false;
        }

        return true;
    }

    private long processResult(Response response) {
        if (response.body() == null) {
            return 0;
        }

        UploadRes uploadRes = (UploadRes) response.body();
        DBLog.d("UPLOAD :", "result " + uploadRes.getStatus());
        if (uploadRes.getStatus() != 200) {
            DBLog.d("ERROR :", response.body().toString());
            return 0;
        }

        return uploadRes.getId();
    }

    private void exportSectionInfo(ObservableEmitter<String> emitter, LineSection section) {

        SectionJson sectionJson = new SectionJson(
                section.getLineUniqId(),
                section.getTowerFromUniqId(),
                section.getTowerToUniqId(),
                section.getName(),
                (section.getMaterial() != null) ? section.getMaterial().getId() : 0
        );


        Response response = null;
        try {
            response = apiArmIS.uploadLineSectionInfo(sectionJson).execute();
        } catch (IOException e) {
            DBLog.e("UPLOAD SECTION INFO", "Ошибка отправки информации о пролете");
            DBLog.e("UPLOAD SECTION INFO", e);
            e.printStackTrace();
            return;
        }

        if (response.body() == null) {
            emitter.onNext("Ошибка обновления информации о пролете");
            return;
        }

        UploadRes uploadRes = (UploadRes) response.body();
        DBLog.d("UPLOAD :", "result " + uploadRes.getStatus());
        if (uploadRes.getStatus() != 200) {
            emitter.onNext("Ошибка обновления информации о пролете");
        }


    }

    private long exportSectionInspection(long lineInspectionId, InspectedSection inspectedSection) {

        long timestamp = 0;
        if (inspectedSection.getInspection().getInspectionDate() != null) {
            timestamp = inspectedSection.getInspection().getInspectionDate().getTime() / 1000L;
        }

        SectionInspectionJson inspectionJson = new SectionInspectionJson(
                lineInspectionId,
                inspectedSection.getSection().getLineUniqId(),
                inspectedSection.getSection().getTowerFromUniqId(),
                inspectedSection.getSection().getTowerToUniqId(),
                inspectedSection.getInspection().getComment(),
                timestamp
        );

        Response response = null;
        try {
            response = apiArmIS.uploadLineSectionInspection(inspectionJson).execute();
        } catch (IOException e) {
            DBLog.e("UPLOAD SECTION INSPECTION", "Ошибка отправки информации о осмотре пролета");
            DBLog.e("UPLOAD SECTION INSPECTION", e);
            e.printStackTrace();
            return 0;
        }

        return processResult(response);
    }

    private long exportSectionDeffects(LineSection section, List<LineSectionDeffect> deffectList, long sectionInspectionId) {

        List<SectionDeffectJson> deffectsJsonList = new ArrayList<>();
        for (LineSectionDeffect deffect : deffectList) {

            if(deffect.getValue() == 0){
                continue;
            }

            deffectsJsonList.add(new SectionDeffectJson(
                    section.getLineUniqId(),
                    section.getTowerFromUniqId(),
                    section.getTowerToUniqId(),
                    (int) deffect.getDeffectType().getId(),
                    deffect.getValue()
            ));
        }

        SectionDeffectsJson deffectsJson = new SectionDeffectsJson(sectionInspectionId, deffectsJsonList);

        Response response = null;
        try {
            response = apiArmIS.uploadLineSectionDeffects(deffectsJson).execute();
        } catch (IOException e) {
            DBLog.e("UPLOAD SECTION DEFECT", "Ошибка отправки информации о дефекте пролета");
            DBLog.e("UPLOAD SECTION DEFECT", e);
            e.printStackTrace();
            return 0;
        }

        return processResult(response);

    }

    private void exportSectionDeffectsPhotos(List<InspectionPhoto> photos, long sectionInspectionId) {
        if (photos == null || photos.size() == 0) {
            return;
        }

        for (InspectionPhoto photo : photos) {
            uploadPhoto(photo, sectionInspectionId, EquipmentType.LINE_SECTION);
        }

        return;
    }
}

