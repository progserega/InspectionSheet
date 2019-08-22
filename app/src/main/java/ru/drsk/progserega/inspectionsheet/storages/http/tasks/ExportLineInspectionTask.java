package ru.drsk.progserega.inspectionsheet.storages.http.tasks;

import android.text.TextUtils;
import android.util.Log;

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
import ru.drsk.progserega.inspectionsheet.entities.LineSection;
import ru.drsk.progserega.inspectionsheet.entities.Tower;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectedLine;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectedSection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectedTower;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TowerDeffect;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TowerInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TransformerInspection;
import ru.drsk.progserega.inspectionsheet.storages.http.IApiInspectionSheet;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.LineInspectionJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.SectionJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.TowerDeffectJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.TowerDeffectsJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.TowerInspectionJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.TowerJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.TransformerInfo;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.TransformerInspectionResult;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.UploadInspectionImageInfo;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.UploadRes;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.UploadTransformerImageInfo;

public class ExportLineInspectionTask implements ObservableOnSubscribe< String > {

    private IApiInspectionSheet apiArmIS;
    private List< InspectedLine > inspectedLines;

    public ExportLineInspectionTask(IApiInspectionSheet apiArmIS, List< InspectedLine > inspectedLines) {
        this.apiArmIS = apiArmIS;
        this.inspectedLines = inspectedLines;
    }

    @Override
    public void subscribe(ObservableEmitter< String > emitter) throws Exception {

        long unixTime = System.currentTimeMillis() / 1000L;
        for (InspectedLine line : inspectedLines) {

            emitter.onNext("Экспорт данных линии: " + line.getLineInspection().getLine().getName());
            long inspectionId = exportInspection(line.getLineInspection());
            if (inspectionId == 0) {
                continue;//что-то пошло не так
            }

            for (InspectedTower inspectedTower : line.getInspectedTowers()) {

                emitter.onNext("Обновление данных опоры: " + inspectedTower.getTower().getName());
                exportTowerInfo(emitter, inspectedTower.getTower());

                emitter.onNext("Экспорт данных осмотра опоры: " + inspectedTower.getTower().getName());
                long towerInspectionId = exportTowerInspection(inspectionId, inspectedTower.getInspection());
                if (towerInspectionId == 0) {
                    continue; //что-то пошло не так
                }

                emitter.onNext("Экспорт деффектов опоры: " + inspectedTower.getTower().getName());
                exportTowerDeffects(inspectedTower.getDeffects(), towerInspectionId);

                emitter.onNext("Экспорт фотографий деффектов опоры: " + inspectedTower.getTower().getName());
                exportTowerDeffectsPhotos(inspectedTower.getInspection().getPhotos(), towerInspectionId);
            }

            for (InspectedSection inspectedSection : line.getInspectedSections()) {

                emitter.onNext("Обновление данных пролета: " + inspectedSection.getSection().getName());
                exportSectionInfo(emitter, inspectedSection.getSection());



            }


        }

        emitter.onComplete();
    }

    private long exportInspection(LineInspection inspection) {

        long timestamp = 0;
        if (inspection.getInspectionDate() != null) {
            timestamp = inspection.getInspectionDate().getTime() / 1000L;
        }

        LineInspectionJson inspectionJson = new LineInspectionJson(
                inspection.getLine().getUniqId(),
                inspection.getInspectorName(),
                inspection.getInspectionType().getId(),
                timestamp,
                0.0f
        );

        Response response = null;
        try {
            response = apiArmIS.uploadLineInspection(inspectionJson).execute();
        } catch (IOException e) {
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
            e.printStackTrace();
            return 0;
        }

        return processResult(response);

    }

    private void exportTowerInfo(ObservableEmitter< String > emitter, Tower tower) {

        TowerJson towerJson = new TowerJson(
                tower.getUniqId(),
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
            e.printStackTrace();
            emitter.onNext("Ошибка обновления информации о Опоре");
            return;
        }

        if (response.body() == null) {
            emitter.onNext("Ошибка обновления информации о Опоре");
            return;
        }

        UploadRes uploadRes = (UploadRes) response.body();
        Log.d("UPLOAD :", "result " + uploadRes.getStatus());
        if (uploadRes.getStatus() != 200) {
            emitter.onNext("Ошибка обновления информации о Опоре");
        }


    }

    private long exportTowerDeffects(List< TowerDeffect > deffectList, long towerInspectionId) {

        List< TowerDeffectJson > deffectsJsonList = new ArrayList<>();
        for (TowerDeffect deffect : deffectList) {
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
            e.printStackTrace();
            return 0;
        }

        return processResult(response);

    }

    private long exportTowerDeffectsPhotos(List< InspectionPhoto > photos, long towerInspectionId) {

        if (photos == null || photos.size() == 0) {
            return 0;
        }

        for (InspectionPhoto photo : photos) {
            uploadPhoto(photo, towerInspectionId);
        }

        return 0;
    }

    private boolean uploadPhoto(InspectionPhoto photo, long towerInspectionId) {

        File file = new File(photo.getPath());
        Log.d("UPLOAD FILE:", "Start upload file: " + file.getName());
        if (!file.exists()) {
            Log.d("UPLOAD FILE:", "File does not exist: " + file.getName());
            return false;
        }
        //RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
        Response response = null;
        try {
            //Не работает с кирилическими именами файлов
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);

            UploadInspectionImageInfo imageInfo = new UploadInspectionImageInfo(file.getName(), towerInspectionId);
            Gson gson = new Gson();
            String fileInfoJson = gson.toJson(imageInfo);
            RequestBody fileInfo = RequestBody.create(MediaType.parse("text/plain"), fileInfoJson);


            response = apiArmIS.uploadTowerInspectionImage(fileToUpload, fileInfo).execute();


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

    private long processResult(Response response) {
        if (response.body() == null) {
            return 0;
        }

        UploadRes uploadRes = (UploadRes) response.body();
        Log.d("UPLOAD :", "result " + uploadRes.getStatus());
        if (uploadRes.getStatus() != 200) {
            Log.d("ERROR :", response.body().toString());
            return 0;
        }

        return uploadRes.getId();
    }

    private void exportSectionInfo(ObservableEmitter< String > emitter, LineSection section) {

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
            e.printStackTrace();
            return;
        }

        if (response.body() == null) {
            emitter.onNext("Ошибка обновления информации о пролете");
            return;
        }

        UploadRes uploadRes = (UploadRes) response.body();
        Log.d("UPLOAD :", "result " + uploadRes.getStatus());
        if (uploadRes.getStatus() != 200) {
            emitter.onNext("Ошибка обновления информации о пролете");
        }


    }


}

