package ru.drsk.progserega.inspectionsheet.storages.http.api_is_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TowerJson {

    @SerializedName("tower_uniq_id")
    @Expose
    private long towerUniqId;

    @SerializedName("material")
    @Expose
    private int material;

    @SerializedName("type")
    @Expose
    private int type;

    @SerializedName("lat")
    @Expose
    private double lat;

    @SerializedName("lon")
    @Expose
    private double lon;

    @SerializedName("ele")
    @Expose
    private double ele;

    public TowerJson(long towerUniqId, int material, int type, double lat, double lon, double ele) {
        this.towerUniqId = towerUniqId;
        this.material = material;
        this.type = type;
        this.lat = lat;
        this.lon = lon;
        this.ele = ele;
    }

    public long getTowerUniqId() {
        return towerUniqId;
    }

    public void setTowerUniqId(long towerUniqId) {
        this.towerUniqId = towerUniqId;
    }

    public int getMaterial() {
        return material;
    }

    public void setMaterial(int material) {
        this.material = material;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getEle() {
        return ele;
    }

    public void setEle(double ele) {
        this.ele = ele;
    }
}
