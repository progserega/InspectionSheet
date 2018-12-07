package ru.drsk.progserega.inspectionsheet.entities.inspections;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;

public interface ISubstationInspection {

    void setInspection(List<TransformerInspection> inspections);

    List<TransformerInspection> getTransformerInspections();

    Equipment getEquipment();
}
