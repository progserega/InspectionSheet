package ru.drsk.progserega.inspectionsheet.storages.http.api_is_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.SteTPModel;

public class SubstationsResponse {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("total")
    @Expose
    private int total;

    @SerializedName("data")
    @Expose
    private List< SubstationModel > data;

    public SubstationsResponse(String status, int total, List< SubstationModel > data) {
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

    public List< SubstationModel > getData() {
        return data;
    }

    public void setData(List< SubstationModel > data) {
        this.data = data;
    }
}
