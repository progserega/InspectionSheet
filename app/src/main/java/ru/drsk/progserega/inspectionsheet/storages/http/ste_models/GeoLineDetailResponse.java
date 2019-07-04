package ru.drsk.progserega.inspectionsheet.storages.http.ste_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeoLineDetailResponse {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("description")
    @Expose
    private String description;


    @SerializedName("data")
    @Expose
    private GeoLineDetail data;

    public GeoLineDetailResponse(String status, String description, GeoLineDetail data) {
        this.status = status;
        this.description = description;
        this.data = data;
    }

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

    public GeoLineDetail getData() {
        return data;
    }

    public void setData(GeoLineDetail data) {
        this.data = data;
    }
}
