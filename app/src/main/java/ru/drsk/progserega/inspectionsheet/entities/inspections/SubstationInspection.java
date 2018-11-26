package ru.drsk.progserega.inspectionsheet.entities.inspections;

import java.util.HashMap;
import java.util.Map;

import ru.drsk.progserega.inspectionsheet.entities.Substation;

public class SubstationInspection {
    private Substation substation;
    private Map<Long, TransformatorInspection> transformatorInspectionMap;

    public Substation getSubstation() {
        return substation;
    }

    public SubstationInspection(Substation substation) {
        this.substation = substation;
        transformatorInspectionMap = new HashMap<>();
    }

    public TransformatorInspection getInspectionByTransformator(long transformatorId){
        return transformatorInspectionMap.get(transformatorId);
    }

    public void addInspection(long transformatorId, TransformatorInspection inspection ){
        transformatorInspectionMap.put(transformatorId, inspection);
    }
}
