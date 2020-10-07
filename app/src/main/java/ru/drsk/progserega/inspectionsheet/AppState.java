package ru.drsk.progserega.inspectionsheet;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.inspections.DeffectDescription;
import ru.drsk.progserega.inspectionsheet.entities.inspections.IStationInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItemResult;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.StationEquipmentInspection;

public class AppState {
    private StationEquipmentInspection currentStaionEquipmentInspection;
    private InspectionItem currentInspectionItem;
    private List< InspectionItem > inspectionItemsGroup;
    private InspectionItemResult currentDeffect;
    private DeffectDescription deffectDescription;
    private IStationInspection currentStationInspection;
    private LineInspection currentLineInspection;
    private List<IStationInspection> substationInspectionsCache = new ArrayList<>();

    public StationEquipmentInspection getCurrentStaionEquipmentInspection() {
        return currentStaionEquipmentInspection;
    }

    public void setCurrentStaionEquipmentInspection(StationEquipmentInspection currentStaionEquipmentInspection) {
        this.currentStaionEquipmentInspection = currentStaionEquipmentInspection;
    }

    public InspectionItem getCurrentInspectionItem() {
        return currentInspectionItem;
    }

    public void setCurrentInspectionItem(InspectionItem currentInspectionItem) {
        this.currentInspectionItem = currentInspectionItem;
    }

    public List<InspectionItem> getInspectionItemsGroup() {
        return inspectionItemsGroup;
    }

    public void setInspectionItemsGroup(List<InspectionItem> inspectionItemsGroup) {
        this.inspectionItemsGroup = inspectionItemsGroup;
    }

    public InspectionItemResult getCurrentDeffect() {
        return currentDeffect;
    }

    public void setCurrentDeffect(InspectionItemResult currentDeffect) {
        this.currentDeffect = currentDeffect;
    }

    public DeffectDescription getDeffectDescription() {
        return deffectDescription;
    }

    public void setDeffectDescription(DeffectDescription deffectDescription) {
        this.deffectDescription = deffectDescription;
    }

    public IStationInspection getCurrentStationInspection() {
        return currentStationInspection;
    }

    public void setCurrentStationInspection(IStationInspection currentStationInspection) {
        this.currentStationInspection = currentStationInspection;
    }

    public LineInspection getCurrentLineInspection() {
        return currentLineInspection;
    }

    public void setCurrentLineInspection(LineInspection currentLineInspection) {
        this.currentLineInspection = currentLineInspection;
    }

    public List< IStationInspection > getSubstationInspectionsCache() {
        return substationInspectionsCache;
    }


    public void clear(){
        currentInspectionItem = null;
        currentStaionEquipmentInspection = null;
        inspectionItemsGroup = null;
        currentDeffect = null;
        deffectDescription = null;
        currentStationInspection = null;
        currentLineInspection = null;
        substationInspectionsCache = new ArrayList<>();
    }
}
