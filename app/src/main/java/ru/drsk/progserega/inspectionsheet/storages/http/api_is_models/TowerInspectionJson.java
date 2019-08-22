package ru.drsk.progserega.inspectionsheet.storages.http.api_is_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TowerInspectionJson {

    @SerializedName("line_inspection_id")
    @Expose
    private long lineInspectionId;

    @SerializedName("tower_uniq_id")
    @Expose
    private long towerUniqId;

    @SerializedName("comment")
    @Expose
    private String comment;

    @SerializedName("inspection_date")
    @Expose
    private long inspectionDate;

    public TowerInspectionJson(long lineInspectionId, long towerUniqId, String comment, long inspectionDate) {
        this.lineInspectionId = lineInspectionId;
        this.towerUniqId = towerUniqId;
        this.comment = comment;
        this.inspectionDate = inspectionDate;
    }

    public long getLineInspectionId() {
        return lineInspectionId;
    }

    public void setLineInspectionId(long lineInspectionId) {
        this.lineInspectionId = lineInspectionId;
    }

    public long getTowerUniqId() {
        return towerUniqId;
    }

    public void setTowerUniqId(long towerUniqId) {
        this.towerUniqId = towerUniqId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(long inspectionDate) {
        this.inspectionDate = inspectionDate;
    }
}
