package ru.drsk.progserega.inspectionsheet.entities.inspections;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * информация о осмотре пролета
 */
public class LineSectionInspection {
    private long id;
    private long sectionId;
    private String comment;
    private List<InspectionPhoto> photos;
    private Date inspectionDate;

    public LineSectionInspection(long id, long sectionId, String comment, Date inspectionDate) {
        this.id = id;
        this.sectionId = sectionId;
        this.comment = comment;
        this.inspectionDate = inspectionDate;
        photos = new ArrayList<>();
    }

    public LineSectionInspection(long id, long sectionId, String comment, List<InspectionPhoto> photos, Date inspectionDate) {
        this(id, sectionId, comment, inspectionDate);
        this.photos = photos;
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

    public String getComment() {
        return comment;
    }

    public List<InspectionPhoto> getPhotos() {
        return photos;
    }

    public Date getInspectionDate() {
        return inspectionDate;
    }

    public void setSectionId(long sectionId) {
        this.sectionId = sectionId;
    }

    public void setInspectionDate(Date inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public void setPhotos(List<InspectionPhoto> photos) {
        this.photos = photos;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
