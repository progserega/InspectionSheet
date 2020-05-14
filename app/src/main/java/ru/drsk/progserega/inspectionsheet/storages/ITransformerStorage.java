package ru.drsk.progserega.inspectionsheet.storages;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;
import ru.drsk.progserega.inspectionsheet.entities.TransformerType;
import ru.drsk.progserega.inspectionsheet.entities.Transformer;

public interface ITransformerStorage {
    TransformerType getById(long transformerTypeId);

    List<TransformerType> getAll();

    List<TransformerType> getAllByInstallationInEquipment(EquipmentType type);

    List<Transformer> getBySubstantionId(long substantionUniqId, EquipmentType substationType);

    long addToSubstation(long transformerTypeId, Equipment substation, int slot);


}
