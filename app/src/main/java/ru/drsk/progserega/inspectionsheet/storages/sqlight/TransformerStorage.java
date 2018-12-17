package ru.drsk.progserega.inspectionsheet.storages.sqlight;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;
import ru.drsk.progserega.inspectionsheet.entities.Transformer;
import ru.drsk.progserega.inspectionsheet.storages.ITransformerStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.SubstationEquipmentDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.TransformerDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.TransformerSubstationEquipmentDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.SubstationEquipmentModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TransformerInsideSubstaionModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TransformerModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TransformerSubstationEuipmentModel;

public class TransformerStorage implements ITransformerStorage {

    private InspectionSheetDatabase db;
    private TransformerDao transformerDao;
    private TransformerSubstationEquipmentDao transformerSubstationEquipmentDao;
    private SubstationEquipmentDao substationEquipmentDao;

    public TransformerStorage(InspectionSheetDatabase db) {
        this.db = db;
        transformerDao = db.transformerDao();
        transformerSubstationEquipmentDao = db.transfSubstationEquipmentDao();
        substationEquipmentDao = db.substationEquipmentDao();
    }

    @Override
    public List<Transformer> getBySubstantionId(long substantionId, EquipmentType type) {

        List<Transformer> transformers = new ArrayList<>();
        if (type == EquipmentType.TRANS_SUBSTATION) {

            List<TransformerInsideSubstaionModel> transformerDBModels = transformerSubstationEquipmentDao.getBySubstation(substantionId);
            transformers = dbTranformerInsideSubstationModel(transformerDBModels);
        }

        if (type == EquipmentType.SUBSTATION) {

            List<TransformerInsideSubstaionModel> transformerDBModels = substationEquipmentDao.getBySubstation(substantionId);
            transformers = dbTranformerInsideSubstationModel(transformerDBModels);
        }

        return transformers;
    }

    private List<Transformer> dbTranformerInsideSubstationModel(List<TransformerInsideSubstaionModel> transformerDBModels) {
        List<Transformer> transformers = new ArrayList<>();
        for (TransformerInsideSubstaionModel transformerModel : transformerDBModels) {
            TransformerModel transformerDBModel = transformerModel.getTransformer();
            transformers.add(new Transformer(
                    transformerModel.getEquipmentId(),
                    transformerDBModel.getId(),
                    transformerDBModel.getDesc(),
                    transformerModel.getSlot()));
        }
        return transformers;
    }

    private List<Transformer> dbTransformerModelToEntity(List<TransformerModel> transformerDBModels) {
        List<Transformer> transformers = new ArrayList<>();
        for (TransformerModel transformerModel : transformerDBModels) {
            transformers.add(new Transformer( 0, transformerModel.getId(),  transformerModel.getDesc(), 0));
        }
        return transformers;
    }


    @Override
    public List<Transformer> getAll() {
        List<TransformerModel> transformerModelLit = transformerDao.loadAllTransformers();
        return dbTransformerModelToEntity(transformerModelLit);

    }

    @Override
    public long addToSubstation(long transformerTypeId, Equipment substation, int slot) {

        if(substation.getType().equals(EquipmentType.SUBSTATION)){
            SubstationEquipmentModel equipment = new SubstationEquipmentModel(0,substation.getId(), transformerTypeId, slot);
            return substationEquipmentDao.insert(equipment);
        }


        if(substation.getType().equals(EquipmentType.TRANS_SUBSTATION)){
            TransformerSubstationEuipmentModel euipmentModel = new TransformerSubstationEuipmentModel(0, substation.getId(), transformerTypeId, slot);
            return transformerSubstationEquipmentDao.insert(euipmentModel);
        }

        return 0;
    }

    @Override
    public Transformer getById(long transformerTypeId) {
        TransformerModel transformerModel = transformerDao.getById(transformerTypeId);
        if(transformerModel == null){
            return null;
        }
        return new Transformer(0, transformerModel.getId(), transformerModel.getDesc(), 0);
    }
}
