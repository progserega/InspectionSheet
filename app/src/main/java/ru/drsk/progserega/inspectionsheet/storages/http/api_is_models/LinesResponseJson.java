package ru.drsk.progserega.inspectionsheet.storages.http.api_is_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LinesResponseJson {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("total")
    @Expose
    private long total;

    @SerializedName("data")
    @Expose
    private List<LineData> data;

    public LinesResponseJson(String status, long total, List< LineData > data) {
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

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List< LineData > getData() {
        return data;
    }

    public void setData(List< LineData > data) {
        this.data = data;
    }
}
