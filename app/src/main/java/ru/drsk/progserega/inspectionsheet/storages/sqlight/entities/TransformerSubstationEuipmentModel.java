package ru.drsk.progserega.inspectionsheet.storages.sqlight.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

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

    @ColumnInfo(name = "manufacture_year")
    private int manufactureYear;

    @ColumnInfo(name = "inspection_date")
    private Date inspectionDate;

    public TransformerSubstationEuipmentModel(long id, long tpId, long transformerId, int slot, int manufactureYear, Date inspectionDate) {
        this.id = id;
        this.tpId = tpId;
        this.transformerId = transformerId;
        this.slot = slot;
        this.manufactureYear = manufactureYear;
        this.inspectionDate = inspectionDate;
    }

//    public TransformerSubstationEuipmentModel(long id, long tpId, long transformerId, int slot, int manufactureYear) {
//        this.id = id;
//        this.tpId = tpId;
//        this.transformerId = transformerId;
//        this.slot = slot;
//        this.manufactureYear = manufactureYear;
//    }

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

    public int getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(int manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

    public Date getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(Date inspectionDate) {
        this.inspectionDate = inspectionDate;
    }
}
