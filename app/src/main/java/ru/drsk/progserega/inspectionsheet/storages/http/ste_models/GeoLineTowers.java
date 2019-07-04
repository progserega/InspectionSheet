package ru.drsk.progserega.inspectionsheet.storages.http.ste_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class GeoLineTowers {

    @SerializedName("towers")
    @Expose
    private Map<Long, GeoLineTower> towers;

    public GeoLineTowers(Map<Long, GeoLineTower> towers) {
        this.towers = towers;
    }

    public Map<Long, GeoLineTower> getTowers() {
        return towers;
    }

    public void setTowers(Map<Long, GeoLineTower> towers) {
        this.towers = towers;
    }
}
