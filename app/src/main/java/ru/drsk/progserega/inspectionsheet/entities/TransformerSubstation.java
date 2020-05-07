package ru.drsk.progserega.inspectionsheet.entities;

import java.util.Date;

public class TransformerSubstation extends Equipment {

    private Point location;

    public TransformerSubstation(long id, long uniqId, String name, Date inspectionDate, float inspectionPercent, double lat, double lon) {
        this.id = id;
        this.uniqId = uniqId;
        this.name = name;
        this.type = EquipmentType.TP;
        this.inspectionDate = inspectionDate;
        this.inspectionPercent = inspectionPercent;
        this.location = new Point(lat, lon, 0);
    }

    @Override
    public Point getLocation() {
        return location;
    }
}
