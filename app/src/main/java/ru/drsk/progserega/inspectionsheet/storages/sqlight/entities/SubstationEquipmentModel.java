package ru.drsk.progserega.inspectionsheet.storages.sqlight.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "substation_equipments")
public class SubstationEquipmentModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "substation_id")
    private long substationId;

    @ColumnInfo(name = "transformer_id")
    private long transformerId;

    @ColumnInfo(name = "slot")
    private int slot;

    @ColumnInfo(name = "manufacture_year")
    private int manufactureYear;


    public SubstationEquipmentModel(long id, long substationId, long transformerId, int slot, int manufactureYear) {
        this.id = id;
        this.substationId = substationId;
        this.transformerId = transformerId;
        this.slot = slot;
        this.manufactureYear = manufactureYear;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSubstationId() {
        return substationId;
    }

    public void setSubstationId(long substationId) {
        this.substationId = substationId;
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
}
