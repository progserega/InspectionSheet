package ru.drsk.progserega.inspectionsheet.storages.sqlight.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "inspections")
public class InspectionModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "substation_id")
    private long substationId;

    @ColumnInfo(name = "substation_type")
    private int substationType;

    @ColumnInfo(name = "equipment_id")
    private long equipmentId;

    @ColumnInfo(name = "deffect_id")
    private long deffectId;

    @ColumnInfo(name = "deffect_value")
    private String deffectValue;

    public InspectionModel(long id, long substationId, int substationType, long equipmentId, long deffectId, String deffectValue) {
        this.id = id;
        this.substationId = substationId;
        this.substationType = substationType;
        this.equipmentId = equipmentId;
        this.deffectId = deffectId;
        this.deffectValue = deffectValue;
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

    public int getSubstationType() {
        return substationType;
    }

    public void setSubstationType(int substationType) {
        this.substationType = substationType;
    }

    public long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(long equipmentId) {
        this.equipmentId = equipmentId;
    }

    public long getDeffectId() {
        return deffectId;
    }

    public void setDeffectId(long deffectId) {
        this.deffectId = deffectId;
    }

    public String getDeffectValue() {
        return deffectValue;
    }

    public void setDeffectValue(String deffectValue) {
        this.deffectValue = deffectValue;
    }
}
