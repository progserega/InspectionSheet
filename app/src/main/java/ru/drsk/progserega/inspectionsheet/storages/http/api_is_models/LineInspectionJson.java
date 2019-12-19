package ru.drsk.progserega.inspectionsheet.storages.http.api_is_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LineInspectionJson {

    @SerializedName("line_uniq_id")
    @Expose
    private long lineUniqId;

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

    @SerializedName("res_id")
    @Expose
    private long resId;


    public LineInspectionJson(long lineUniqId, String inspectorName, int inspectionType, long inspectionDate, float inspectionPercent, long resId) {
        this.lineUniqId = lineUniqId;
        this.inspectorName = inspectorName;
        this.inspectionType = inspectionType;
        this.inspectionDate = inspectionDate;
        this.inspectionPercent = inspectionPercent;
        this.resId = resId;
    }

    public long getLineUniqId() {
        return lineUniqId;
    }

    public void setLineUniqId(long lineUniqId) {
        this.lineUniqId = lineUniqId;
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

    public long getResId() {
        return resId;
    }

    public void setResId(long resId) {
        this.resId = resId;
    }
}
