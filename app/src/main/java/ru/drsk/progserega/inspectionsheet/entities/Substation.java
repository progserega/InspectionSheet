package ru.drsk.progserega.inspectionsheet.entities;

public class Substation extends Equipment {


    public Substation(long id, String name ){
        this.id = id;
        this.name = name;
        this.type = EquipmentType.SUBSTATION;
    }
}
