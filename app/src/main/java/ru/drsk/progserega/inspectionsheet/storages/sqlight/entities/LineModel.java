package ru.drsk.progserega.inspectionsheet.storages.sqlight.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "lines")
public class LineModel {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "uniq_id")
    private long uniqId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "voltage")
    private int voltage;

    @ColumnInfo(name = "start_exploitation_year")
    private int startExploitationYear;

    @ColumnInfo(name = "bbox_top_lat")
    private double bboxTopLat;

    @ColumnInfo(name = "bbox_top_lon")
    private double bboxTopLob;

    @ColumnInfo(name = "bbox_bottom_lat")
    private double bboxBottomLat;

    @ColumnInfo(name = "bbox_botttom_lon")
    private double bboxBottomLob;

    public LineModel(long id, long uniqId, String name, int voltage, int startExploitationYear, double bboxTopLat, double bboxTopLob, double bboxBottomLat, double bboxBottomLob) {
        this.id = id;
        this.uniqId = uniqId;
        this.name = name;
        this.voltage = voltage;
        this.startExploitationYear = startExploitationYear;
        this.bboxTopLat = bboxTopLat;
        this.bboxTopLob = bboxTopLob;
        this.bboxBottomLat = bboxBottomLat;
        this.bboxBottomLob = bboxBottomLob;
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

    public double getBboxTopLat() {
        return bboxTopLat;
    }

    public void setBboxTopLat(double bboxTopLat) {
        this.bboxTopLat = bboxTopLat;
    }

    public double getBboxTopLob() {
        return bboxTopLob;
    }

    public void setBboxTopLob(double bboxTopLob) {
        this.bboxTopLob = bboxTopLob;
    }

    public double getBboxBottomLat() {
        return bboxBottomLat;
    }

    public void setBboxBottomLat(double bboxBottomLat) {
        this.bboxBottomLat = bboxBottomLat;
    }

    public double getBboxBottomLob() {
        return bboxBottomLob;
    }

    public void setBboxBottomLob(double bboxBottomLob) {
        this.bboxBottomLob = bboxBottomLob;
    }
}
