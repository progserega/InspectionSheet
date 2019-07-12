package ru.drsk.progserega.inspectionsheet.entities.inspections;

public enum PhotoSubject {
    TRANSFORMER("transformer"),
    TOWER("tower"),
    SECTION("section");

    private String type;

    PhotoSubject(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
