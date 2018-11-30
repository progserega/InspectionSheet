package ru.drsk.progserega.inspectionsheet.entities;

import java.util.ArrayList;
import java.util.List;

public class Substation extends Equipment {

    private List<Transformer> transformators;

    public List<Transformer> getTransformators() {
        return transformators;
    }

    public void setTransformators(List<Transformer> transformators) {
        this.transformators = transformators;
    }

    public Substation(long id, String name ){
        this.id = id;
        this.name = name;
        this.type = EquipmentType.SUBSTATION;

        this.transformators = new ArrayList<>();
    }
}
