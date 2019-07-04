package ru.drsk.progserega.inspectionsheet.storages.http.ste_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeoLineDetail {
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("voltage")
    @Expose
    private int voltage;

    @SerializedName("ways")
    @Expose
    private GeoLineTowers ways;

    public GeoLineDetail(String name, int voltage, GeoLineTowers ways) {
        this.name = name;
        this.voltage = voltage;
        this.ways = ways;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVoltage() {
        return voltage;
    }

    public void setVoltage(int voltage) {
        this.voltage = voltage;
    }

    public GeoLineTowers getWays() {
        return ways;
    }

    public void setWays(GeoLineTowers ways) {
        this.ways = ways;
    }
}
