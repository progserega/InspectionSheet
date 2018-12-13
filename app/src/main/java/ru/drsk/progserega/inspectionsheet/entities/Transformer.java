package ru.drsk.progserega.inspectionsheet.entities;

import java.util.ArrayList;
import java.util.List;

public class Transformer extends Equipment{

    private long typeId;

    public long getTypeId() {
        return typeId;
    }


    public Transformer(long id, long typeId, String name) {
        this.id = id;
        this.typeId = typeId;
        this.name = name;
        this.type = EquipmentType.TRANSFORMER;
    }

    public static List<String> getNames(List<Transformer> transformators){
        List<String> names = new ArrayList<>();
        if(transformators == null){
            return  names;
        }
        for (Transformer trans: transformators){
            names.add(trans.getName());
        }
        return names;
    }
}
