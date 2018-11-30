package ru.drsk.progserega.inspectionsheet.storages.stub;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Transformer;
import ru.drsk.progserega.inspectionsheet.storages.ITransformerStorage;

public class TransformerStorageStub implements ITransformerStorage {

    @Override
    public List<Transformer> getBySubstantionId(long substantionId) {

        List<Transformer> transformators = new ArrayList<>();

        transformators.add(new Transformer(1, "SteTransformator 1"));
        transformators.add(new Transformer(2, "SteTransformator 2"));
        transformators.add(new Transformer(3, "SteTransformator 3"));
        transformators.add(new Transformer(4, "SteTransformator 4"));
        transformators.add(new Transformer(5, "SteTransformator 5"));
        return transformators;
    }
}


