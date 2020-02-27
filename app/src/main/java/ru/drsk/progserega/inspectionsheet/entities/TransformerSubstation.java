package ru.drsk.progserega.inspectionsheet.entities;

import java.util.Date;

public class TransformerSubstation extends Equipment {

    public TransformerSubstation(long id, long uniqId, String name, Date inspectionDate, float inspectionPercent) {
        this.id = id;
        this.uniqId = uniqId;
        this.name = name;
        this.type = EquipmentType.TRANS_SUBSTATION;
        this.inspectionDate = inspectionDate;
        this.inspectionPercent = inspectionPercent;
    }

    @Override
    public Point getLocation() {
        return null;
    }
}
