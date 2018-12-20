package ru.drsk.progserega.inspectionsheet.entities;

import java.util.Date;

public class TransformerSubstation extends Equipment {

    public TransformerSubstation(long id, String name, Date inspectionDate, float inspectionPercent) {
        this.id = id;
        this.name = name;
        this.type = EquipmentType.TRANS_SUBSTATION;
        this.inspectionDate = inspectionDate;
        this.inspectionPercent = inspectionPercent;
    }
}
