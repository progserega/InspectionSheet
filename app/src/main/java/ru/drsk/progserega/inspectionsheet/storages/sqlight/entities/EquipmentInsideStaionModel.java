package ru.drsk.progserega.inspectionsheet.storages.sqlight.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;

public class EquipmentInsideStaionModel {
    @Embedded
    StationEquipment equipment;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "descr")
    private String descr;

    public EquipmentInsideStaionModel(StationEquipment equipment, String name, String descr) {
        this.equipment = equipment;
        this.name = name;
        this.descr = descr;
    }

    public StationEquipment getEquipment() {
        return equipment;
    }

    public void setEquipment(StationEquipment equipment) {
        this.equipment = equipment;
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
