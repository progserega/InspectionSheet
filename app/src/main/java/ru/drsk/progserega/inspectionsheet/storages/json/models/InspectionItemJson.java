package ru.drsk.progserega.inspectionsheet.storages.json.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItemType;

public class InspectionItemJson {

    @SerializedName("number")
    @Expose
    private String number;

    @SerializedName("name")
    @Expose
    private String name;


    @SerializedName("type")
    @Expose
    private InspectionItemType type;

    @SerializedName("result")
    @Expose
    private InspectionItemResultValues result;

    @SerializedName("sub_result")
    @Expose
    private InspectionItemResultValues subResult;


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

    public InspectionItemType getType() {
        return type;
    }

    public void setType(InspectionItemType type) {
        this.type = type;
    }

    public InspectionItemResultValues getResult() {
        return result;
    }

    public void setResult(InspectionItemResultValues result) {
        this.result = result;
    }

    public InspectionItemResultValues getSubResult() {
        return subResult;
    }

    public void setSubResult(InspectionItemResultValues subResult) {
        this.subResult = subResult;
    }
}
