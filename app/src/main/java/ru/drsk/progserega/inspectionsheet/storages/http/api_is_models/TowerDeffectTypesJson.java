package ru.drsk.progserega.inspectionsheet.storages.http.api_is_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TowerDeffectTypesJson {

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("order")
    @Expose
    private int order;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("voltage")
    @Expose
    private String voltage;

    public TowerDeffectTypesJson(long id, int order, String name, String voltage) {
        this.id = id;
        this.order = order;
        this.name = name;
        this.voltage = voltage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }
}
