package ru.drsk.progserega.inspectionsheet.storages.sqlight;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Line;
import ru.drsk.progserega.inspectionsheet.entities.Tower;
import ru.drsk.progserega.inspectionsheet.entities.Voltage;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineSectionInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.PhotoSubject;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineSectionDeffect;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TowerDeffect;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TowerInspection;
import ru.drsk.progserega.inspectionsheet.storages.ICatalogStorage;
import ru.drsk.progserega.inspectionsheet.storages.ILineInspectionStorage;
import ru.drsk.progserega.inspectionsheet.storages.ILineDeffectTypesStorage;
import ru.drsk.progserega.inspectionsheet.storages.ILineStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.InspectionPhotoModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.LineInspectionModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.LineModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.LineSectionDeffectModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.LineSectionInspectionModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TowerDeffectModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TowerInspectionModel;

public class LineInspectionStorage implements ILineInspectionStorage {

    private InspectionSheetDatabase db;
    private Context context;
    private ILineDeffectTypesStorage deffectTypesStorage;
    private ICatalogStorage catalogStorage;
    private ILineStorage lineStorage;

    public LineInspectionStorage(InspectionSheetDatabase db, Context context, ILineDeffectTypesStorage deffectTypesStorage, ICatalogStorage catalogStorage, ILineStorage lineStorage) {
        this.db = db;
        this.context = context;
        this.deffectTypesStorage = deffectTypesStorage;
        this.catalogStorage = catalogStorage;
        this.lineStorage = lineStorage;
    }

    @Override
    public List< TowerDeffect > getTowerDeffects(long towerUniqId) {
        List< TowerDeffectModel > towerDeffectModels = db.towerDeffectDao().getTowerDeffects(towerUniqId);
        List< TowerDeffect > deffects = new ArrayList<>();
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

        List< InspectionPhotoModel > photoModels = db.inspectionPhotoDao().getByInspection(inspectionModel.getId(), PhotoSubject.TOWER.getType());
        List< InspectionPhoto > photos = new ArrayList<>();
        for (InspectionPhotoModel photoModel : photoModels) {
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
    public List< TowerInspection > getTowerInspectionByLine(long lineUniqId) {
        List< TowerInspection > inspections = new ArrayList<>();
        List< TowerInspectionModel > inspectionModels = db.towerInspectionDao().getByLine(lineUniqId);
        if (inspectionModels.isEmpty()) {
            return inspections;
        }

        for (TowerInspectionModel inspectionModel : inspectionModels) {

            List< InspectionPhotoModel > photoModels = db.inspectionPhotoDao().getByInspection(inspectionModel.getId(), PhotoSubject.TOWER.getType());
            List< InspectionPhoto > photos = new ArrayList<>();
            for (InspectionPhotoModel photoModel : photoModels) {
                photos.add(new InspectionPhoto(photoModel.getId(), photoModel.getPhotoPath(), context));
            }

            inspections.add(new TowerInspection(
                    inspectionModel.getId(),
                    inspectionModel.getTowerUniqId(),
                    inspectionModel.getComment(),
                    photos,
                    inspectionModel.getInspectionDate()
            ));
        }

        return inspections;
    }

    @Override
    public List< LineSectionInspection > getSectionInspectionByLine(long lineUniqId) {

        List< LineSectionInspection > inspections = new ArrayList<>();
        List< LineSectionInspectionModel > inspectionModels = db.lineSectionInspectionDao().getByLine(lineUniqId);
        if (inspectionModels.isEmpty()) {
            return inspections;
        }

        for (LineSectionInspectionModel inspectionModel : inspectionModels) {

            List< InspectionPhotoModel > photoModels = db.inspectionPhotoDao().getByInspection(inspectionModel.getId(), PhotoSubject.SECTION.getType());
            List< InspectionPhoto > photos = new ArrayList<>();
            for (InspectionPhotoModel photoModel : photoModels) {
                photos.add(new InspectionPhoto(photoModel.getId(), photoModel.getPhotoPath(), context));
            }

            inspections.add(new LineSectionInspection(
                    inspectionModel.getId(),
                    inspectionModel.getSectionId(),
                    inspectionModel.getComment(),
                    photos,
                    inspectionModel.getInspectionDate()
            ));
        }

        return inspections;
    }

    @Override
    public void saveToweInspection(TowerInspection inspection) {

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
                InspectionPhotoModel photoModel = new InspectionPhotoModel(
                        0,
                        inspection.getId(),
                        photo.getPath(),
                        PhotoSubject.TOWER.getType()
                );
                long photoId = db.inspectionPhotoDao().insert(photoModel);
                photo.setId(photoId);
            }
        }
    }

