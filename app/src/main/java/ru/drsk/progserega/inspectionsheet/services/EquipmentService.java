package ru.drsk.progserega.inspectionsheet.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;
import ru.drsk.progserega.inspectionsheet.entities.Line;
import ru.drsk.progserega.inspectionsheet.entities.LineTower;
import ru.drsk.progserega.inspectionsheet.entities.Point;
import ru.drsk.progserega.inspectionsheet.entities.Voltage;
import ru.drsk.progserega.inspectionsheet.storages.ILineStorage;

public class EquipmentService {

    public static final String FILTER_TYPE = "type";
    public static final String FILTER_VOLTAGE = "voltage";
    public static final String FILTER_NAME = "name";
    public static final String FILTER_POSITION = "position";
    public static final String FILTER_POSITION_RADIUS = "radius";
    public static final String FILTER_ENTERPRISE = "enterprise";
    public static final String FILTER_AREA = "area";


    private Map<String, Object> filters;

    // private LinesService linesService;
    private ILineStorage lineStorage;
    //private ILocation location;

    public EquipmentService(ILineStorage lineStorage) {
        this.lineStorage = lineStorage;
        //this.location = location;

        this.filters = new HashMap<>();
    }

    public Map<String, Object> getFilters() {
        return filters;
    }

    public void addFilter(String filterName, Object value) {
        this.filters.put(filterName, value);
    }

    public void removeFilter(String filterName) {
        this.filters.remove(filterName);
    }


    private List<Equipment> linesToEquipment(List<Line> lines) {
        List<Equipment> equipments = new ArrayList<>();
        for (Line line : lines) {
            equipments.add((Equipment) line);
        }
        return equipments;
    }

    public List<Equipment> getEquipments() {

        List<Equipment> equipments = new ArrayList<>();

        if (filters.get(EquipmentService.FILTER_TYPE) == null) {
            //тип должен быть задан всегда
            return equipments;
        }

        if ((EquipmentType) filters.get(EquipmentService.FILTER_TYPE) == EquipmentType.LINE) {
            List<Line> lines = lineStorage.getByFilters(this.filters);
            return linesToEquipment(lines);
        }
        else{
            //NOT IMPLEMENTED

        }


        return equipments;
    }

    public Line getLineById (long lineId){
        return lineStorage.getById(lineId);
    }

}
