package ru.drsk.progserega.inspectionsheet.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;

public class Transformer extends Equipment {
    private long id;
    private int slot;
   // private String place;
    private EquipmentModel model;

    private List<InspectionPhoto> photoList;
    //private Date inspectionDate;

//    public Transformer(long id, int slot, EquipmentModel model) {
//        this.id = id;
//        this.slot = slot;
//        this.model = model;
//        this.photoList = new ArrayList<>(); //!!
//        inspectionDate = null;
//        this.name = model.getName();
//        this.type = EquipmentType.TRANSFORMER;
//    }
//
    public Transformer(long id, int slot, EquipmentModel model, int year, Date inspectionDate, EquipmentType equipmentType) {
        this.id = id;
        this.slot = slot;
        this.model = model;
        this.year = year;
        this.photoList = new ArrayList<>();//!!
        this.inspectionDate = inspectionDate;
        this.name = model.getName();
        this.type = equipmentType;
    }

    public Transformer(long id, String place, EquipmentModel model, int year, Date inspectionDate, EquipmentType equipmentType) {
        this.id = id;
        this.place = place;
        this.model = model;
        this.year = year;

        this.photoList = new ArrayList<>();//!!
        this.inspectionDate = inspectionDate;
        this.name = model.getName();
        this.type = equipmentType;

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

    public EquipmentModel getModel() {
        return model;
    }

    public void setModel(EquipmentModel model) {
        this.model = model;
    }

//    public int getYear() {
//        return year;
//    }
//
//    public void setYear(int year) {
//        this.year = year;
//    }

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

//    public String getPlace() {
//        return place;
//    }

    @Override
    public Point getLocation() {
        return null;
    }
}
