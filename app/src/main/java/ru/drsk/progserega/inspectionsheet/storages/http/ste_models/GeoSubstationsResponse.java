package ru.drsk.progserega.inspectionsheet.storages.http.ste_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class GeoSubstationsResponse {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("description")
    @Expose
    private String description;


    @SerializedName("data")
    @Expose
    private Map<String, GeoSubstation> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, GeoSubstation> getData() {
        return data;
    }

    public void setData(Map<String, GeoSubstation> data) {
        this.data = data;
    }
}
