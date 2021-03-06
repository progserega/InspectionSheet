package ru.drsk.progserega.inspectionsheet.storages.sqlight.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Deprecated
@Entity(tableName = "substations")
public class SubstationModel {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "uniq_id")
    private long uniqId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "voltage")
    private String voltage;

    @ColumnInfo(name = "object_type")
    private String objectType;

    @ColumnInfo(name = "sp_id")
    private long spId;

    @ColumnInfo(name = "res_id")
    private long resId;

    @ColumnInfo(name = "location_lat")
    private double lat;

    @ColumnInfo(name = "location_lon")
    private double lon;

    @ColumnInfo(name = "inspection_date")
    private Date inspectionDate;

    @ColumnInfo(name = "inspection_percent")
    private float inspectionPercent;

    public SubstationModel(long id, long uniqId, String name, String voltage, String objectType, long spId, long resId, double lat, double lon, Date inspectionDate, float inspectionPercent) {
        this.id = id;
        this.uniqId = uniqId;
        this.name = name;
        this.voltage = voltage;
        this.objectType = objectType;
        this.spId = spId;
        this.resId = resId;
        this.lat = lat;
        this.lon = lon;
        this.inspectionDate = inspectionDate;
        this.inspectionPercent = inspectionPercent;
    }

    //    public SubstationModel(long id, String name, String voltage, String objectType, long spId, long resId, double lat, double lon) {
//        this.id = id;
//        this.name = name;
//        this.voltage = voltage;
//        this.objectType = objectType;
//        this.spId = spId;
//        this.resId = resId;
//        this.lat = lat;
//        this.lon = lon;
//
//        this.inspectionDate = new Date(0);
//        this.inspectionPercent = 0;
//    }

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

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public long getSpId() {
        return spId;
    }

    public void setSpId(long spId) {
        this.spId = spId;
    }

    public long getResId() {
        return resId;
    }

    public void setResId(long resId) {
        this.resId = resId;
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

    public Date getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(Date inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public float getInspectionPercent() {
        return inspectionPercent;
    }

    public void setInspectionPercent(float inspectionPercent) {
        this.inspectionPercent = inspectionPercent;
    }
}
