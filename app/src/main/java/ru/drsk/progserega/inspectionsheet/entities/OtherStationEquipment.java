package ru.drsk.progserega.inspectionsheet.entities;

public class OtherStationEquipment extends Equipment {

    public OtherStationEquipment(long id, long uniqId, String name, EquipmentType type) {
        super(id, uniqId, name, type);
    }

    @Override
    public Point getLocation() {
        return null;
    }
}
