package ru.drsk.progserega.inspectionsheet.entities.catalogs;

@Deprecated
public class TowerDeffectType {

    private int typeId;
    private String name;

    public TowerDeffectType(int typeId, String name) {
        this.typeId = typeId;
        this.name = name;
    }

    public int getTypeId() {
        return typeId;
    }

    public String getName() {
        return name;
    }
}
