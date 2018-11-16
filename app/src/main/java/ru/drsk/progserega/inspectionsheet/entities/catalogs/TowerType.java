package ru.drsk.progserega.inspectionsheet.entities.catalogs;

public class TowerType {
    private int id;
    private String type;

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public TowerType(int id, String type) {
        this.id = id;
        this.type = type;
    }
}
