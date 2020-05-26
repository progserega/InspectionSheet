package ru.drsk.progserega.inspectionsheet.storages.sqlight;

import android.content.Context;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;
import ru.drsk.progserega.inspectionsheet.entities.Transformer;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItemType;
import ru.drsk.progserega.inspectionsheet.entities.inspections.PhotoSubject;
import ru.drsk.progserega.inspectionsheet.entities.inspections.StationEquipmentInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TransformerInspection;
import ru.drsk.progserega.inspectionsheet.storages.IInspectionStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.EquipmentPhotoDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.InspectionDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.InspectionPhotoDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.SubstationDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.SubstationEquipmentDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.TransformerSubstationDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.TransformerSubstationEquipmentDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.EquipmentPhotoModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.InspectionModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.InspectionPhotoModel;

public class InspectionStorage implements IInspectionStorage {

    private InspectionSheetDatabase db;
    private InspectionDao inspectionDao;
    private InspectionPhotoDao inspectionPhotoDao;
    private SubstationDao substationDao;
    private TransformerSubstationDao transformerSubstationDao;
    private TransformerSubstationEquipmentDao transformerSubstationEquipmentDao;
    private SubstationEquipmentDao substationEquipmentDao;
    private EquipmentPhotoDao equipmentPhotoDao;
    private Context context;

    public InspectionStorage(InspectionSheetDatabase db, Context context) {
        this.db = db;
        inspectionDao = db.inspectionDao();
        inspectionPhotoDao = db.inspectionPhotoDao();
        this.context = context;

        this.substationDao = db.substationDao();
        this.transformerSubstationDao = db.transformerSubstationDao();
        this.transformerSubstationEquipmentDao = db.transfSubstationEquipmentDao();
        this.substationEquipmentDao = db.substationEquipmentDao();
        this.equipmentPhotoDao = db.equipmentPhotoDao();
    }

//    @Override
//    public void saveInspection(TransformerInspection inspection) {
//
//        if (inspection == null) {
//            return;
//        }
//
//        for (InspectionItem inspectionItem : inspection.getInspectionItems()) {
//
//            if (inspectionItem.getType().equals(InspectionItemType.HEADER)) {
//                continue;
//            }
//
//            InspectionModel inspectionModel = new InspectionModel(
//                    inspection.getSubstation().getUniqId(),
//                    inspection.getSubstation().getType().getValue(),
//                    inspection.getTransformator().getId(),
//                    inspectionItem
//            );
//
//            if (inspectionItem.getId() == 0) {
//                long insertId = inspectionDao.insert(inspectionModel);
//                inspectionItem.setId(insertId);
//            } else {
//                inspectionDao.update(inspectionModel);
//            }
//
//            //save photos
//            for (InspectionPhoto photo : inspectionItem.getResult().getPhotos()) {
//
//                if (photo.getId() == 0) {
//                    InspectionPhotoModel photoModel = new InspectionPhotoModel(0, inspectionItem.getId(), photo.getPath(), PhotoSubject.TRANSFORMER.getType());
//                    long photoId = inspectionPhotoDao.insert(photoModel);
//                    photo.setId(photoId);
//                }
//            }
//        }
//
//        // updateSubstationInspectionInfo(inspection);
//
//        updateTransformerEquipmentInfo(inspection);
//
//        saveTransformerPhoto(inspection);
//    }

    @Override
    public void saveInspection(StationEquipmentInspection inspection) {
        if (inspection == null) {
            return;
        }

        List< InspectionItem > inspectionItemList = inspection.getInspectionItems();
        for (InspectionItem inspectionItem : inspectionItemList) {

            if (inspectionItem.getType().equals(InspectionItemType.HEADER)) {
                continue;
            }

            InspectionModel inspectionModel = new InspectionModel(
                    inspection.getStation().getUniqId(),
                    inspection.getStation().getType().getValue(),
                    inspection.getEquipment().getId(),
                    inspectionItem
            );

            if (inspectionItem.getId() == 0) {
                long insertId = inspectionDao.insert(inspectionModel);
                inspectionItem.setId(insertId);
            } else {
                inspectionDao.update(inspectionModel);
            }

            //save photos
            for (InspectionPhoto photo : inspectionItem.getResult().getPhotos()) {

                if (photo.getId() == 0) {
                    InspectionPhotoModel photoModel = new InspectionPhotoModel(0, inspectionItem.getId(), photo.getPath(), PhotoSubject.TRANSFORMER.getType());
                    long photoId = inspectionPhotoDao.insert(photoModel);
                    photo.setId(photoId);
                }
            }
        }

    }

