package ru.drsk.progserega.inspectionsheet.storages.sqlight.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "towers", primaryKeys = {"id","uniq_id"})
public class TowerModel {

    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "uniq_id")
    private long uniqId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "voltage")
    private int voltage;

    @ColumnInfo(name = "material")
    private int material;

    @ColumnInfo(name = "type")
    private int type;

    @ColumnInfo(name = "ele")
    private double ele;

    @ColumnInfo(name = "lat")
    private double lat;

    @ColumnInfo(name = "lon")
    private double lon;


    public TowerModel(long id, long uniqId, String name, int voltage, int material, int type, double ele, double lat, double lon) {
        this.id = id;
        this.uniqId = uniqId;
        this.name = name;
        this.voltage = voltage;
        this.material = material;
        this.type = type;
        this.ele = ele;
        this.lat = lat;
        this.lon = lon;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVoltage() {
        return voltage;
    }

    public void setVoltage(int voltage) {
        this.voltage = voltage;
    }

    public int getMaterial() {
        return material;
    }

    public void setMaterial(int material) {
        this.material = material;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getEle() {
        return ele;
    }

    public void setEle(double ele) {
        this.ele = ele;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
