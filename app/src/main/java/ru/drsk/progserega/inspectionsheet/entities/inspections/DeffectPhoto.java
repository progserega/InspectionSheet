package ru.drsk.progserega.inspectionsheet.entities.inspections;

import android.graphics.Bitmap;

public class DeffectPhoto {

    private Bitmap thumbnail;


    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public DeffectPhoto(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }
}
