package ru.drsk.progserega.inspectionsheet.entities.inspections;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.TransformerSubstation;

public class InspectedTransformerSubstation extends InspectedStation implements IStationInspection {

    private TransformerSubstation transformerSubstation;

    private List<TransformerInspection> transformerInspections;

    public InspectedTransformerSubstation(TransformerSubstation transformerSubstation, List<TransformerInspection> transformerInspections,  List<InspectionItem> stationInspectionItems) {
        this.transformerSubstation = transformerSubstation;
        this.transformerInspections = transformerInspections;
        this.stationInspectionItems = stationInspectionItems;
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
        return transformerSubstation;
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
