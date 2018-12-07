package ru.drsk.progserega.inspectionsheet.entities;

import java.util.ArrayList;
import java.util.List;

public class Substation extends Equipment {

    private List<Transformer> transformers;

    public List<Transformer> getTransformers() {
        return transformers;
    }

    public void setTransformers(List<Transformer> transformers) {
        this.transformers = transformers;
    }

    public Substation(long id, String name ){
        this.id = id;
        this.name = name;
        this.type = EquipmentType.SUBSTATION;

        this.transformers = new ArrayList<>();
    }
}
