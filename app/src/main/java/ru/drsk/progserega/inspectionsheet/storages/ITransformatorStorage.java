package ru.drsk.progserega.inspectionsheet.storages;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Transformator;

public interface ITransformatorStorage {
    List<Transformator> getBySubstantionId(long substantionId);
}
