package ru.drsk.progserega.inspectionsheet.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Substation extends Equipment {

    private List<TransformerType> transformerTypes;

    public List<TransformerType> getTransformerTypes() {
        return transformerTypes;
    }

    public void setTransformerTypes(List<TransformerType> transformerTypes) {
        this.transformerTypes = transformerTypes;
    }

    public Substation(long id, long uniqId, String name , Date inspectionDate, float inspectionPercent){
        this.id = id;
        this.uniqId = uniqId;
        this.name = name;
        this.type = EquipmentType.SUBSTATION;

        this.transformerTypes = new ArrayList<>();
        this.inspectionDate = inspectionDate;
        this.inspectionPercent = inspectionPercent;
    }
}
