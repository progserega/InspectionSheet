package ru.drsk.progserega.inspectionsheet.services;

import java.lang.ref.WeakReference;

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
     * @param equipmentPoint первая точка
     * @param userPosition вторая точка
     * @return расстояние
     */
    @Override
    public double distanceBetween(Point equipmentPoint, Point userPosition) {
        return Math.sqrt((userPosition.getLat() - equipmentPoint.getLat()) * (userPosition.getLat() - equipmentPoint.getLat()) + (userPosition.getLon() - equipmentPoint.getLon()) * (userPosition.getLon() - equipmentPoint.getLon()));
    }


    @Override
    public void stopUsingGPS() {
        return;
    }

    @Override
    public void setLocationChangeListener(WeakReference<ILocationChangeListener> listener) {

    }
}
