package ru.drsk.progserega.inspectionsheet.storages.sqlight.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "tower_deffects")
public class TowerDeffectModel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "line_uniq_id")
    private long lineUniqId;

    @ColumnInfo(name = "tower_uniq_id")
    private long towerUniqId;

    @ColumnInfo(name = "deffect_type_id")
    private long deffectTypeId;

    @ColumnInfo(name = "deffect_value")
    private int deffectValue;

    public TowerDeffectModel(long id, long lineUniqId, long towerUniqId, long deffectTypeId, int deffectValue) {
        this.id = id;
        this.lineUniqId = lineUniqId;
        this.towerUniqId = towerUniqId;
        this.deffectTypeId = deffectTypeId;
        this.deffectValue = deffectValue;
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

    public int getDeffectValue() {
        return deffectValue;
    }

    public void setDeffectValue(int deffectValue) {
        this.deffectValue = deffectValue;
    }
}
