package ru.drsk.progserega.inspectionsheet.storages.http.api_is_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadTransformerInfo {
    @SerializedName("substation_id")
    @Expose
    private long substationId;

    @SerializedName("substation_type")
    @Expose
    private int substationType;

    @SerializedName("equipment_id")
    @Expose
    private long equipmentId;

    @SerializedName("manufacture_year")
    @Expose
    private int  manufactureYear;

    @SerializedName("inspection_precent")
    @Expose
    private int inspectionPercent;

    @SerializedName("inspection_date")
    @Expose
    private long inspectionDate;

    public UploadTransformerInfo(long substationId, int substationType, long equipmentId, int manufactureYear, int inspectionPercent, long inspectionDate) {
        this.substationId = substationId;
        this.substationType = substationType;
        this.equipmentId = equipmentId;
        this.manufactureYear = manufactureYear;
        this.inspectionPercent = inspectionPercent;
        this.inspectionDate = inspectionDate;
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

    public int getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(int manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

    public int getInspectionPercent() {
        return inspectionPercent;
    }

    public void setInspectionPercent(int inspectionPercent) {
        this.inspectionPercent = inspectionPercent;
    }

    public long getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(long inspectionDate) {
        this.inspectionDate = inspectionDate;
    }
}

