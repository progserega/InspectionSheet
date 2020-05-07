package ru.drsk.progserega.inspectionsheet.services;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;
import ru.drsk.progserega.inspectionsheet.entities.Substation;
import ru.drsk.progserega.inspectionsheet.entities.TransformerSubstation;
import ru.drsk.progserega.inspectionsheet.entities.inspections.IStationInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectedSubstation;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TransformerInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectedTransformerSubstation;

public class StationInspectionFactory {

    private EquipmentService equipmentService;
    private InspectionService inspectionService;
    private IStationInspectionService stationInspectionService;

    public StationInspectionFactory(EquipmentService equipmentService, InspectionService inspectionService, IStationInspectionService stationInspectionService) {
        this.equipmentService = equipmentService;
        this.inspectionService = inspectionService;
        this.stationInspectionService = stationInspectionService;
    }


    public IStationInspection build(Equipment station) {

        List<InspectionItem> stationInspectionItems = stationInspectionService.getStationInspectionItems(station);

        if (station.getType().equals(EquipmentType.SUBSTATION)) {
            Substation substation = equipmentService.getSubstationById(station.getId());
            List<TransformerInspection> transformerInspections = inspectionService.getSubstationTransformersWithInspections(substation);

            InspectedSubstation substationInspection = new InspectedSubstation(substation, transformerInspections, stationInspectionItems);

            return substationInspection;

        }

        if (station.getType().equals(EquipmentType.TP)) {
            TransformerSubstation transformerSubstation = equipmentService.getTransformerSubstationById(station.getId());

            List<TransformerInspection> transformerInspections = inspectionService.getTPTransformersWithInspections(transformerSubstation);


            InspectedTransformerSubstation transformerStationInspection =  new InspectedTransformerSubstation(transformerSubstation, transformerInspections, stationInspectionItems);


            return transformerStationInspection;

        }

        return null;
    }


}
