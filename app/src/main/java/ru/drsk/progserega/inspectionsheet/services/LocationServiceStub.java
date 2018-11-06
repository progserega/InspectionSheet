package ru.drsk.progserega.inspectionsheet.services;

import ru.drsk.progserega.inspectionsheet.entities.Point;

public class LocationServiceStub implements ILocation {

    @Override
    public Point getUserPosition() {
        return new Point(1.0d, 1.0d);
    }

    @Override
    //Для отладки запилим на плоскости по теореме пифагора
    public double distanceBetween(Point p1, Point p2) {
        return Math.sqrt((p2.getLat() - p1.getLat()) * (p2.getLat() - p1.getLat()) + (p2.getLon() - p1.getLon()) * (p2.getLon() - p1.getLon()));
    }


}
