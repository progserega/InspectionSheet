package ru.drsk.progserega.inspectionsheet.entities;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.inspections.TransformatorInspection;

public class Transformator extends Equipment{

    public Transformator(long id, String name) {
        this.id = id;
        this.name = name;
        this.type = EquipmentType.TRANSFORMATOR;
    }

    public static List<String> getNames(List<Transformator> transformators){
        List<String> names = new ArrayList<>();
        if(transformators == null){
            return  names;
        }
        for (Transformator trans: transformators){
            names.add(trans.getName());
        }
        return names;
    }
}
