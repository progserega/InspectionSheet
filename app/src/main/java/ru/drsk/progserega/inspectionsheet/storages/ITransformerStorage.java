package ru.drsk.progserega.inspectionsheet.storages;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;
import ru.drsk.progserega.inspectionsheet.entities.Transformer;
import ru.drsk.progserega.inspectionsheet.entities.TransformerInSlot;

public interface ITransformerStorage {
    Transformer getById(long transformerTypeId);

    List<Transformer> getAll();

    List<Transformer> getAllByInstallationInEquipment(EquipmentType type);

    List<TransformerInSlot> getBySubstantionId(long substantionId, EquipmentType type);

    long addToSubstation(long transformerTypeId, Equipment substation, int slot);


}
