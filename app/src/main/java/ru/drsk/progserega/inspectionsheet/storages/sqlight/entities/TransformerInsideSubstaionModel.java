package ru.drsk.progserega.inspectionsheet.storages.sqlight.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;

public class TransformerInsideSubstaionModel {
    @Embedded
    TransformerModel transformer;

    @ColumnInfo(name = "equipment_id")
    private long equipmentId;

    public TransformerModel getTransformer() {
        return transformer;
    }

    public void setTransformer(TransformerModel transformer) {
        this.transformer = transformer;
    }

    public long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(long equipmentId) {
        this.equipmentId = equipmentId;
    }
}
