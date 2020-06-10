package ru.drsk.progserega.inspectionsheet.entities.inspections;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.Substation;

public class InspectedSubstation extends InspectedStation implements IStationInspection {
    private Substation substation;

    private List< StationEquipmentInspection > equipmentInspections;

    public InspectedSubstation(Substation substation, List< InspectionItem > stationInspectionItems, List< StationEquipmentInspection > equipmentInspections) {
        this.substation = substation;
        this.stationInspectionItems = stationInspectionItems;
        this.equipmentInspections = equipmentInspections;
    }

    @Override
    public List< StationEquipmentInspection > getStationEquipmentInspections() {
        return equipmentInspections;
    }


    @Override
    public Equipment getStation() {
        return substation;
    }

    @Override
    public List< InspectionItem > getStationInspectionItems() {
        return this.stationInspectionItems;
    }

    @Override
    public List< InspectionPhoto > getCommonPhotos() {
        return commonPhotos;
    }

    @Override
    public float getInspectionPercent() {
        int stationPercent = calcInspectionPercent();

        for (StationEquipmentInspection equipmentInspection : equipmentInspections) {
            stationPercent += equipmentInspection.calcInspectionPercent();
        }

        return stationPercent / (equipmentInspections.size() + 1);
    }
}
