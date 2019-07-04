package ru.drsk.progserega.inspectionsheet.services;

import java.lang.ref.WeakReference;

import ru.drsk.progserega.inspectionsheet.entities.Point;

public interface ILocation {
    Point getUserPosition();

    double distanceBetween(Point equipmentPoint, Point userPosition);

    float defaultSearchRadius();

    void setLocationChangeListener(WeakReference<ILocationChangeListener>  listener);

    void stopUsingGPS();
}
