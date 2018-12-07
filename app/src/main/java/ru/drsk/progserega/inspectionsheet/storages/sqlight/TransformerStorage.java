package ru.drsk.progserega.inspectionsheet.storages.sqlight;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;
import ru.drsk.progserega.inspectionsheet.entities.Transformer;
import ru.drsk.progserega.inspectionsheet.storages.ITransformerStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.TransformerDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.TransformerSubstationEquipmentDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TransformerInsideSubstaionModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TransformerModel;

public class TransformerStorage implements ITransformerStorage {

    private InspectionSheetDatabase db;
    private TransformerDao transformerDao;
    private TransformerSubstationEquipmentDao transformerSubstationEquipmentDao;

    public TransformerStorage(InspectionSheetDatabase db) {
        this.db = db;
        transformerDao = db.transformerDao();
        transformerSubstationEquipmentDao = db.transfSubstationEquipmentDao();
    }

    @Override
    public List<Transformer> getBySubstantionId(long substantionId, EquipmentType type) {

        List<Transformer> transformers = new ArrayList<>();
        if(type == EquipmentType.TRANS_SUBSTATION){

            List<TransformerInsideSubstaionModel> transformerDBModels = transformerSubstationEquipmentDao.getBySubstation(substantionId);
            transformers = dbModelToEntity(transformerDBModels);
        }

        if(type == EquipmentType.SUBSTATION){
            //TODO временный хардкод!!!

            transformers.add(new Transformer(1, 1,"SteTransformator 1"));
            transformers.add(new Transformer(2, 1,"SteTransformator 2"));
            transformers.add(new Transformer(3, 1,"SteTransformator 3"));
            transformers.add(new Transformer(4, 1,"SteTransformator 4"));
            transformers.add(new Transformer(5, 1,"SteTransformator 5"));
        }

        return transformers;
    }

    private List<Transformer> dbModelToEntity(List<TransformerInsideSubstaionModel> transformerDBModels){
        List<Transformer> transformers = new ArrayList<>();
        for(TransformerInsideSubstaionModel transformerModel: transformerDBModels){
            TransformerModel transformerDBModel = transformerModel.getTransformer();
            transformers.add(new Transformer(transformerModel.getEquipmentId(), transformerDBModel.getId(), transformerDBModel.getDesc()));
        }
        return transformers;
    }
}
