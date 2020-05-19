package ru.drsk.progserega.inspectionsheet.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Substation extends Equipment {

    private List<EquipmentModel> transformerTypes;

    public List<EquipmentModel> getTransformerTypes() {
        return transformerTypes;
    }

    public void setTransformerTypes(List<EquipmentModel> transformerTypes) {
        this.transformerTypes = transformerTypes;
    }

    Point location;
    public Substation(long id, long uniqId, String name , Date inspectionDate, float inspectionPercent, double lat, double lon){
        this.id = id;
        this.uniqId = uniqId;
        this.name = name;
        this.type = EquipmentType.SUBSTATION;

        this.transformerTypes = new ArrayList<>();
        this.inspectionDate = inspectionDate;
        this.inspectionPercent = inspectionPercent;
        this.location = new Point(lat,lon,0);
    }

    @Override
    public Point getLocation() {
        return location;
    }
}
