package ru.drsk.progserega.inspectionsheet.storages.sqlight.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;


@Deprecated
/**
 * Модель таблици описывает Трансформаторную подстанцию
 */
@Entity(tableName = "tp")
public class TransformerSubstationModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "uniq_id")
    private long uniqId;

    @ColumnInfo(name = "power_center_name")
    private String powerCenterName;

    @ColumnInfo(name = "disp_name_name")
    private String dispName;

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


    public TransformerSubstationModel(long id, long uniqId, String powerCenterName, String dispName, long spId, long resId, double lat, double lon) {
        this.id = id;
        this.uniqId = uniqId;
        this.powerCenterName = powerCenterName;
        this.dispName = dispName;
        this.spId = spId;
        this.resId = resId;
        this.lat = lat;
        this.lon = lon;
        this.inspectionDate = new Date(0);
        this.inspectionPercent = 0;
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

    public String getPowerCenterName() {
        return powerCenterName;
    }

    public void setPowerCenterName(String powerCenterName) {
        this.powerCenterName = powerCenterName;
    }

    public String getDispName() {
        return dispName;
    }

    public void setDispName(String dispName) {
        this.dispName = dispName;
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