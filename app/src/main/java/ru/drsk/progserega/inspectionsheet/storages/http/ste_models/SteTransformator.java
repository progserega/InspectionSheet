package ru.drsk.progserega.inspectionsheet.storages.http.ste_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SteTransformator {
    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("tr_type")
    @Expose
    private String trType;

    @SerializedName("desc")
    @Expose
    private String desc;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTrType() {
        return trType;
    }

    public void setTrType(String trType) {
        this.trType = trType;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
