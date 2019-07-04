package ru.drsk.progserega.inspectionsheet.storages.http.ste_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeoLineTowerNode {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("voltage")
    @Expose
    private int voltage;

    @SerializedName("node_id")
    @Expose
    private long nodeId;

    @SerializedName("ele")
    @Expose
    private double ele;

    @SerializedName("lat")
    @Expose
    private double lat;

    @SerializedName("lon")
    @Expose
    private double lon;

    public GeoLineTowerNode(String name, int voltage, long nodeId, double ele, double lat, double lon) {
        this.name = name;
        this.voltage = voltage;
        this.nodeId = nodeId;
        this.ele = ele;
        this.lat = lat;
        this.lon = lon;
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

    public long getNodeId() {
        return nodeId;
    }

    public void setNodeId(long nodeId) {
        this.nodeId = nodeId;
    }

    public double getEle() {
        return ele;
    }

    public void setEle(double ele) {
        this.ele = ele;
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
}
