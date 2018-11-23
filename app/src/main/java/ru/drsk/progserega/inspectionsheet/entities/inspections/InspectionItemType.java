package ru.drsk.progserega.inspectionsheet.entities.inspections;

public enum InspectionItemType {
    HEADER(0), ITEM(1);

    private final int value;
    private InspectionItemType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
