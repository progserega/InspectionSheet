package ru.drsk.progserega.inspectionsheet.storages.sqlight.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Модель таблици описывает район электрических сетей
 * связан с сетевым предприятием
 */
@Entity(
        foreignKeys = @ForeignKey(entity = SP.class,
                parentColumns = "id",
                childColumns = "sp_id",
                onDelete = ForeignKey.CASCADE),
        tableName = "res",
        indices = {@Index("sp_id")}
)
public class Res {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "full_name")
    private String fullName;

    @ColumnInfo(name = "sp_id")
    private long enterpriseId;

    public Res(long id, String name, String fullName, long enterpriseId) {
        this.id = id;
        this.name = name;
        this.fullName = fullName;
        this.enterpriseId = enterpriseId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }
}
