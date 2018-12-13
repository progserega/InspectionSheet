package ru.drsk.progserega.inspectionsheet.storages.sqlight.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.text.TextUtils;

import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItemResult;

@Entity(tableName = "inspections")
public class InspectionModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "substation_id")
    private long substationId;

    @ColumnInfo(name = "substation_type")
    private int substationType;

    @ColumnInfo(name = "equipment_id")
    private long equipmentId;

    @ColumnInfo(name = "deffect_id")
    private long deffectId;

    @ColumnInfo(name = "deffect_values")
    private String deffectValues;

    @ColumnInfo(name = "deffect_sub_values")
    private String deffectSubValues;

    @ColumnInfo(name = "deffect_comment")
    private String deffectComment;

    public InspectionModel(long id, long substationId, int substationType, long equipmentId, long deffectId, String deffectValues, String deffectSubValues, String deffectComment) {
        this.id = id;
        this.substationId = substationId;
        this.substationType = substationType;
        this.equipmentId = equipmentId;
        this.deffectId = deffectId;
        this.deffectValues = deffectValues;
        this.deffectSubValues = deffectSubValues;
        this.deffectComment = deffectComment;
    }

    public InspectionModel(long substationId, int substationType, long equipmentId, InspectionItem inspectionItem) {
        this.id = inspectionItem.getId();
        this.deffectId = inspectionItem.getValueId();
        this.substationId = substationId;
        this.substationType = substationType;
        this.equipmentId = equipmentId;
        this.deffectValues = TextUtils.join(",", inspectionItem.getResult().getValues());
        this.deffectSubValues = TextUtils.join(",", inspectionItem.getResult().getSubValues());
        this.deffectComment = inspectionItem.getResult().getComment();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSubstationId() {
        return substationId;
    }

    public void setSubstationId(long substationId) {
        this.substationId = substationId;
    }

    public int getSubstationType() {
        return substationType;
    }

    public void setSubstationType(int substationType) {
        this.substationType = substationType;
    }

    public long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(long equipmentId) {
        this.equipmentId = equipmentId;
    }

    public long getDeffectId() {
        return deffectId;
    }

    public void setDeffectId(long deffectId) {
        this.deffectId = deffectId;
    }

    public String getDeffectComment() {
        return deffectComment;
    }

    public void setDeffectComment(String deffectComment) {
        this.deffectComment = deffectComment;
    }

    public String getDeffectValues() {
        return deffectValues;
    }

    public void setDeffectValues(String deffectValues) {
        this.deffectValues = deffectValues;
    }

    public String getDeffectSubValues() {
        return deffectSubValues;
    }

    public void setDeffectSubValues(String deffectSubValues) {
        this.deffectSubValues = deffectSubValues;
    }
}
