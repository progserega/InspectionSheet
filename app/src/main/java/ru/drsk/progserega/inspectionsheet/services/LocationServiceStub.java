package ru.drsk.progserega.inspectionsheet.services;

import ru.drsk.progserega.inspectionsheet.entities.Point;

public class LocationServiceStub implements ILocation {


    @Override
    public float defaultSearchRadius() {
        return 2.0f;
    }

    @Override
    public Point getUserPosition() {
        return new Point(1.0d, 1.0d);
    }

    /**
     * Находит расстояние между двумя точками
     *
     * Для отладки запилим на плоскости по теореме пифагора
     * @param p1 первая точка
     * @param p2 вторая точка
     * @return расстояние
     */
    @Override
    public double distanceBetween(Point p1, Point p2) {
        return Math.sqrt((p2.getLat() - p1.getLat()) * (p2.getLat() - p1.getLat()) + (p2.getLon() - p1.getLon()) * (p2.getLon() - p1.getLon()));
    }


}
