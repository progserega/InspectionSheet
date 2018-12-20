package ru.drsk.progserega.inspectionsheet.storages.sqlight;

import android.content.Context;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;
import ru.drsk.progserega.inspectionsheet.entities.Transformer;
import ru.drsk.progserega.inspectionsheet.entities.inspections.DeffectPhoto;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItemType;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TransformerInspection;
import ru.drsk.progserega.inspectionsheet.storages.IInspectionStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.InspectionDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.InspectionPhotoDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.SubstationDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.TransformerSubstationDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.InspectionModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.InspectionPhotoModel;

public class InspectionStorage implements IInspectionStorage {

    private InspectionSheetDatabase db;
    private InspectionDao inspectionDao;
    private InspectionPhotoDao inspectionPhotoDao;
    private SubstationDao substationDao;
    private TransformerSubstationDao transformerSubstationDao;
    private Context context;

    public InspectionStorage(InspectionSheetDatabase db, Context context) {
        this.db = db;
        inspectionDao = db.inspectionDao();
        inspectionPhotoDao = db.inspectionPhotoDao();
        this.context = context;

        this.substationDao = db.substationDao();
        this.transformerSubstationDao = db.transformerSubstationDao();
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
                    inspection.getSubstation().getId(),
                    inspection.getSubstation().getType().getValue(),
                    inspection.getTransformator().getId(),
                    inspectionItem
            );

            if (inspectionItem.getId() == 0) {
                long insertId = inspectionDao.insert(inspectionModel);
                inspectionItem.setId(insertId);
            } else {
                inspectionDao.update(inspectionModel);
            }

            //save photos
            for (DeffectPhoto photo : inspectionItem.getResult().getPhotos()) {

                if (photo.getId() == 0) {
                    InspectionPhotoModel photoModel = new InspectionPhotoModel(0, inspectionItem.getId(), photo.getPath());
                    long photoId = inspectionPhotoDao.insert(photoModel);
                    photo.setId(photoId);
                }
            }
        }

        updateSubstationInspectionInfo(inspection);

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
        for (InspectionModel inspectionModel : inspectionModels) {
            inpectionsMap.put(inspectionModel.getDeffectId(), inspectionModel);
        }

        for (InspectionItem inspectionItem : inspection.getInspectionItems()) {

            if (inspectionItem.getType().equals(InspectionItemType.HEADER)) {
                continue;
            }

            InspectionModel inspectionModel = inpectionsMap.get(Long.valueOf(inspectionItem.getValueId()));
            if (inspectionModel != null) {
                inspectionItem.setId(inspectionModel.getId());
                inspectionItem.getResult().setComment(inspectionModel.getDeffectComment());
                String valuesString = inspectionModel.getDeffectValues();
                if (!valuesString.isEmpty()) {
                    List<String> values = Arrays.asList(valuesString.split(","));
                    inspectionItem.getResult().getValues().addAll(values);
                }

                String subValuesString = inspectionModel.getDeffectSubValues();
                if (!subValuesString.isEmpty()) {
                    List<String> subValues = Arrays.asList(subValuesString.split(","));
                    inspectionItem.getResult().getSubValues().addAll(subValues);
                }
            }

            //Load photos
            List<InspectionPhotoModel> photoModels = inspectionPhotoDao.getByInspection(inspectionItem.getId());
            for (InspectionPhotoModel photoModel : photoModels) {
                inspectionItem.getResult().getPhotos().add(new DeffectPhoto(photoModel.getId(), photoModel.getPhotoPath(), context));
            }
        }
    }

    private void updateSubstationInspectionInfo(TransformerInspection inspection) {
        Equipment equipment = inspection.getSubstation();
        if (equipment.getType().equals(EquipmentType.TRANS_SUBSTATION)) {
            transformerSubstationDao.updateInspectionInfo(
                    equipment.getId(),
                    equipment.getInspectionDate(),
                    equipment.getInspectionPercent());
        }

        if (equipment.getType().equals(EquipmentType.SUBSTATION)) {
            substationDao.updateInspectionInfo(
                    equipment.getId(),
                    equipment.getInspectionDate(),
                    equipment.getInspectionPercent());
        }

    }
}
