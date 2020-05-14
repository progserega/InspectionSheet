package ru.drsk.progserega.inspectionsheet.storages.sqlight.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "station_equipments")
public class StationEquipment {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "uid")
    private long uniqId;

    @ColumnInfo(name = "station_id")
    private long substationId;

    @ColumnInfo(name = "type_id")
    private long typeId;

    @ColumnInfo(name = "model_id")
    private long modelId;

    @ColumnInfo(name = "place")
    private String place;

    @ColumnInfo(name = "manufacture_year")
    private int manufactureYear;

    @ColumnInfo(name = "inspection_date")
    private Date inspectionDate;

    public StationEquipment(long id, long uniqId, long substationId, long typeId, long modelId, String place, int manufactureYear, Date inspectionDate) {
        this.id = id;
        this.uniqId = uniqId;
        this.substationId = substationId;
        this.typeId = typeId;
        this.modelId = modelId;
        this.place = place;
        this.manufactureYear = manufactureYear;
        this.inspectionDate = inspectionDate;
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

    public long getSubstationId() {
        return substationId;
    }

    public void setSubstationId(long substationId) {
        this.substationId = substationId;
    }

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public long getModelId() {
        return modelId;
    }

    public void setModelId(long modelId) {
        this.modelId = modelId;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(int manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

    public Date getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(Date inspectionDate) {
        this.inspectionDate = inspectionDate;
    }
}
