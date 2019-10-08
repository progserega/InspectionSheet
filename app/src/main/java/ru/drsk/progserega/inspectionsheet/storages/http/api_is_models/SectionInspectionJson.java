package ru.drsk.progserega.inspectionsheet.storages.http.api_is_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SectionInspectionJson {

    @SerializedName("line_inspection_id")
    @Expose
    private long lineInspectionId;

    @SerializedName("line_uniq_id")
    @Expose
    private long lineUniqId;

    @SerializedName("from_tower_uniq_id")
    @Expose
    private long fromTowerUniqId;

    @SerializedName("to_tower_uniq_id")
    @Expose
    private long toTowerUniqId;

    @SerializedName("comment")
    @Expose
    private String comment;

    @SerializedName("inspection_date")
    @Expose
    private long inspectionDate;


    public SectionInspectionJson(long lineInspectionId, long lineUniqId, long fromTowerUniqId, long toTowerUniqId, String comment, long inspectionDate) {
        this.lineInspectionId = lineInspectionId;
        this.lineUniqId = lineUniqId;
        this.fromTowerUniqId = fromTowerUniqId;
        this.toTowerUniqId = toTowerUniqId;
        this.comment = comment;
        this.inspectionDate = inspectionDate;
    }

    public long getLineInspectionId() {
        return lineInspectionId;
    }

    public void setLineInspectionId(long lineInspectionId) {
        this.lineInspectionId = lineInspectionId;
    }

    public long getLineUniqId() {
        return lineUniqId;
    }

    public void setLineUniqId(long lineUniqId) {
        this.lineUniqId = lineUniqId;
    }

    public long getFromTowerUniqId() {
        return fromTowerUniqId;
    }

    public void setFromTowerUniqId(long fromTowerUniqId) {
        this.fromTowerUniqId = fromTowerUniqId;
    }

    public long getToTowerUniqId() {
        return toTowerUniqId;
    }

    public void setToTowerUniqId(long toTowerUniqId) {
        this.toTowerUniqId = toTowerUniqId;
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
