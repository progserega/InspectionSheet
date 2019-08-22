package ru.drsk.progserega.inspectionsheet.storages;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;
import ru.drsk.progserega.inspectionsheet.entities.TransformerType;
import ru.drsk.progserega.inspectionsheet.entities.TransformerInSlot;

public interface ITransformerStorage {
    TransformerType getById(long transformerTypeId);

    List<TransformerType> getAll();

    List<TransformerType> getAllByInstallationInEquipment(EquipmentType type);

    List<TransformerInSlot> getBySubstantionId(long substantionUniqId, EquipmentType type);

    long addToSubstation(long transformerTypeId, Equipment substation, int slot);


}
