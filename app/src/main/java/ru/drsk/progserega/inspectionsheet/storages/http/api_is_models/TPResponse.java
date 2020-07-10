package ru.drsk.progserega.inspectionsheet.storages.http.api_is_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TPResponse {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("total")
    @Expose
    private int total;

    @SerializedName("data")
    @Expose
    private List< TPModel > data;

    public TPResponse(String status, int total, List< TPModel > data) {
        this.status = status;
        this.total = total;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List< TPModel > getData() {
        return data;
    }

    public void setData(List< TPModel > data) {
        this.data = data;
    }
}