    @Override
    public List< LineSectionDeffect > getSectionDeffects(long sectionId) {

        List< LineSectionDeffectModel > lineSectionDeffects = db.lineSectionDeffectDao().getLineSectionDeffects(sectionId);

        List< LineSectionDeffect > deffects = new ArrayList<>();
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

    @Override
    public LineSectionInspection getSectionInspection(long sectionId) {
        LineSectionInspectionModel inspectionModel = db.lineSectionInspectionDao().getBySectionId(sectionId);
        if (inspectionModel == null) {
            return null;
        }

        List< InspectionPhotoModel > photoModels = db.inspectionPhotoDao().getByInspection(inspectionModel.getId(), PhotoSubject.SECTION.getType());
        List< InspectionPhoto > photos = new ArrayList<>();
        for (InspectionPhotoModel photoModel : photoModels) {
            photos.add(new InspectionPhoto(photoModel.getId(), photoModel.getPhotoPath(), context));
        }

        LineSectionInspection sectionInspection = new LineSectionInspection(
                inspectionModel.getId(),
                inspectionModel.getSectionId(),
                inspectionModel.getComment(),
                inspectionModel.getInspectionDate()
        );

        sectionInspection.setPhotos(photos);
        return sectionInspection;
    }

    @Override
    public void saveSectionInspection(LineSectionInspection inspection) {
        LineSectionInspectionModel inspectionModel = new LineSectionInspectionModel(
                inspection.getId(),
                inspection.getSectionId(),
                inspection.getComment(),
                inspection.getInspectionDate()
        );

        if (inspection.getId() == 0) {
            long id = db.lineSectionInspectionDao().addInspection(inspectionModel);
            inspection.setId(id);
        } else {
            db.lineSectionInspectionDao().updateInspection(inspectionModel);
        }

        saveSectionInspectionPhotos(inspection);
    }

    private void saveSectionInspectionPhotos(LineSectionInspection inspection) {
        for (InspectionPhoto photo : inspection.getPhotos()) {

            if (photo.getId() == 0) {
                InspectionPhotoModel photoModel = new InspectionPhotoModel(
                        0,
                        inspection.getId(),
                        photo.getPath(),
                        PhotoSubject.SECTION.getType()
                );
                long photoId = db.inspectionPhotoDao().insert(photoModel);
                photo.setId(photoId);
            }
        }
    }

    @Override
    public LineInspection getLineInspection(long lineId) {

        Line line = lineStorage.getById(lineId);
        if (line == null) {
            return null;
        }

        LineInspectionModel lineInspectionModel = db.lineInspectionDao().getByLineUniqId(line.getUniqId());
        if (lineInspectionModel == null) {
            return new LineInspection(0, line);
        }

        return new LineInspection(
                lineInspectionModel.getId(),
                line,
                catalogStorage.getInspectionTypeById(lineInspectionModel.getInspectionType()),
                lineInspectionModel.getInspectorName(),
                lineInspectionModel.getInspectionDate()
        );
    }

    @Override
    public long saveLineInspection(LineInspection inspection) {
        LineInspectionModel model = new LineInspectionModel(
                inspection.getId(),
                inspection.getLine().getUniqId(),
                inspection.getInspectorName(),
                inspection.getInspectionType().getId(),
                inspection.getInspectionDate()
        );

        if (model.getId() == 0) {
            long id = db.lineInspectionDao().addInspection(model);
            inspection.setId(id);
        } else {
            db.lineInspectionDao().updateInspection(model);
        }
        return inspection.getId();
    }

//    @Override
//    public void updateLineInspection(LineInspection inspection) {
//        LineInspectionModel model = new LineInspectionModel(
//                inspection.getId(),
//                inspection.getLine().getUniqId(),
//                inspection.getInspectorName(),
//                inspection.getInspectionType().getId(),
//                inspection.getInspectionDate()
//        );
//        db.lineInspectionDao().updateInspection(model);
//    }


    @Override
    public List< LineInspection > getAllLineInspections() {
        List< LineInspectionModel > allInspections = db.lineInspectionDao().loadAll();

        List< LineInspection > inspections = new ArrayList<>();

        for (LineInspectionModel inspectionModel : allInspections) {
            Line line = lineStorage.getByUniqId(inspectionModel.getLineUniqId());
            if (line == null) {
                continue;
            }
            LineInspection lineInspection = new LineInspection(
                    inspectionModel.getId(),
                    line,
                    catalogStorage.getInspectionTypeById(inspectionModel.getInspectionType()),
                    inspectionModel.getInspectorName(),
                    inspectionModel.getInspectionDate()
            );

            inspections.add(lineInspection);
        }

        return inspections;
    }
}
