package ru.drsk.progserega.inspectionsheet.storages.sqlight;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineSectionDeffect;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TowerDeffect;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TowerInspection;
import ru.drsk.progserega.inspectionsheet.storages.ILineInspectionStorage;
import ru.drsk.progserega.inspectionsheet.storages.ILineDeffectTypesStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.LineSectionDeffectModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TowerDeffectModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TowerInspectionModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TowerInspectionPhotoModel;

public class LineInspectionStorage implements ILineInspectionStorage {

    private InspectionSheetDatabase db;
    private Context context;
    private ILineDeffectTypesStorage deffectTypesStorage;

    public LineInspectionStorage(InspectionSheetDatabase db, Context context, ILineDeffectTypesStorage deffectTypesStorage) {
        this.db = db;
        this.context = context;
        this.deffectTypesStorage = deffectTypesStorage;
    }

    @Override
    public List<TowerDeffect> getTowerDeffects(long towerUniqId) {
        List<TowerDeffectModel> towerDeffectModels = db.towerDeffectDao().getTowerDeffects(towerUniqId);
        List<TowerDeffect> deffects = new ArrayList<>();
        for (TowerDeffectModel deffectModel : towerDeffectModels) {
            deffects.add(new TowerDeffect(
                    deffectModel.getId(),
                    towerUniqId,
                    deffectTypesStorage.getTowerDeffectById(deffectModel.getDeffectTypeId()),
                    deffectModel.getDeffectValue()
            ));
        }
        return deffects;
    }

    @Override
    public Long addTowerDeffect(TowerDeffect deffect) {

        TowerDeffectModel towerDeffectModel = new TowerDeffectModel(deffect.getId(), 0, deffect.getTowerUniqId(), deffect.getDeffectType().getId(), deffect.getValue());
        return db.towerDeffectDao().addDeffect(towerDeffectModel);
    }

    @Override
    public void updateTowerDeffect(TowerDeffect deffect) {
        TowerDeffectModel towerDeffectModel = new TowerDeffectModel(deffect.getId(), 0, deffect.getTowerUniqId(), deffect.getDeffectType().getId(), deffect.getValue());
        db.towerDeffectDao().updateDeffect(towerDeffectModel);
    }

    @Override
    public TowerInspection getTowerInspection(long towerUniqId) {
        TowerInspectionModel inspectionModel = db.towerInspectionDao().getByTower(towerUniqId);
        if (inspectionModel == null) {
            return null;
        }

        List<TowerInspectionPhotoModel> photoModels = db.towerInspectionPhotoDao().getByInspection(inspectionModel.getId());
        List<InspectionPhoto> photos = new ArrayList<>();
        for(TowerInspectionPhotoModel photoModel: photoModels){
            photos.add(new InspectionPhoto(photoModel.getId(), photoModel.getPhotoPath(), context));
        }

        return new TowerInspection(
                inspectionModel.getId(),
                inspectionModel.getTowerUniqId(),
                inspectionModel.getComment(),
                photos,
                inspectionModel.getInspectionDate()
        );
    }

    @Override
    public void saveInspection(TowerInspection inspection) {

        TowerInspectionModel inspectionModel = new TowerInspectionModel(
                inspection.getId(),
                0,
                inspection.getTowerUniqId(),
                inspection.getComment(),
                inspection.getInspectionDate()
        );

        if (inspection.getId() == 0) {
            long id = db.towerInspectionDao().addInspection(inspectionModel);
            inspection.setId(id);
        } else {
            db.towerInspectionDao().updateInspection(inspectionModel);
        }

        saveTowerInspectionPhotos(inspection);
    }

    private void saveTowerInspectionPhotos(TowerInspection inspection) {
        for (InspectionPhoto photo : inspection.getPhotos()) {

            if (photo.getId() == 0) {
                TowerInspectionPhotoModel photoModel = new TowerInspectionPhotoModel(
                        0,
                        inspection.getId(),
                        photo.getPath()
                );
                long photoId = db.towerInspectionPhotoDao().insert(photoModel);
                photo.setId(photoId);
            }
        }
    }

    @Override
    public List<LineSectionDeffect> getSectionDeffects(long sectionId) {

        List<LineSectionDeffectModel> lineSectionDeffects = db.lineSectionDeffectDao().getLineSectionDeffects(sectionId);

        List<LineSectionDeffect> deffects = new ArrayList<>();
        for (LineSectionDeffectModel deffectModel : lineSectionDeffects) {
            deffects.add(new LineSectionDeffect(
                    deffectModel.getId(),
                    sectionId,
                    deffectTypesStorage.getSectionDeffectById(deffectModel.getDeffectTypeId()),
                    deffectModel.getDeffectValue()
            ));
        }
        return deffects;
    }

    @Override
    public Long addSectionDeffect(LineSectionDeffect deffect) {
        LineSectionDeffectModel sectionDeffectModel = new LineSectionDeffectModel(
                deffect.getId(),
                deffect.getSectionId(),
                deffect.getDeffectType().getId(), deffect.getValue());
        return db.lineSectionDeffectDao().addDeffect(sectionDeffectModel);
    }

    @Override
    public void updateSectionDeffect(LineSectionDeffect deffect) {
        LineSectionDeffectModel sectionDeffectModel = new LineSectionDeffectModel(
                deffect.getId(),
                deffect.getSectionId(),
                deffect.getDeffectType().getId(), deffect.getValue());
        db.lineSectionDeffectDao().updateDeffect(sectionDeffectModel);
    }
}
