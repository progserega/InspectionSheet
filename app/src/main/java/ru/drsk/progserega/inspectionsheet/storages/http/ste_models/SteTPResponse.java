package ru.drsk.progserega.inspectionsheet.storages.http.ste_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SteTPResponse {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("all_count_records")
    @Expose
    private int totalRecords;


    @SerializedName("data")
    @Expose
    private List<SteTPModel> data;


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

    public List<SteTPModel> getData() {
        return data;
    }

    public void setData(List<SteTPModel> data) {
        this.data = data;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }
}
