package ru.drsk.progserega.inspectionsheet.storages.http.ste_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class GeoSubstation {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("voltage")
    @Expose
    private String voltage;

    @SerializedName("object_type")
    @Expose
    private String objectType;

    @SerializedName("lat")
    @Expose
    private double lat;

    @SerializedName("lon")
    @Expose
    private double lon;

    @SerializedName("res")
    @Expose
    private  Map<String, String> res;

    @SerializedName("sp")
    @Expose
    private  Map<String, String> sp;

    @SerializedName("nodes")
    @Expose
    private Map<Long, GeoSubstationNode> nodes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
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

    public Map<Long, GeoSubstationNode> getNodes() {
        return nodes;
    }

    public void setNodes(Map<Long, GeoSubstationNode> nodes) {
        this.nodes = nodes;
    }

    public Map<String, String> getRes() {
        return res;
    }

    public void setRes(Map<String, String> res) {
        this.res = res;
    }

    public Map<String, String> getSp() {
        return sp;
    }

    public void setSp(Map<String, String> sp) {
        this.sp = sp;
    }
}
