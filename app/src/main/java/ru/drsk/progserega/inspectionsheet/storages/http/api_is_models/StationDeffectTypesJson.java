package ru.drsk.progserega.inspectionsheet.storages.http.api_is_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StationDeffectTypesJson {

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("order")
    @Expose
    private int order;

    @SerializedName("number")
    @Expose
    private String number;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("type")
    @Expose
    private int type;

    @SerializedName("result")
    @Expose
    private String result;

    @SerializedName("sub_result")
    @Expose
    private String subResult;

    @SerializedName("equipment_type")
    @Expose
    private String equipmentType;

    public StationDeffectTypesJson(long id, int order, String number, String name, int type, String result, String subResult, String equipmentType) {
        this.id = id;
        this.order = order;
        this.number = number;
        this.name = name;
        this.type = type;
        this.result = result;
        this.subResult = subResult;
        this.equipmentType = equipmentType;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSubResult() {
        return subResult;
    }

    public void setSubResult(String subResult) {
        this.subResult = subResult;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }
}
