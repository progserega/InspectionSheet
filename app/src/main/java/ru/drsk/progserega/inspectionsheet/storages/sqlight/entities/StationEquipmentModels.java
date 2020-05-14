package ru.drsk.progserega.inspectionsheet.storages.sqlight.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "station_equipment_models")
public class StationEquipmentModels {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "uid")
    private long uniqId;


    @ColumnInfo(name = "equipment_type_id")
    private long equipmentTypeId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "descr")
    private String descr;


    public StationEquipmentModels(long id, long uniqId, long equipmentTypeId, String name, String descr) {
        this.id = id;
        this.uniqId = uniqId;
        this.equipmentTypeId = equipmentTypeId;
        this.name = name;
        this.descr = descr;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUniqId() {
        return uniqId;
    }

    public void setUniqId(long uniqId) {
        this.uniqId = uniqId;
    }

    public long getEquipmentTypeId() {
        return equipmentTypeId;
    }

    public void setEquipmentTypeId(long equipmentTypeId) {
        this.equipmentTypeId = equipmentTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }
}
