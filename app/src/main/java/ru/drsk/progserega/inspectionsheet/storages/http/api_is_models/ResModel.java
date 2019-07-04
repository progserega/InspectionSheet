package ru.drsk.progserega.inspectionsheet.storages.http.api_is_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResModel {
    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("sp_id")
    @Expose
    private long spId;


    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("alter_name")
    @Expose
    private String alterName;

    public ResModel(long id, long spId, String name, String alterName) {
        this.id = id;
        this.spId = spId;
        this.name = name;
        this.alterName = alterName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSpId() {
        return spId;
    }

    public void setSpId(long spId) {
        this.spId = spId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlterName() {
        return alterName;
    }

    public void setAlterName(String alterName) {
        this.alterName = alterName;
    }
}
