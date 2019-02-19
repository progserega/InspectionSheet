package ru.drsk.progserega.inspectionsheet.storages.sqlight.entities;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;

@Entity(tableName = "inspection_items")
public class InspectionItemModel {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "parent_id")
    private long parentId = 0;

    @ColumnInfo(name = "number")
    private String number;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "type")
    private int type;

    @ColumnInfo(name = "result")
    private String result;

    @ColumnInfo(name = "sub_result")
    private String subResult;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSubResult() {
        return subResult;
    }

    public void setSubResult(String subResult) {
        this.subResult = subResult;
    }

    public InspectionItemModel(long id, long parentId, String number, String name, int type, String result, String subResult) {
        this.id = id;
        this.parentId = parentId;
        this.number = number;
        this.name = name;
        this.type = type;
        this.result = result;
        this.subResult = subResult;
    }

    public InspectionItemModel(InspectionItem inspectionItem, String result, String subResult){
        id = inspectionItem.getValueId();
        number = inspectionItem.getNumber();
        name = inspectionItem.getName();
        type = inspectionItem.getType().getValue();

        this.result = result;
        this.subResult = subResult;

    }
}
