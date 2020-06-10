package ru.drsk.progserega.inspectionsheet.storages.http.api_is_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadStationImageInfo {
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("station_uid")
    @Expose
    private long stationUid;

    @SerializedName("station_type")
    @Expose
    private int stationType;

    @SerializedName("upload_date")
    @Expose
    private long uploadDate;

    @SerializedName("inspection_id")
    @Expose
    private long inspectionId;


    public UploadStationImageInfo(String name, long stationUid, int stationType, long uploadDate, long inspectionId) {
        this.name = name;
        this.stationUid = stationUid;
        this.stationType = stationType;
        this.uploadDate = uploadDate;
        this.inspectionId = inspectionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getStationUid() {
        return stationUid;
    }

    public void setStationUid(long stationUid) {
        this.stationUid = stationUid;
    }

    public int getStationType() {
        return stationType;
    }

    public void setStationType(int stationType) {
        this.stationType = stationType;
    }

    public long getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(long uploadDate) {
        this.uploadDate = uploadDate;
    }

    public long getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(long inspectionId) {
        this.inspectionId = inspectionId;
    }
}
