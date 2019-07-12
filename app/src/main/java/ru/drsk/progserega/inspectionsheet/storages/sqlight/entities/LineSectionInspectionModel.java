package ru.drsk.progserega.inspectionsheet.storages.sqlight.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "line_section_inspection")
public class LineSectionInspectionModel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "section_id")
    private long sectionId;

    @ColumnInfo(name = "comment")
    private String comment;

    @ColumnInfo(name = "inspection_date")
    private Date inspectionDate;


    public LineSectionInspectionModel(long id, long sectionId, String comment, Date inspectionDate) {
        this.id = id;
        this.sectionId = sectionId;
        this.comment = comment;
        this.inspectionDate = inspectionDate;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(Date inspectionDate) {
        this.inspectionDate = inspectionDate;
    }
}
