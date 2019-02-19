package ru.drsk.progserega.inspectionsheet.storages.stub;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;
import ru.drsk.progserega.inspectionsheet.entities.TransformerType;
import ru.drsk.progserega.inspectionsheet.entities.TransformerInSlot;
import ru.drsk.progserega.inspectionsheet.storages.ITransformerStorage;

public class TransformerStorageStub implements ITransformerStorage {

    @Override
    public List<TransformerType> getAll() {
        return null;
    }

    @Override
    public List<TransformerInSlot> getBySubstantionId(long substantionId, EquipmentType type) {

        List<TransformerInSlot> transformators = new ArrayList<>();

        transformators.add(new TransformerInSlot(1,1, new TransformerType( 1,"SteTransformator 1")));
        transformators.add(new TransformerInSlot(2,1, new TransformerType( 1,"SteTransformator 1")));
        transformators.add(new TransformerInSlot(3,1, new TransformerType( 1,"SteTransformator 1")));
        transformators.add(new TransformerInSlot(4,1, new TransformerType( 1,"SteTransformator 1")));
        transformators.add(new TransformerInSlot(5,1, new TransformerType( 1,"SteTransformator 1")));
        transformators.add(new TransformerInSlot(6,1, new TransformerType( 1,"SteTransformator 1")));
        transformators.add(new TransformerInSlot(7,1, new TransformerType( 1,"SteTransformator 1")));

        return transformators;
    }

    @Override
    public long addToSubstation(long transformerTypeId, Equipment substation, int slot) {
        return 0;
    }

    @Override
    public TransformerType getById(long transformerTypeId) {
        return null;
    }

    @Override
    public List<TransformerType> getAllByInstallationInEquipment(EquipmentType type) {
        return null;
    }
}


