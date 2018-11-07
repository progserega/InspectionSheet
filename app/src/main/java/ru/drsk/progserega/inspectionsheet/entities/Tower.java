package ru.drsk.progserega.inspectionsheet.entities;

/**
 * Опора
 */
public class Tower extends Equipment {
    private Point mapPoint;

    public Point getMapPoint() {
        return mapPoint;
    }

    public Tower(int id, Point point ){
        this.id = id;
        this.name = "опора";
        this.type = EquipmentType.TOWER;
        this.mapPoint = point;
    }
}
