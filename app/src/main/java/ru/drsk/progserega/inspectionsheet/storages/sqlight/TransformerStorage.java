package ru.drsk.progserega.inspectionsheet.storages.sqlight;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;
import ru.drsk.progserega.inspectionsheet.entities.TransformerType;
import ru.drsk.progserega.inspectionsheet.entities.TransformerInSlot;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;
import ru.drsk.progserega.inspectionsheet.storages.ITransformerStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.EquipmentPhotoDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.SubstationEquipmentDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.TransformerDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.TransformerSubstationEquipmentDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.EquipmentPhotoModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.SubstationEquipmentModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TransformerInsideSubstaionModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TransformerModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TransformerSubstationEuipmentModel;

public class TransformerStorage implements ITransformerStorage {

    private InspectionSheetDatabase db;
    private TransformerDao transformerDao;
    private TransformerSubstationEquipmentDao transformerSubstationEquipmentDao;
    private SubstationEquipmentDao substationEquipmentDao;
    private EquipmentPhotoDao equipmentPhotoDao;
    private Context context;

    public TransformerStorage(InspectionSheetDatabase db, Context context) {
        this.db = db;
        transformerDao = db.transformerDao();
        transformerSubstationEquipmentDao = db.transfSubstationEquipmentDao();
        substationEquipmentDao = db.substationEquipmentDao();
        equipmentPhotoDao = db.equipmentPhotoDao();
        this.context = context;
    }

    @Override
    public List<TransformerInSlot> getBySubstantionId(long substantionUniqId, EquipmentType type) {

        List<TransformerInSlot> transformers = new ArrayList<>();
        if (type == EquipmentType.TRANS_SUBSTATION) {

            List<TransformerInsideSubstaionModel> transformerDBModels = transformerSubstationEquipmentDao.getBySubstation(substantionUniqId);
            transformers = dbTranformerInsideSubstationModel(transformerDBModels, type);
        }

        if (type == EquipmentType.SUBSTATION) {

            List<TransformerInsideSubstaionModel> transformerDBModels = substationEquipmentDao.getBySubstation(substantionUniqId);
            transformers = dbTranformerInsideSubstationModel(transformerDBModels, type);
        }

        return transformers;
    }

    private List<TransformerInSlot> dbTranformerInsideSubstationModel(List<TransformerInsideSubstaionModel> transformerDBModels, EquipmentType equipmentType) {
        List<TransformerInSlot> transformers = new ArrayList<>();
        for (TransformerInsideSubstaionModel transformerModel : transformerDBModels) {
            TransformerModel transformerDBModel = transformerModel.getTransformer();
            TransformerType transformerType = new TransformerType(transformerDBModel.getId(), transformerDBModel.getType() );
            TransformerInSlot transformerInSlot = new TransformerInSlot(
                    transformerModel.getEquipmentId(),
                    transformerModel.getSlot(),
                    transformerType,
                    transformerModel.getManufactureYear(),
                    transformerModel.getInspectioDate()
            );

            List<EquipmentPhotoModel> equipmentPhotoModels = equipmentPhotoDao.getByEquipment( transformerModel.getEquipmentId(), equipmentType.getValue());
            transformerInSlot.setPhotoList(equipmentPhotoModelToInspectionPhoto(equipmentPhotoModels));

            transformers.add(transformerInSlot);
        }
        return transformers;
    }

    private List<TransformerType> dbTransformerModelToEntity(List<TransformerModel> transformerDBModels) {
        List<TransformerType> transformerTypes = new ArrayList<>();
        for (TransformerModel transformerModel : transformerDBModels) {
            transformerTypes.add(new TransformerType(  transformerModel.getId(),  transformerModel.getType()));
        }
        return transformerTypes;
    }


    @Override
    public List<TransformerType> getAll() {
        List<TransformerModel> transformerModelLit = transformerDao.loadAllTransformers();
        return dbTransformerModelToEntity(transformerModelLit);

    }

    @Override
    public List<TransformerType> getAllByInstallationInEquipment(EquipmentType type) {
        if(type.equals(EquipmentType.TRANS_SUBSTATION)){
            List<TransformerModel> transformerModelLit = transformerDao.loadAllTransformersByInstallation("transformer_substation");
            return dbTransformerModelToEntity(transformerModelLit);
        }

        if(type.equals(EquipmentType.SUBSTATION)){
            List<TransformerModel> transformerModelLit = transformerDao.loadAllTransformersByInstallation("substation");
            return dbTransformerModelToEntity(transformerModelLit);
        }

        return new ArrayList<>();


    }

    @Override
    public long addToSubstation(long transformerTypeId, Equipment substation, int slot) {

        if(substation.getType().equals(EquipmentType.SUBSTATION)){
            SubstationEquipmentModel equipment = new SubstationEquipmentModel(0,substation.getUniqId(), transformerTypeId, slot, 0, null);
            return substationEquipmentDao.insert(equipment);
        }


        if(substation.getType().equals(EquipmentType.TRANS_SUBSTATION)){
            TransformerSubstationEuipmentModel euipmentModel = new TransformerSubstationEuipmentModel(0, substation.getUniqId(), transformerTypeId, slot, 0, null);
            return transformerSubstationEquipmentDao.insert(euipmentModel);
        }

        return 0;
    }

    @Override
    public TransformerType getById(long transformerTypeId) {
        TransformerModel transformerModel = transformerDao.getById(transformerTypeId);
        if(transformerModel == null){
            return null;
        }
        return new TransformerType( transformerModel.getId(), transformerModel.getDesc());
    }


    private List<InspectionPhoto> equipmentPhotoModelToInspectionPhoto(List<EquipmentPhotoModel> photoModels){
        List<InspectionPhoto>  photos = new ArrayList<>();
        for(EquipmentPhotoModel equipmentPhotoModel: photoModels){
            photos.add(new InspectionPhoto(equipmentPhotoModel.getId(), equipmentPhotoModel.getPhotoPath(), this.context ));
        }
        return photos;
    }
}
