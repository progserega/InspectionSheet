package ru.drsk.progserega.inspectionsheet.services;

import ru.drsk.progserega.inspectionsheet.entities.Point;

public interface ILocationChangeListener {

    void onLocationChange(Point location);
}
