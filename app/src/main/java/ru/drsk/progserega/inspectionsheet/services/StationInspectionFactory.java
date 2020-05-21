package ru.drsk.progserega.inspectionsheet.services;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;
import ru.drsk.progserega.inspectionsheet.entities.Substation;
import ru.drsk.progserega.inspectionsheet.entities.TransformerSubstation;
import ru.drsk.progserega.inspectionsheet.entities.inspections.IStationInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectedSubstation;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;
import ru.drsk.progserega.inspectionsheet.entities.inspections.StationEquipmentInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TransformerInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectedTransformerSubstation;

public class StationInspectionFactory {

    private EquipmentService equipmentService;
   // private InspectionService inspectionService;
    private IStationInspectionService stationInspectionService;

    public StationInspectionFactory(EquipmentService equipmentService, /*InspectionService inspectionService,*/ IStationInspectionService stationInspectionService) {
        this.equipmentService = equipmentService;
     //   this.inspectionService = inspectionService;
        this.stationInspectionService = stationInspectionService;
    }


    public IStationInspection build(Equipment station) {

        List<InspectionItem> stationInspectionItems = stationInspectionService.getStationInspectionItems(station);
        List<StationEquipmentInspection> equipmentInspections = stationInspectionService.getStationEquipmentWithInspections(station);
        List<InspectionPhoto> commonPhotos = stationInspectionService.getStationCommonPhotos(station);

        if (station.getType().equals(EquipmentType.SUBSTATION)) {
            Substation substation = equipmentService.getSubstationById(station.getUniqId());
            InspectedSubstation substationInspection = new InspectedSubstation(substation, stationInspectionItems, equipmentInspections);
            substationInspection.setCommonPhotos(commonPhotos);
            return substationInspection;

        }

        if (station.getType().equals(EquipmentType.TP)) {
            TransformerSubstation transformerSubstation = equipmentService.getTransformerSubstationById(station.getUniqId());
            InspectedTransformerSubstation transformerStationInspection = new InspectedTransformerSubstation(transformerSubstation, stationInspectionItems, equipmentInspections);
            transformerStationInspection.setCommonPhotos(commonPhotos);

            return transformerStationInspection;

        }

        return null;
    }


}
