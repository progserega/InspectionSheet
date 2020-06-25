package ru.drsk.progserega.inspectionsheet.storages.http.api_is_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
id: 463,
substation_id: 1,
transformer_id: 159,
slot: 1,
voltage: "110/10",
power: "25,0",
vn: "110",
sn: null,
nn: "10",
type_rn: "VIII200Y-76-10191WR",
is_pvb: "нет",
type_oil_def: "ВОФ, ТСФ",
manufacturer: "СВЭЛ",
manuf_number: null,
manufacture_year: 2017,
start_exploitation_year: 2018,
inspection_percent: null,
inspection_date: null
 */
public class TPTransformerModel {

    @SerializedName("substation_id")
    @Expose
    private long substationId;

    @SerializedName("transformer_id")
    @Expose
    private long transformerTypeid;

    @SerializedName("slot")
    @Expose
    private int slot;

    @SerializedName("voltage")
    @Expose
    private String voltage;

    @SerializedName("power")
    @Expose
    private String power;

    @SerializedName("vn")
    @Expose
    private String vn;

    @SerializedName("sn")
    @Expose
    private String sn;

    @SerializedName("nn")
    @Expose
    private String nn;

    @SerializedName("type_rn")
    @Expose
    private String typeRN;

    @SerializedName("is_pvb")
    @Expose
    private String isPVB;

    @SerializedName("type_oil_def")
    @Expose
    private String typeOilDef;

    @SerializedName("manufacturer")
    @Expose
    private String manufacturer;

    @SerializedName("manuf_number")
    @Expose
    private String manufNumber;

    @SerializedName("manufacture_year")
    @Expose
    private int manufactureYear;

    @SerializedName("start_exploitation_year")
    @Expose
    private int startExploitationYear;

    @SerializedName("slot_name")
    @Expose
    private String place;

    public TPTransformerModel(
            long substationId,
            long transformerTypeid,
            int slot,
            String voltage,
            String power,
            String vn, String sn, String nn,
            String typeRN,
            String isPVB,
            String typeOilDef,
            String manufacturer,
            String manufNumber,
            int manufactureYear,
            int startExploitationYear,
            String place
            ) {

        this.substationId = substationId;
        this.transformerTypeid = transformerTypeid;
        this.slot = slot;
        this.voltage = voltage;
        this.power = power;
        this.vn = vn;
        this.sn = sn;
        this.nn = nn;
        this.typeRN = typeRN;
        this.isPVB = isPVB;
        this.typeOilDef = typeOilDef;
        this.manufacturer = manufacturer;
        this.manufNumber = manufNumber;
        this.manufactureYear = manufactureYear;
        this.startExploitationYear = startExploitationYear;
        this.place = place;
    }

    public long getSubstationId() {
        return substationId;
    }

    public void setSubstationId(long substationId) {
        this.substationId = substationId;
    }

    public long getTransformerTypeid() {
        return transformerTypeid;
    }

    public void setTransformerTypeid(long transformerTypeid) {
        this.transformerTypeid = transformerTypeid;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

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

    public String getVn() {
        return vn;
    }

    public void setVn(String vn) {
        this.vn = vn;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getNn() {
        return nn;
    }

    public void setNn(String nn) {
        this.nn = nn;
    }

    public String getTypeRN() {
        return typeRN;
    }

    public void setTypeRN(String typeRN) {
        this.typeRN = typeRN;
    }

    public String getIsPVB() {
        return isPVB;
    }

    public void setIsPVB(String isPVB) {
        this.isPVB = isPVB;
    }

    public String getTypeOilDef() {
        return typeOilDef;
    }

    public void setTypeOilDef(String typeOilDef) {
        this.typeOilDef = typeOilDef;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getManufNumber() {
        return manufNumber;
    }

    public void setManufNumber(String manufNumber) {
        this.manufNumber = manufNumber;
    }

    public int getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(int manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

    public int getStartExploitationYear() {
        return startExploitationYear;
    }

    public void setStartExploitationYear(int startExploitationYear) {
        this.startExploitationYear = startExploitationYear;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
