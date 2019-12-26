package ru.drsk.progserega.inspectionsheet.storages.sqlight.entities;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "section_deffect_types")
public class SectionDeffectTypesModel {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "order")
    private int order ;


    @ColumnInfo(name = "name")
    private String name;


    @ColumnInfo(name = "voltage")
    private String voltage;

    public SectionDeffectTypesModel(long id, int order, String name, String voltage) {
        this.id = id;
        this.order = order;
        this.name = name;
        this.voltage = voltage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }
}
