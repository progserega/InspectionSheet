package ru.drsk.progserega.inspectionsheet.storages.sqlight.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "line_section")
public class LineSectionModel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "line_uniq_id")
    private long lineUniqId;

    @ColumnInfo(name = "from_tower_uniq_id")
    private long fromTowerUniqId;

    @ColumnInfo(name = "to_tower_uniq_id")
    private long toTowerUniqId;

    public LineSectionModel(long id, long lineUniqId, long fromTowerUniqId, long toTowerUniqId) {
        this.id = id;
        this.lineUniqId = lineUniqId;
        this.fromTowerUniqId = fromTowerUniqId;
        this.toTowerUniqId = toTowerUniqId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLineUniqId() {
        return lineUniqId;
    }

    public void setLineUniqId(long lineUniqId) {
        this.lineUniqId = lineUniqId;
    }

    public long getFromTowerUniqId() {
        return fromTowerUniqId;
    }

    public void setFromTowerUniqId(long fromTowerUniqId) {
        this.fromTowerUniqId = fromTowerUniqId;
    }

    public long getToTowerUniqId() {
        return toTowerUniqId;
    }

    public void setToTowerUniqId(long toTowerUniqId) {
        this.toTowerUniqId = toTowerUniqId;
    }
}
