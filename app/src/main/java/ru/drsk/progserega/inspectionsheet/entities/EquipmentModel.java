package ru.drsk.progserega.inspectionsheet.entities;

import java.util.ArrayList;
import java.util.List;

public class EquipmentModel
//        extends Equipment
{

    private long id;
    private String name;

    public EquipmentModel(long id, String name) {
        this.id = id;
        this.name = name;
      //  this.type = EquipmentType.TRANSFORMER;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    //    public static List<String> getNames(List<TransformerType> transformators){
//        List<String> names = new ArrayList<>();
//        if(transformators == null){
//            return  names;
//        }
//        for (TransformerType trans: transformators){
//            names.add(trans.getName());
//        }
//        return names;
//    }
//
//    @Override
//    public Point getLocation() {
//        return null;
//    }
}
