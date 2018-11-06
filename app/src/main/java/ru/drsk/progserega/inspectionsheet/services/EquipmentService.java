package ru.drsk.progserega.inspectionsheet.services;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;
import ru.drsk.progserega.inspectionsheet.entities.Line;
import ru.drsk.progserega.inspectionsheet.entities.Point;
import ru.drsk.progserega.inspectionsheet.entities.Voltage;
import ru.drsk.progserega.inspectionsheet.storages.ILineStorage;

public class EquipmentService {

   // private LinesService linesService;
    private ILineStorage lineStorage;
    private ILocation location;

    public EquipmentService(ILineStorage lineStorage,  ILocation location){
        this.lineStorage = lineStorage;
        this.location = location;
    }

    public List<Equipment> getByType(EquipmentType type){

        return  new ArrayList<>();
    }

    public List<Equipment> getByType(EquipmentType type, Voltage voltage){

        List<Equipment> equipmentLines = new ArrayList<>();
        List<Line>  lines =  lineStorage.getLinesByType(voltage);

        for (Line line: lines) {
            equipmentLines.add((Equipment) line);
        }
        return  equipmentLines;
    }

    public List<Equipment> getByTypeAndName(EquipmentType type, Voltage voltage, String name){
        List<Equipment> equipmentLines = new ArrayList<>();
        List<Line>  lines =  lineStorage.getLinesByTypeAndName(voltage, name);

        for (Line line: lines) {
            equipmentLines.add((Equipment) line);
        }
        return  equipmentLines;
    }

    public List<Equipment> getByTypeAndPoint(EquipmentType type, Point point){
        List<Equipment> equipmentLines = new ArrayList<>();

        return equipmentLines;
    }
}
