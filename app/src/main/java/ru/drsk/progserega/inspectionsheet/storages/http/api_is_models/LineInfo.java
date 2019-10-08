package ru.drsk.progserega.inspectionsheet.storages.http.api_is_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LineInfo {

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("uniq_id")
    @Expose
    private long uniqId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("voltage")
    @Expose
    private int voltage;

    @SerializedName("start_exploitation_year")
    @Expose
    private int startExploitationYear;

    public LineInfo(long id, long uniqId, String name, int voltage, int startExploitationYear) {
        this.id = id;
        this.uniqId = uniqId;
        this.name = name;
        this.voltage = voltage;
        this.startExploitationYear = startExploitationYear;
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

    public int getVoltage() {
        return voltage;
    }

    public void setVoltage(int voltage) {
        this.voltage = voltage;
    }

    public int getStartExploitationYear() {
        return startExploitationYear;
    }

    public void setStartExploitationYear(int startExploitationYear) {
        this.startExploitationYear = startExploitationYear;
    }
}
