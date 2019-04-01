package ru.drsk.progserega.inspectionsheet.storages.http.api_is_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransformerInspectionResult {
    @SerializedName("substation_id")
    @Expose
    private long substationId;

    @SerializedName("substation_type")
    @Expose
    private int substationType;

    @SerializedName("equipment_id")
    @Expose
    private long equipmentId;

    @SerializedName("deffect_id")
    @Expose
    private int deffectId;

    @SerializedName("deffect_values")
    @Expose
    private String deffectValues;

    @SerializedName("deffect_sub_values")
    @Expose
    private String deffectSubValues;

    @SerializedName("deffect_comment")
    @Expose
    private String deffectComment;

    @SerializedName("upload_date")
    @Expose
    private long uploadDate;

    public TransformerInspectionResult(long substationId, int substationType, long equipmentId, int deffectId, String deffectValues, String deffectSubValues, String deffectComment, long uploadDate) {
        this.substationId = substationId;
        this.substationType = substationType;
        this.equipmentId = equipmentId;
        this.deffectId = deffectId;
        this.deffectValues = deffectValues;
        this.deffectSubValues = deffectSubValues;
        this.deffectComment = deffectComment;
        this.uploadDate = uploadDate;
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

    public int getDeffectId() {
        return deffectId;
    }

    public void setDeffectId(int deffectId) {
        this.deffectId = deffectId;
    }

    public String getDeffectValues() {
        return deffectValues;
    }

    public void setDeffectValues(String deffectValues) {
        this.deffectValues = deffectValues;
    }

    public String getDeffectSubValues() {
        return deffectSubValues;
    }

    public void setDeffectSubValues(String deffectSubValues) {
        this.deffectSubValues = deffectSubValues;
    }

    public String getDeffectComment() {
        return deffectComment;
    }

    public void setDeffectComment(String deffectComment) {
        this.deffectComment = deffectComment;
    }

    public long getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(long uploadDate) {
        this.uploadDate = uploadDate;
    }
}