    @Override
    public void saveStationInspection(Equipment station, List< InspectionItem > inspectionItems) {

        if (inspectionItems == null || inspectionItems.isEmpty()) {
            return;
        }

        for (InspectionItem inspectionItem : inspectionItems) {

            if (inspectionItem.getType().equals(InspectionItemType.HEADER)) {
                continue;
            }

            InspectionModel inspectionModel = new InspectionModel(
                    station.getUniqId(),
                    station.getType().getValue(),
                    0,
                    inspectionItem
            );

            if (inspectionItem.getId() == 0) {
                long insertId = inspectionDao.insert(inspectionModel);
                inspectionItem.setId(insertId);
            } else {
                inspectionDao.update(inspectionModel);
            }

            //save photos
            for (InspectionPhoto photo : inspectionItem.getResult().getPhotos()) {

                if (photo.getId() == 0) {
                    InspectionPhotoModel photoModel = new InspectionPhotoModel(0, inspectionItem.getId(), photo.getPath(), PhotoSubject.TRANSFORMER.getType());
                    long photoId = inspectionPhotoDao.insert(photoModel);
                    photo.setId(photoId);
                }
            }
        }

    }

    @Override
    public void saveStationCommonPhotos(Equipment station, List< InspectionPhoto > commonPhotos) {
        for (InspectionPhoto photo : commonPhotos) {

            if (photo.getId() == 0) {
                EquipmentPhotoModel photoModel = new EquipmentPhotoModel(
                        0,
                        station.getUniqId(),
                        station.getType().getValue(),
                        photo.getPath()
                );
                long photoId = equipmentPhotoDao.insert(photoModel);
                photo.setId(photoId);
            }
        }
    }

    /**
     * Загружает сохраненные данные осмотра трансформатора в подстанции или ТП
     *
     * @param inspection TransformerInspection осмотр трансформатора
     * @param inspection TransformerInspection осмотр трансформатора
     */
    @Override
    public void loadInspections(TransformerInspection inspection) {
        if (inspection == null) {
            return;
        }

        //сохраненные результаты
        List< InspectionModel > inspectionModels = inspectionDao.getByEquipment(
                inspection.getSubstation().getUniqId(),
                inspection.getSubstation().getType().getValue(),
                inspection.getTransformator().getId());

        //заполняем сохраненными результатами
        fillInspectionValues(inspection.getInspectionItems(), inspectionModels);
        loadInspectionPhotos(inspection.getInspectionItems());
    }

    @Override
    public void loadInspections(StationEquipmentInspection inspection) {
        if (inspection == null) {
            return;
        }

        //сохраненные результаты
        List< InspectionModel > inspectionModels = inspectionDao.getByEquipment(
                inspection.getStation().getUniqId(),
                inspection.getStation().getType().getValue(),
                inspection.getEquipment().getId());

        //заполняем сохраненными результатами
        fillInspectionValues(inspection.getInspectionItems(), inspectionModels);
        loadInspectionPhotos(inspection.getInspectionItems());
    }

    /**
     * Загружает сохраненные данные осмотров посдстанции/ТП вцелом
     *
     * @param stationUniqId   long уникальный идентификатор подстанции или ТП
     * @param inspectionItems список элементов осмотра
     */
    @Override
    public void loadStationInspections(long stationUniqId, List< InspectionItem > inspectionItems) {

        if (inspectionItems == null || inspectionItems.isEmpty()) {
            return;
        }
        //сохраненные результаты
        List< InspectionModel > inspectionModels = inspectionDao.getByStation(stationUniqId);

        //заполняем сохраненными результатами
        fillInspectionValues(inspectionItems, inspectionModels);
        loadInspectionPhotos(inspectionItems);
    }

