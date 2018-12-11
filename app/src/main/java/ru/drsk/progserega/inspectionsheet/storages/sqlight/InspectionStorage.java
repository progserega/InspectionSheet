package ru.drsk.progserega.inspectionsheet.storages.sqlight;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.drsk.progserega.inspectionsheet.entities.inspections.Deffect;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItemType;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TransformerInspection;
import ru.drsk.progserega.inspectionsheet.storages.IInspectionStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.InspectionDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.InspectionPhotoDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.InspectionModel;

public class InspectionStorage implements IInspectionStorage {

    private InspectionSheetDatabase db;
    private InspectionDao inspectionDao;
    private InspectionPhotoDao inspectionPhotoDao;

    public InspectionStorage(InspectionSheetDatabase db) {
        this.db = db;
        inspectionDao = db.inspectionDao();
        inspectionPhotoDao = db.inspectionPhotoDao();
    }

    @Override
    public void saveInspection(TransformerInspection inspection) {

        if (inspection == null) {
            return;
        }

        for (InspectionItem inspectionItem : inspection.getInspectionItems()) {

            if (inspectionItem.getType().equals(InspectionItemType.HEADER)) {
                continue;
            }

            InspectionModel inspectionModel = new InspectionModel(
                    inspectionItem.getId(),
                    inspection.getSubstation().getId(),
                    inspection.getSubstation().getType().getValue(),
                    inspection.getTransformator().getId(),
                    inspectionItem.getValueId(),
                    inspectionItem.getDeffect().getDescription()
            );

            if (inspectionItem.getId() == 0) {
                long insertId = inspectionDao.insert(inspectionModel);
                inspectionItem.setId(insertId);
            } else {
                inspectionDao.update(inspectionModel);
            }

            //TODO save photos
        }

    }

    @Override
    public void loadInspections(TransformerInspection inspection) {
        if (inspection == null) {
            return;
        }

        List<InspectionModel> inspectionModels = inspectionDao.getByEquipment(
                inspection.getSubstation().getId(),
                inspection.getSubstation().getType().getValue(),
                inspection.getTransformator().getId());

        Map<Long, InspectionModel> inpectionsMap = new HashMap<>();
        for(InspectionModel inspectionModel: inspectionModels){
            inpectionsMap.put(inspectionModel.getDeffectId(), inspectionModel);
        }

        for (InspectionItem inspectionItem : inspection.getInspectionItems()) {

            if (inspectionItem.getType().equals(InspectionItemType.HEADER)) {
                continue;
            }

            InspectionModel inspectionModel = inpectionsMap.get(Long.valueOf(inspectionItem.getValueId()));
            if(inspectionModel != null) {
                inspectionItem.getDeffect().setDescription(inspectionModel.getDeffectValue());
            }

            //TODO Load photos
        }
    }
}
