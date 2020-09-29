package ru.drsk.progserega.inspectionsheet.storages.sqlight.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "defects_descriptions")
public class DefectDescriptionModel {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "deffect_id")
    private long deffectId;

    @ColumnInfo(name = "object_type_id")
    private int objectTypeId;

    @ColumnInfo(name = "description")
    private String description;

    public DefectDescriptionModel(long id, long deffectId, int objectTypeId, String description) {
        this.id = id;
        this.deffectId = deffectId;
        this.objectTypeId = objectTypeId;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDeffectId() {
        return deffectId;
    }

    public void setDeffectId(long deffectId) {
        this.deffectId = deffectId;
    }

    public int getObjectTypeId() {
        return objectTypeId;
    }

    public void setObjectTypeId(int objectTypeId) {
        this.objectTypeId = objectTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
