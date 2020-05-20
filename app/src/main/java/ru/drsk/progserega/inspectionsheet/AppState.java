package ru.drsk.progserega.inspectionsheet;

import ru.drsk.progserega.inspectionsheet.entities.inspections.StationEquipmentInspection;

public class AppState {
    private StationEquipmentInspection currentStaionEquipmentInspection;

    public StationEquipmentInspection getCurrentStaionEquipmentInspection() {
        return currentStaionEquipmentInspection;
    }

    public void setCurrentStaionEquipmentInspection(StationEquipmentInspection currentStaionEquipmentInspection) {
        this.currentStaionEquipmentInspection = currentStaionEquipmentInspection;
    }
}
