package ru.drsk.progserega.inspectionsheet.storages.sqlight.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

public class DefectDescriptionWithPhoto {

    @ColumnInfo(name = "deffect_id")
    private long deffectId;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "photo_path")
    private String photoPath;

    public DefectDescriptionWithPhoto(long deffectId, String description, String photoPath) {
        this.deffectId = deffectId;
        this.description = description;
        this.photoPath = photoPath;
    }

    public long getDeffectId() {
        return deffectId;
    }

    public void setDeffectId(long deffectId) {
        this.deffectId = deffectId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}
