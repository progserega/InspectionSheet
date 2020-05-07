package ru.drsk.progserega.inspectionsheet.entities.inspections;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.Substation;

public class InspectedSubstation extends InspectedStation implements IStationInspection {
    private Substation substation;

    private List<TransformerInspection> transformerInspections;

    public Substation getSubstation() {
        return substation;
    }

    public InspectedSubstation(Substation substation, List<TransformerInspection> transformerInspections, List<InspectionItem> stationInspectionItems) {
       this.substation = substation;
       this.transformerInspections = transformerInspections;
       this.stationInspectionItems = stationInspectionItems;
    }

    public String getSubstationName() {
        return substation.getName();
    }

    @Override
    public List<TransformerInspection> getTransformerInspections() {
        return transformerInspections;
    }



    public void setInspection(List<TransformerInspection> inspections) {
        this.transformerInspections = inspections;
    }

    @Override
    public Equipment getEquipment() {
        return substation;
    }

    @Override
    public List<InspectionItem> getStationInspectionItems() {
        return this.stationInspectionItems;
    }

    @Override
    public List<InspectionPhoto> getCommonPhotos() {
        return commonPhotos;
    }
}
