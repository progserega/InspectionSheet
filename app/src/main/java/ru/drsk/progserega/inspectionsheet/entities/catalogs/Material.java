package ru.drsk.progserega.inspectionsheet.entities.catalogs;

public class Material  {

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Material(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
