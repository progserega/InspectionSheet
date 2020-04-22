package ru.drsk.progserega.inspectionsheet.storages.sqlight.entities;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "log_storage")
public class LogModel {
    @PrimaryKey
    @ColumnInfo(name = "date")
    private Date date;

    @ColumnInfo(name = "level")
    private int level;

    @ColumnInfo(name = "tag")
    private String tag;

    @ColumnInfo(name = "message")
    private String message;

    public LogModel(Date date, int level, String tag, String message) {
        this.date = date;
        this.level = level;
        this.tag = tag;
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
