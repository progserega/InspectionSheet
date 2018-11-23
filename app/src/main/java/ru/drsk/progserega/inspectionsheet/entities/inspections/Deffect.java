package ru.drsk.progserega.inspectionsheet.entities.inspections;

import java.util.ArrayList;
import java.util.List;

public class Deffect {
    private String description;

    private List<DeffectPhoto> photos;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<DeffectPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<DeffectPhoto> photos) {
        this.photos = photos;
    }

    public Deffect(){
        this.photos = new ArrayList<>();
        this.description = "";
    }
    public Deffect(String description) {
        this.photos = new ArrayList<>();
        this.description = description;
    }
}
