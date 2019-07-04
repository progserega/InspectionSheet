package ru.drsk.progserega.inspectionsheet.storages.http.ste_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class GeoLinesResponse {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("description")
    @Expose
    private String description;


    @SerializedName("data")
    @Expose
    private List<GeoLine> data;

    public GeoLinesResponse(String status, String description, List<GeoLine> data) {

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

    public List<GeoLine> getData() {
        return data;
    }

    public void setData(List<GeoLine> data) {
        this.data = data;
    }
}
