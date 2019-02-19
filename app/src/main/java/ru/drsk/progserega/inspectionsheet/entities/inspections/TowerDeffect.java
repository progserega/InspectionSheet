package ru.drsk.progserega.inspectionsheet.entities.inspections;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.catalogs.TowerDeffectType;

public class TowerDeffect {
    private long id;
    private long towerId;
    private TowerDeffectType deffectType;
    private List<InspectionPhoto> photos;

    public TowerDeffect(long id, long towerId, TowerDeffectType deffectType) {
        this.id = id;
        this.towerId = towerId;
        this.deffectType = deffectType;
        this.photos = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public long getTowerId() {
        return towerId;
    }

    public TowerDeffectType getDeffectType() {
        return deffectType;
    }

    public List<InspectionPhoto> getPhotos() {
        return photos;
    }

    public void addPhoto(InspectionPhoto photo) {
        photos.add(photo);
    }
}
