package ru.drsk.progserega.inspectionsheet.entities;

public class TransformerSubstation extends Equipment {

    public TransformerSubstation(long id, String name) {
        this.id = id;
        this.name = name;
        this.type = EquipmentType.TRANS_SUBSTATION;
    }
}
