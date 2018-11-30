package ru.drsk.progserega.inspectionsheet.storages.http.ste_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SteTPModel {

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("uniq_id")
    @Expose
    private long uniqId;

    @SerializedName("power_center_name")
    @Expose
    private String powerCenterName;

    @SerializedName("disp_name_name")
    @Expose
    private String dispNameName;

    @SerializedName("sp_name")
    @Expose
    private String spName;

    @SerializedName("res_name")
    @Expose
    private String resName;

    @SerializedName("location_lat")
    @Expose
    private long lat;

    @SerializedName("location_lon")
    @Expose
    private long lon;


    @SerializedName("t1")
    @Expose
    SteTransformator t1;

    @SerializedName("t2")
    @Expose
    SteTransformator t2;

    @SerializedName("t3")
    @Expose
    SteTransformator t3;

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

    public String getPowerCenterName() {
        return powerCenterName;
    }

    public void setPowerCenterName(String powerCenterName) {
        this.powerCenterName = powerCenterName;
    }

    public String getDispNameName() {
        return dispNameName;
    }

    public void setDispNameName(String dispNameName) {
        this.dispNameName = dispNameName;
    }

    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public long getLat() {
        return lat;
    }

    public void setLat(long lat) {
        this.lat = lat;
    }

    public long getLon() {
        return lon;
    }

    public void setLon(long lon) {
        this.lon = lon;
    }

    public SteTransformator getT1() {
        return t1;
    }

    public void setT1(SteTransformator t1) {
        this.t1 = t1;
    }

    public SteTransformator getT2() {
        return t2;
    }

    public void setT2(SteTransformator t2) {
        this.t2 = t2;
    }

    public SteTransformator getT3() {
        return t3;
    }

    public void setT3(SteTransformator t3) {
        this.t3 = t3;
    }
}
