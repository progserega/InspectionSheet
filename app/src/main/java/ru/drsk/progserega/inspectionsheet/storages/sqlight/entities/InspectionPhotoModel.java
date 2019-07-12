package ru.drsk.progserega.inspectionsheet.storages.sqlight.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "inspections_photos")
public class InspectionPhotoModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "inspection_id")
    private long inspectionId;

    @ColumnInfo(name = "photo_path")
    private String photoPath;

    @ColumnInfo(name = "type")
    private String type;

    public InspectionPhotoModel(long id, long inspectionId, String photoPath, String type) {
        this.id = id;
        this.inspectionId = inspectionId;
        this.photoPath = photoPath;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(long inspectionId) {
        this.inspectionId = inspectionId;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
