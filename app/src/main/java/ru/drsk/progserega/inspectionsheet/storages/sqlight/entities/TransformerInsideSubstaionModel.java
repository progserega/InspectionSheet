package ru.drsk.progserega.inspectionsheet.storages.sqlight.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;

import java.util.Date;

public class TransformerInsideSubstaionModel {
    @Embedded
    TransformerModel transformer;

    @ColumnInfo(name = "equipment_id")
    private long equipmentId;

    @ColumnInfo(name = "slot")
    private int slot;

    @ColumnInfo(name = "manufacture_year")
    private int manufactureYear;

    @ColumnInfo(name = "inspection_date")
    private Date inspectioDate;


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

    public Date getInspectioDate() {
        return inspectioDate;
    }

    public void setInspectioDate(Date inspectioDate) {
        this.inspectioDate = inspectioDate;
    }
}
