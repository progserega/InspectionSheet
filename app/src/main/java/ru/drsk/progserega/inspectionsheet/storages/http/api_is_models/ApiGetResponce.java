package ru.drsk.progserega.inspectionsheet.storages.http.api_is_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiGetResponce<T> {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("total")
    @Expose
    private long total;

    @SerializedName("data")
    @Expose
    private List<T> data;

    public ApiGetResponce(String status, long total, List< T > data) {
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

    public List< T > getData() {
        return data;
    }

    public void setData(List< T > data) {
        this.data = data;
    }
}
