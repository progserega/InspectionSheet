package ru.drsk.progserega.inspectionsheet.storages.sqlight.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "defect_description_photos")
public class DefectDescriptionPhotoModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "description_id")
    private long descriptionId;

    @ColumnInfo(name = "image_url")
    private String imageUrl;

    @ColumnInfo(name = "photo_path")
    private String photoPath;

    public DefectDescriptionPhotoModel(long id, long descriptionId, String imageUrl, String photoPath) {
        this.id = id;
        this.descriptionId = descriptionId;
        this.imageUrl = imageUrl;
        this.photoPath = photoPath;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDescriptionId() {
        return descriptionId;
    }

    public void setDescriptionId(long descriptionId) {
        this.descriptionId = descriptionId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}
