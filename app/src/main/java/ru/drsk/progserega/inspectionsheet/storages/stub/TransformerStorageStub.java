package ru.drsk.progserega.inspectionsheet.storages.stub;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;
import ru.drsk.progserega.inspectionsheet.entities.Transformer;
import ru.drsk.progserega.inspectionsheet.storages.ITransformerStorage;

public class TransformerStorageStub implements ITransformerStorage {

    @Override
    public List<Transformer> getAll() {
        return null;
    }

    @Override
    public List<Transformer> getBySubstantionId(long substantionId, EquipmentType type) {

        List<Transformer> transformators = new ArrayList<>();

        transformators.add(new Transformer(1, 1,"SteTransformator 1"));
        transformators.add(new Transformer(2, 1, "SteTransformator 2"));
        transformators.add(new Transformer(3, 1, "SteTransformator 3"));
        transformators.add(new Transformer(4, 1, "SteTransformator 4"));
        transformators.add(new Transformer(5, 1, "SteTransformator 5"));
        return transformators;
    }

    @Override
    public long addToSubstation(long transformerTypeId, Equipment substation) {
        return 0;
    }

    @Override
    public Transformer getById(long transformerTypeId) {
        return null;
    }
}


