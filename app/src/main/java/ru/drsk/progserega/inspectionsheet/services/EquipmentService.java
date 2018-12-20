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
import ru.drsk.progserega.inspectionsheet.entities.Substation;
import ru.drsk.progserega.inspectionsheet.entities.TransformerSubstation;
import ru.drsk.progserega.inspectionsheet.entities.Voltage;
import ru.drsk.progserega.inspectionsheet.storages.ILineStorage;
import ru.drsk.progserega.inspectionsheet.storages.ISubstationStorage;
import ru.drsk.progserega.inspectionsheet.storages.ITransformerSubstationStorage;

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

    private ISubstationStorage substationStorage;

    private ITransformerSubstationStorage transformerSubstationStorage;

    public EquipmentService(ILineStorage lineStorage, ISubstationStorage substationStorage, ITransformerSubstationStorage transformerSubstationStorage) {
        this.lineStorage = lineStorage;
        //this.location = location;
        this.substationStorage = substationStorage;

        this.transformerSubstationStorage = transformerSubstationStorage;

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

    public void clearFilters(){
        this.filters.clear();
    }

    private List<Equipment> linesToEquipment(List<Line> lines) {
        List<Equipment> equipments = new ArrayList<>();
        for (Line line : lines) {
            equipments.add((Equipment) line);
        }
        return equipments;
    }

    private List<Equipment> substationsToEquipment(List<Substation> substations) {
        List<Equipment> equipments = new ArrayList<>();
        for (Substation substation : substations) {
            equipments.add((Equipment) substation);
        }
        return equipments;
    }

    private List<Equipment> transformerSubstationsToEquipment(List<TransformerSubstation> substations) {
        List<Equipment> equipments = new ArrayList<>();
        for (TransformerSubstation substation : substations) {
            equipments.add((Equipment) substation);
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
        else if ((EquipmentType) filters.get(EquipmentService.FILTER_TYPE) == EquipmentType.SUBSTATION) {
            List<Substation> substations = substationStorage.getByFilters(this.filters);
            return substationsToEquipment(substations);
        }
        else if ((EquipmentType) filters.get(EquipmentService.FILTER_TYPE) == EquipmentType.TRANS_SUBSTATION) {
            List<TransformerSubstation> substations = transformerSubstationStorage.getByFilters(this.filters);
            return transformerSubstationsToEquipment(substations);
        }
        else
            {
            //NOT IMPLEMENTED

        }


        return equipments;
    }

    public Line getLineById (long lineId){
        return lineStorage.getById(lineId);
    }

    public Substation getSubstationById(long substationId){
        return substationStorage.getById(substationId);
    }

    public TransformerSubstation getTransformerSubstationById(long id){
        return transformerSubstationStorage.getById(id);
    }


}
