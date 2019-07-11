package ru.drsk.progserega.inspectionsheet.storages.sqlight.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "line_section_deffects")
public class LineSectionDeffectModel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "section_id")
    private long sectionId;

    @ColumnInfo(name = "deffect_type_id")
    private long deffectTypeId;

    @ColumnInfo(name = "deffect_value")
    private int deffectValue;

    public LineSectionDeffectModel(long id, long sectionId, long deffectTypeId, int deffectValue) {
        this.id = id;
        this.sectionId = sectionId;
        this.deffectTypeId = deffectTypeId;
        this.deffectValue = deffectValue;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSectionId() {
        return sectionId;
    }

    public void setSectionId(long sectionId) {
        this.sectionId = sectionId;
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
