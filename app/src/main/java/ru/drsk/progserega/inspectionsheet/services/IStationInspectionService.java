package ru.drsk.progserega.inspectionsheet.services;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;
import ru.drsk.progserega.inspectionsheet.entities.inspections.StationEquipmentInspection;

public interface IStationInspectionService {

    List<InspectionItem> getStationInspectionItems(Equipment station);

    List<StationEquipmentInspection> getStationEquipmentWithInspections(Equipment station);

    List<InspectionPhoto> getStationCommonPhotos(Equipment station);
}
