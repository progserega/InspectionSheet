package ru.drsk.progserega.inspectionsheet.entities.inspections;

import android.print.PrinterId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * информация о осмотре опоры
 */
public class TowerInspection {
    private long id;
    private long towerUniqId;
    private String comment;
    private List<InspectionPhoto> photos;
    private Date inspectionDate;

    public TowerInspection(long id, long towerUniqId, String comment, Date inspectionDate) {
        this.id = id;
        this.towerUniqId = towerUniqId;
        this.comment = comment;
        this.inspectionDate = inspectionDate;
        photos = new ArrayList<>();
    }

    public TowerInspection(long id, long towerUniqId, String comment, List<InspectionPhoto> photos, Date inspectionDate) {
        this(id,  towerUniqId, comment, inspectionDate);
        this.photos = photos;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTowerUniqId() {
        return towerUniqId;
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

    public void setTowerUniqId(long towerUniqId) {
        this.towerUniqId = towerUniqId;
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
