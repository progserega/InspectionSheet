package ru.drsk.progserega.inspectionsheet.entities.inspections;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.Substation;

public class SubstationInspection implements ISubstationInspection {
    private Substation substation;

    private List<TransformerInspection> transformerInspections;

    public Substation getSubstation() {
        return substation;
    }

    public SubstationInspection(Substation substation) {
        this.substation = substation;
        transformerInspections = null;
    }

    public String getSubstationName() {
        return substation.getName();
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
        return substation;
    }
}
