package ru.drsk.progserega.inspectionsheet.storages.json.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InspectionItemResultValues {

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("values")
    @Expose
    private List<String> possibleValues;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getPossibleValues() {
        return possibleValues;
    }

    public void setPossibleValues(List<String> possibleValues) {
        this.possibleValues = possibleValues;
    }
}
