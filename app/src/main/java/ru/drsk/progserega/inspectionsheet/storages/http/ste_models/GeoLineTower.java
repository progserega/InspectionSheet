package ru.drsk.progserega.inspectionsheet.storages.http.ste_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class GeoLineTower {

    @SerializedName("node")
    @Expose
    private GeoLineTowerNode node;

    @SerializedName("prolet")
    @Expose
    private GeoLineProlet prolet;


    @SerializedName("otpaiki")
    @Expose
    private Map<Long,GeoLineTowers> otpaiki;

    public GeoLineTower(GeoLineTowerNode node, GeoLineProlet prolet, Map<Long, GeoLineTowers> otpaiki) {
        this.node = node;
        this.prolet = prolet;
        this.otpaiki = otpaiki;
    }

    public GeoLineTowerNode getNode() {
        return node;
    }

    public void setNode(GeoLineTowerNode node) {
        this.node = node;
    }

    public GeoLineProlet getProlet() {
        return prolet;
    }

    public void setProlet(GeoLineProlet prolet) {
        this.prolet = prolet;
    }

    public Map<Long, GeoLineTowers> getOtpaiki() {
        return otpaiki;
    }

    public void setOtpaiki(Map<Long, GeoLineTowers> otpaiki) {
        this.otpaiki = otpaiki;
    }
}
