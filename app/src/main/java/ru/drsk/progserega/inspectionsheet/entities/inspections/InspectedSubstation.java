package ru.drsk.progserega.inspectionsheet.entities.inspections;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.Substation;

public class InspectedSubstation extends InspectedStation implements IStationInspection {
    private Substation substation;

    //private List<TransformerInspection> transformerInspections;

    private List<StationEquipmentInspection> equipmentInspections;

    public InspectedSubstation(Substation substation, List<InspectionItem> stationInspectionItems, List<StationEquipmentInspection> equipmentInspections) {
        this.substation = substation;
        this.stationInspectionItems = stationInspectionItems;
        this.equipmentInspections = equipmentInspections;
    }

    //    public Substation getSubstation() {
//        return substation;
//    }


//    public String getSubstationName() {
//        return substation.getName();
//    }

//    @Override
//    public List<TransformerInspection> getTransformerInspections() {
//        return transformerInspections;
//
//    }

    @Override
    public List<StationEquipmentInspection> getStationEquipmentInspections() {
        return equipmentInspections;
    }

//    public void setInspection(List<TransformerInspection> inspections) {
//        this.transformerInspections = inspections;
//    }

    @Override
    public Equipment getStation() {
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
