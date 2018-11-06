package ru.drsk.progserega.inspectionsheet.entities;

public class Equipment {
    protected int id;
    protected String name;
    protected EquipmentType type;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public EquipmentType getType() {
        return type;
    }
}
