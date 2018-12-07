package ru.drsk.progserega.inspectionsheet.entities.inspections;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.TransformerSubstation;

public class TransformerSubstationInspection implements ISubstationInspection {

    private TransformerSubstation transformerSubstation;

    private List<TransformerInspection> transformerInspections;

    public TransformerSubstationInspection(TransformerSubstation transformerSubstation) {
        this.transformerSubstation = transformerSubstation;
        transformerInspections = null;
    }

    @Override
    public List<TransformerInspection> getTransformerInspections() {
        return transformerInspections;
    }

    @Override
    public void setInspection(List<TransformerInspection> inspections) {
        this.transformerInspections = inspections;
    }

    @Override
    public Equipment getEquipment() {
        return transformerSubstation;
    }
}
