package ru.drsk.progserega.inspectionsheet.storages.sqlight.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "tower_inspection")
public class TowerInspectionModel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "line_uniq_id")
    private long lineUniqId;

    @ColumnInfo(name = "tower_uniq_id")
    private long towerUniqId;

    @ColumnInfo(name = "comment")
    private long deffectTypeId;

    @ColumnInfo(name = "inspection_date")
    private Date inspectionDate;

    public TowerInspectionModel(long id, long lineUniqId, long towerUniqId, long deffectTypeId, Date inspectionDate) {
        this.id = id;
        this.lineUniqId = lineUniqId;
        this.towerUniqId = towerUniqId;
        this.deffectTypeId = deffectTypeId;
        this.inspectionDate = inspectionDate;
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

    public long getTowerUniqId() {
        return towerUniqId;
    }

    public void setTowerUniqId(long towerUniqId) {
        this.towerUniqId = towerUniqId;
    }

    public long getDeffectTypeId() {
        return deffectTypeId;
    }

    public void setDeffectTypeId(long deffectTypeId) {
        this.deffectTypeId = deffectTypeId;
    }

    public Date getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(Date inspectionDate) {
        this.inspectionDate = inspectionDate;
    }
}
