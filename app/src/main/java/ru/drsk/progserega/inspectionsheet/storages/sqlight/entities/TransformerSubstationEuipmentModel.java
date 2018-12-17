package ru.drsk.progserega.inspectionsheet.storages.sqlight.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "tp_transformers")
public class TransformerSubstationEuipmentModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "tp_id")
    private long tpId;

    @ColumnInfo(name = "transformer_id")
    private long transformerId;

    @ColumnInfo(name = "slot")
    private int slot;

    public TransformerSubstationEuipmentModel(long id, long tpId, long transformerId, int slot) {
        this.id = id;
        this.tpId = tpId;
        this.transformerId = transformerId;
        this.slot = slot;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTpId() {
        return tpId;
    }

    public void setTpId(long tpId) {
        this.tpId = tpId;
    }

    public long getTransformerId() {
        return transformerId;
    }

    public void setTransformerId(long transformerId) {
        this.transformerId = transformerId;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }
}
