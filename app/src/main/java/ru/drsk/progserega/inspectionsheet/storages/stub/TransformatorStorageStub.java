package ru.drsk.progserega.inspectionsheet.storages.stub;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Transformator;
import ru.drsk.progserega.inspectionsheet.storages.ITransformatorStorage;

public class TransformatorStorageStub implements ITransformatorStorage {

    @Override
    public List<Transformator> getBySubstantionId(long substantionId) {

        List<Transformator> transformators = new ArrayList<>();

        transformators.add(new Transformator(1, "Transformator 1"));
        transformators.add(new Transformator(2, "Transformator 2"));
        transformators.add(new Transformator(3, "Transformator 3"));
        transformators.add(new Transformator(4, "Transformator 4"));
        transformators.add(new Transformator(5, "Transformator 5"));
        return transformators;
    }
}


