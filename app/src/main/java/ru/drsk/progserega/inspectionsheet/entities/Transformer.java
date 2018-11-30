package ru.drsk.progserega.inspectionsheet.entities;

import java.util.ArrayList;
import java.util.List;

public class Transformer extends Equipment{

    public Transformer(long id, String name) {
        this.id = id;
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
