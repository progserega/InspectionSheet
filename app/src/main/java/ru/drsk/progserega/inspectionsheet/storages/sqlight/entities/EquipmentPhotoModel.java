package ru.drsk.progserega.inspectionsheet.storages.sqlight.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "equipment_photos")
public class EquipmentPhotoModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "equipment_id")
    private long equipmentId;

    @ColumnInfo(name = "substation_type")
    private int substationType;

    @ColumnInfo(name = "photo_path")
    private String photoPath;

    public EquipmentPhotoModel(long id, long equipmentId, int substationType, String photoPath) {
        this.id = id;
        this.equipmentId = equipmentId;
        this.substationType = substationType;
        this.photoPath = photoPath;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(long equipmentId) {
        this.equipmentId = equipmentId;
    }

    public int getSubstationType() {
        return substationType;
    }

    public void setSubstationType(int substationType) {
        this.substationType = substationType;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}
