package ru.drsk.progserega.inspectionsheet.storages.http.api_is_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StationInspectionJson {

    @SerializedName("inspection_id")
    @Expose
    private long inspectionId;


    @SerializedName("station_uniq_id")
    @Expose
    private long stationUniqId;

    @SerializedName("station_type")
    @Expose
    private int stationType;

    @SerializedName("inspector_name")
    @Expose
    private String inspectorName;

    @SerializedName("inspection_type")
    @Expose
    private int inspectionType;

    @SerializedName("inspection_date")
    @Expose
    private long inspectionDate;

    @SerializedName("inspection_percent")
    @Expose
    private float inspectionPercent;

    @SerializedName("slot")
    @Expose
    private int slot;


    public StationInspectionJson(long inspectionId, long stationUniqId, int stationType, String inspectorName, int inspectionType, long inspectionDate, float inspectionPercent, int slot) {
        this.inspectionId = inspectionId;
        this.stationUniqId = stationUniqId;
        this.stationType = stationType;
        this.inspectorName = inspectorName;
        this.inspectionType = inspectionType;
        this.inspectionDate = inspectionDate;
        this.inspectionPercent = inspectionPercent;
        this.slot = slot;
    }

    public long getStationUniqId() {
        return stationUniqId;
    }

    public void setStationUniqId(long stationUniqId) {
        this.stationUniqId = stationUniqId;
    }

    public int getStationType() {
        return stationType;
    }

    public void setStationType(int stationType) {
        this.stationType = stationType;
    }

    public String getInspectorName() {
        return inspectorName;
    }

    public void setInspectorName(String inspectorName) {
        this.inspectorName = inspectorName;
    }

    public int getInspectionType() {
        return inspectionType;
    }

    public void setInspectionType(int inspectionType) {
        this.inspectionType = inspectionType;
    }

    public long getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(long inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public float getInspectionPercent() {
        return inspectionPercent;
    }

    public void setInspectionPercent(float inspectionPercent) {
        this.inspectionPercent = inspectionPercent;
    }

    public long getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(long inspectionId) {
        this.inspectionId = inspectionId;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }
}
