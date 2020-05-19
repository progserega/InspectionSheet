package ru.drsk.progserega.inspectionsheet.entities.inspections;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.TransformerSubstation;

public class InspectedTransformerSubstation extends InspectedStation implements IStationInspection {

    private TransformerSubstation transformerSubstation;

//    private List<TransformerInspection> transformerInspections;

    private List<StationEquipmentInspection> stationEquipmentInspections;

//    public InspectedTransformerSubstation(TransformerSubstation transformerSubstation, List<TransformerInspection> transformerInspections,  List<InspectionItem> stationInspectionItems) {
//        this.transformerSubstation = transformerSubstation;
//        this.transformerInspections = transformerInspections;
//        this.stationInspectionItems = stationInspectionItems;
//    }


    public InspectedTransformerSubstation(TransformerSubstation transformerSubstation, List<InspectionItem> stationInspectionItems, List<StationEquipmentInspection> stationEquipmentInspections) {
        this.transformerSubstation = transformerSubstation;
        this.stationEquipmentInspections = stationEquipmentInspections;
        this.stationInspectionItems = stationInspectionItems;
    }

//    @Override
//    public List<TransformerInspection> getTransformerInspections() {
//        return transformerInspections;
//    }

    @Override
    public List<StationEquipmentInspection> getStationEquipmentInspections() {
        return stationEquipmentInspections;
    }

//    public void setInspection(List<TransformerInspection> inspections) {
//        this.transformerInspections = inspections;
//    }

    @Override
    public Equipment getStation() {
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