    private void fillInspectionValues(List< InspectionItem > inspectionItems, List< InspectionModel > inspectionModels) {
        Map< Long, InspectionModel > inpectionsMap = new HashMap<>();
        for (InspectionModel inspectionModel : inspectionModels) {
            inpectionsMap.put(inspectionModel.getDeffectId(), inspectionModel);
        }

        for (InspectionItem inspectionItem : inspectionItems) {

            if (inspectionItem.getType().equals(InspectionItemType.HEADER)) {
                continue;
            }

            InspectionModel inspectionModel = inpectionsMap.get(Long.valueOf(inspectionItem.getValueId()));
            if (inspectionModel != null) {
                inspectionItem.setId(inspectionModel.getId());
                inspectionItem.getResult().setComment(inspectionModel.getDeffectComment());
                String valuesString = inspectionModel.getDeffectValues();
                if (!valuesString.isEmpty()) {
                    List< String > values = Arrays.asList(valuesString.split(","));
                    inspectionItem.getResult().getValues().addAll(values);
                }

                String subValuesString = inspectionModel.getDeffectSubValues();
                if (!subValuesString.isEmpty()) {
                    List< String > subValues = Arrays.asList(subValuesString.split(","));
                    inspectionItem.getResult().getSubValues().addAll(subValues);
                }
            } else {
                inspectionItem.setId(0);
            }

        }
    }

    private void loadInspectionPhotos(List< InspectionItem > inspectionItems) {
        for (InspectionItem inspectionItem : inspectionItems) {

            if (inspectionItem.getType().equals(InspectionItemType.HEADER)) {
                continue;
            }

            if (inspectionItem.getId() == 0) {
                continue;
            }

            //Load photos
            List< InspectionPhotoModel > photoModels = inspectionPhotoDao.getByInspection(inspectionItem.getId(), PhotoSubject.TRANSFORMER.getType());
            for (InspectionPhotoModel photoModel : photoModels) {
                inspectionItem.getResult().getPhotos().add(new InspectionPhoto(photoModel.getId(), photoModel.getPhotoPath(), context));
            }
        }
    }

    @Deprecated
    @Override
    public void updateSubstationInspectionInfo(TransformerInspection inspection) {
        Equipment equipment = inspection.getSubstation();
        if (equipment.getType().equals(EquipmentType.TP)) {
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

    @Override
    public void updateStationInspectionInfo(Equipment station, Date inspectionDate, float inspectionPercent) {

        db.stationDao().updateInspectionInfo(station.getUniqId(), inspectionDate, inspectionPercent);
//        if (station.getType().equals(EquipmentType.TP)) {
//            transformerSubstationDao.updateInspectionInfo(
//                    station.getId(),
//                    inspectionDate,
//                    inspectionPercent);
//        }
//
//        if (station.getType().equals(EquipmentType.SUBSTATION)) {
//            substationDao.updateInspectionInfo(
//                    station.getId(),
//                    inspectionDate,
//                    inspectionPercent);
//        }

    }

    private void updateTransformerEquipmentInfo(TransformerInspection inspection) {
        Equipment equipment = inspection.getSubstation();
        Transformer transformer = inspection.getTransformator();
        if (equipment.getType().equals(EquipmentType.TP)) {
            transformerSubstationEquipmentDao.updateTransformerCommonInfo(transformer.getYear(), transformer.getInspectionDate(), transformer.getId());
        }

        if (equipment.getType().equals(EquipmentType.SUBSTATION)) {
            substationEquipmentDao.updateTransformerCommonInfo(transformer.getYear(), transformer.getInspectionDate(), transformer.getId());
        }
    }

    private void saveTransformerPhoto(TransformerInspection inspection) {
        for (InspectionPhoto photo : inspection.getTransformator().getPhotoList()) {

            if (photo.getId() == 0) {
                EquipmentPhotoModel photoModel = new EquipmentPhotoModel(
                        0,
                        inspection.getTransformator().getId(),
                        inspection.getSubstation().getType().getValue(),
                        photo.getPath()
                );
                long photoId = equipmentPhotoDao.insert(photoModel);
                photo.setId(photoId);
            }
        }
    }
}
