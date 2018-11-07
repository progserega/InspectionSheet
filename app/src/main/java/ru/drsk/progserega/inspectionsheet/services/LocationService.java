package ru.drsk.progserega.inspectionsheet.services;

import ru.drsk.progserega.inspectionsheet.entities.Point;

public class LocationService implements ILocation {

    @Override
    public Point getUserPosition() {
        return null;
    }

    @Override
    public double distanceBetween(Point p1, Point p2) {
        return 0;
    }

    @Override
    public float defaultSearchRadius() {
        return 0;
    }
}
