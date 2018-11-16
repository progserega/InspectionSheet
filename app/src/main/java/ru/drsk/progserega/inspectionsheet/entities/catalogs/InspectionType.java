package ru.drsk.progserega.inspectionsheet.entities.catalogs;

public class InspectionType {
    private int id;
    private String inspection;

    public int getId() {
        return id;
    }

    public String getInspection() {
        return inspection;
    }

    public InspectionType(int id, String inspection) {
        this.id = id;
        this.inspection = inspection;
    }
}
