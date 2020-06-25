package ru.drsk.progserega.inspectionsheet.storages.http.api_is_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class TPModel {
    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("uniq_id")
    @Expose
    private long uniqId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("alt_name")
    @Expose
    private String alterName;

    @SerializedName("sp_id")
    @Expose
    private long spId;

    @SerializedName("res_id")
    @Expose
    private long resId;

    @SerializedName("location_lat")
    @Expose
    private double lat;

    @SerializedName("location_lon")
    @Expose
    private double lon;

    @SerializedName("equipmnents")
    @Expose
    private List< TPTransformerModel > equipmnents;

    public TPModel(long id, long uniqId, String name, String alterName, long spId, long resId, double lat, double lon, List< TPTransformerModel > equipmnents) {
        this.id = id;
        this.uniqId = uniqId;
        this.name = name;
        this.alterName = alterName;
        this.spId = spId;
        this.resId = resId;
        this.lat = lat;
        this.lon = lon;
        this.equipmnents = equipmnents;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUniqId() {
        return uniqId;
    }

    public void setUniqId(long uniqId) {
        this.uniqId = uniqId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlterName() {
        return alterName;
    }

    public void setAlterName(String alterName) {
        this.alterName = alterName;
    }

    public long getSpId() {
        return spId;
    }

    public void setSpId(long spId) {
        this.spId = spId;
    }

    public long getResId() {
        return resId;
    }

    public void setResId(long resId) {
        this.resId = resId;
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

    public List< TPTransformerModel > getEquipmnents() {
        return equipmnents;
    }

    public void setEquipmnents(List< TPTransformerModel > equipmnents) {
        this.equipmnents = equipmnents;
    }
}
