package ru.drsk.progserega.inspectionsheet.entities;

import java.util.ArrayList;
import java.util.List;

public class TransformerType extends Equipment{

    public TransformerType(long id, String name) {
        this.id = id;
        this.name = name;
        this.type = EquipmentType.TRANSFORMER;
    }

    public static List<String> getNames(List<TransformerType> transformators){
        List<String> names = new ArrayList<>();
        if(transformators == null){
            return  names;
        }
        for (TransformerType trans: transformators){
            names.add(trans.getName());
        }
        return names;
    }

    @Override
    public Point getLocation() {
        return null;
    }
}
