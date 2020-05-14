package ru.drsk.progserega.inspectionsheet.storages.sqlight.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;

import java.util.Date;

public class EquipmentInsideStaionModel {
    @Embedded
    StationEquipmentModels equipmentModel;

    @ColumnInfo(name = "equipment_id")
    private long equipmentId;

    @ColumnInfo(name = "place")
    private String place;

    @ColumnInfo(name = "manufacture_year")
    private int manufactureYear;

    @ColumnInfo(name = "inspection_date")
    private Date inspectioDate;


    public EquipmentInsideStaionModel(StationEquipmentModels equipmentModel, long equipmentId, String place, int manufactureYear, Date inspectioDate) {
        this.equipmentModel = equipmentModel;
        this.equipmentId = equipmentId;
        this.place = place;
        this.manufactureYear = manufactureYear;
        this.inspectioDate = inspectioDate;
    }

    public StationEquipmentModels getEquipmentModel() {
        return equipmentModel;
    }

    public void setEquipmentModel(StationEquipmentModels equipmentModel) {
        this.equipmentModel = equipmentModel;
    }

    public long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(long equipmentId) {
        this.equipmentId = equipmentId;
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

    public Date getInspectioDate() {
        return inspectioDate;
    }

    public void setInspectioDate(Date inspectioDate) {
        this.inspectioDate = inspectioDate;
    }
}
