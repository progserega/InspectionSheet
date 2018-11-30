package ru.drsk.progserega.inspectionsheet.storages;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Transformer;

public interface ITransformerStorage {
    List<Transformer> getBySubstantionId(long substantionId);
}
