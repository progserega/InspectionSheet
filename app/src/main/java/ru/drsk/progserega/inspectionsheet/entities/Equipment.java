package ru.drsk.progserega.inspectionsheet.entities;

import java.util.Date;

public abstract class Equipment {
    protected long id;
    protected long uniqId;
    protected String name;
    protected String place;
    protected EquipmentType type;

    protected Date inspectionDate = new Date(0);
    protected float inspectionPercent = 0;

    public Equipment() {
    }

    public Equipment(long id,long uniqId, String name, EquipmentType type) {

        this.id = id;
        this.uniqId = uniqId;
        this.name = name;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUniqId() {
        return uniqId;
    }

    public void setUniqId(long uniqId) {
        this.uniqId = uniqId;
    }

    public String getName() {
        return name;
    }

    public EquipmentType getType() {
        return type;
    }

    public Date getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(Date inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public float getInspectionPercent() {
        return inspectionPercent;
    }

    public void setInspectionPercent(float inspectionPercent) {
        this.inspectionPercent = inspectionPercent;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    abstract public Point getLocation();
}
