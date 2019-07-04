package ru.drsk.progserega.inspectionsheet.storages.json.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LineDeffectTypeJson {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("order_number")
    @Expose
    private int order;

    @SerializedName("name")
    @Expose
    private String name;

    public LineDeffectTypeJson(int id, int order, String name) {
        this.id = id;
        this.order = order;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
