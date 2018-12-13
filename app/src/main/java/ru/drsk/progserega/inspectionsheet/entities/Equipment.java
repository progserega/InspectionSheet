package ru.drsk.progserega.inspectionsheet.entities;

public class Equipment {
    protected long id;
    protected String name;
    protected EquipmentType type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public EquipmentType getType() {
        return type;
    }
}
