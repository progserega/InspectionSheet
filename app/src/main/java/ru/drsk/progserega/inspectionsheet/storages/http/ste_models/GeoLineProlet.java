package ru.drsk.progserega.inspectionsheet.storages.http.ste_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class GeoLineProlet {

    @SerializedName("name")
    @Expose
    private String name;


    @SerializedName("tower1")
    @Expose
    private GeoLineTowerNode tower1;

    @SerializedName("tower2")
    @Expose
    private GeoLineTowerNode tower2;

    public GeoLineProlet(String name, GeoLineTowerNode tower1, GeoLineTowerNode tower2) {
        this.name = name;
        this.tower1 = tower1;
        this.tower2 = tower2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GeoLineTowerNode getTower1() {
        return tower1;
    }

    public void setTower1(GeoLineTowerNode tower1) {
        this.tower1 = tower1;
    }

    public GeoLineTowerNode getTower2() {
        return tower2;
    }

    public void setTower2(GeoLineTowerNode tower2) {
        this.tower2 = tower2;
    }
}
