package ru.drsk.progserega.inspectionsheet.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;

public class TransformerInSlot {
    private long id;
    private int slot;
    private TransformerType transformerType;
    private int year;
    private List<InspectionPhoto> photoList;
    private Date inspectionDate;

    public TransformerInSlot(long id, int slot, TransformerType transformerType) {
        this.id = id;
        this.slot = slot;
        this.transformerType = transformerType;
        this.photoList = new ArrayList<>(); //!!
        inspectionDate = null;
    }

    public TransformerInSlot(long id, int slot, TransformerType transformerType, int year, Date inspectionDate) {
        this.id = id;
        this.slot = slot;
        this.transformerType = transformerType;
        this.year = year;
        this.photoList = new ArrayList<>();//!!
        this.inspectionDate = inspectionDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public TransformerType getTransformerType() {
        return transformerType;
    }

    public void setTransformerType(TransformerType transformerType) {
        this.transformerType = transformerType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<InspectionPhoto> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<InspectionPhoto> photoList) {
        this.photoList = photoList;
    }

    public Date getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(Date inspectionDate) {
        this.inspectionDate = inspectionDate;
    }
}
