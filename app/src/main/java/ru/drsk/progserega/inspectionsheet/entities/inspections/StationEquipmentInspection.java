package ru.drsk.progserega.inspectionsheet.entities.inspections;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Equipment;

public class StationEquipmentInspection<T extends Equipment> {

    private Equipment station;
    private T equipment;
    private List<InspectionItem> inspectionItems;
    private Date date;
    private boolean done;
    private String inspectorName = "";

    public Equipment getStation() {
        return station;
    }

    public T getEquipment() {
        return equipment;
    }

    public List<InspectionItem> getInspectionItems() {
        return inspectionItems;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public StationEquipmentInspection(Equipment substation, T equipment) {

        this.station = substation;
        this.equipment = equipment;
        inspectionItems = new ArrayList<>();
        done = false;
    }

    public void setInspectionItems(List<InspectionItem> inspectionItems) {
        this.inspectionItems = inspectionItems;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public int calcInspectionPercent() {

        int total = 0;
        int inspected = 0;
        for (InspectionItem inspectionItem : inspectionItems) {
            if (inspectionItem.getType().equals(InspectionItemType.HEADER)) {
                continue;
            }
            if (inspectionItem.getName().equals("Прочее")) {
                continue;
            }
            total++;

            if (inspectionItem.getResult() == null) {
                continue;
            }
            InspectionItemResult inspectionResult = inspectionItem.getResult();
            if (!inspectionResult.getComment().isEmpty() || !inspectionResult.getValues().isEmpty() || !inspectionResult.getSubValues().isEmpty()) {
                inspected++;
            }
        }

        int percent = (int) (inspected / (float) total * 100);
        return percent;
    }

    public String getInspectorName() {
        return inspectorName;
    }

    public void setInspectorName(String inspectorName) {
        this.inspectorName = inspectorName;
    }
}
