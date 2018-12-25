package ru.drsk.progserega.inspectionsheet.storages.json.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubstationTransformerJson {

    @SerializedName("voltage")
    @Expose
    private String voltage;

    @SerializedName("power")
    @Expose
    private String power;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("VN")
    @Expose
    private String VN;

    @SerializedName("SN")
    @Expose
    private String SN;

    @SerializedName("NN")
    @Expose
    private String NN;

    @SerializedName("rpn_type")
    @Expose
    private String rpnType;

    @SerializedName("is_PVB")
    @Expose
    private String isPVB;

    @SerializedName("type_oil")
    @Expose
    private String oilDefenceType;

    @SerializedName("manufacturer")
    @Expose
    private String manufacturer;


    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVN() {
        return VN;
    }

    public void setVN(String VN) {
        this.VN = VN;
    }

    public String getSN() {
        return SN;
    }

    public void setSN(String SN) {
        this.SN = SN;
    }

    public String getNN() {
        return NN;
    }

    public void setNN(String NN) {
        this.NN = NN;
    }

    public String getRpnType() {
        return rpnType;
    }

    public void setRpnType(String rpnType) {
        this.rpnType = rpnType;
    }

    public String getIsPVB() {
        return isPVB;
    }

    public void setIsPVB(String isPVB) {
        this.isPVB = isPVB;
    }

    public String getOilDefenceType() {
        return oilDefenceType;
    }

    public void setOilDefenceType(String oilDefenceType) {
        this.oilDefenceType = oilDefenceType;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}
