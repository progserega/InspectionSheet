package ru.drsk.progserega.inspectionsheet.storages.http.api_is_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadTransformerImageInfo {
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("transformer_id")
    @Expose
    private long transformer_id;

    @SerializedName("sybstation_type")
    @Expose
    private int sybstation_type;

    @SerializedName("upload_date")
    @Expose
    private long uploadDate;

    public UploadTransformerImageInfo(String name, long transformer_id, int sybstation_type, long uploadDate) {
        this.name = name;
        this.transformer_id = transformer_id;
        this.sybstation_type = sybstation_type;
        this.uploadDate = uploadDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTransformer_id() {
        return transformer_id;
    }

    public void setTransformer_id(long transformer_id) {
        this.transformer_id = transformer_id;
    }

    public int getSybstation_type() {
        return sybstation_type;
    }

    public void setSybstation_type(int sybstation_type) {
        this.sybstation_type = sybstation_type;
    }

    public long getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(long uploadDate) {
        this.uploadDate = uploadDate;
    }
}
