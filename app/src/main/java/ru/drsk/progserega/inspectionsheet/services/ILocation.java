package ru.drsk.progserega.inspectionsheet.services;

import ru.drsk.progserega.inspectionsheet.entities.Point;

public interface ILocation {
    Point getUserPosition();

    double distanceBetween(Point p1, Point p2);


}
