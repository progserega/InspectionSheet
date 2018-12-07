package ru.drsk.progserega.inspectionsheet.storages.sqlight.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "transformers")
public class TransformerModel {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private long id;


    @ColumnInfo(name = "tr_type")
    private String type;

    @ColumnInfo(name = "desc")
    private String desc;


    public TransformerModel(long id, String type, String desc) {
        this.id = id;
        this.type = type;
        this.desc = desc;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
