package ru.drsk.progserega.inspectionsheet.storages.sqlight.entities;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "station_equipments_deffect_types")
public class StationEquipmentsDeffectType {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "order")
    private int order ;

    @ColumnInfo(name = "number")
    private String number;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "type")
    private int type;

    @ColumnInfo(name = "result")
    private String result;

    @ColumnInfo(name = "sub_result")
    private String subResult;

    @ColumnInfo(name = "equipment_type")
    private long equipmentType;

    public StationEquipmentsDeffectType(long id, int order, String number, String name, int type, String result, String subResult, long equipmentType) {
        this.id = id;
        this.order = order;
        this.number = number;
        this.name = name;
        this.type = type;
        this.result = result;
        this.subResult = subResult;
        this.equipmentType = equipmentType;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSubResult() {
        return subResult;
    }

    public void setSubResult(String subResult) {
        this.subResult = subResult;
    }

    public long getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(long equipmentType) {
        this.equipmentType = equipmentType;
    }
}
