package ru.drsk.progserega.inspectionsheet.entities.inspections;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;

public interface IStationInspection {


    List<StationEquipmentInspection> getStationEquipmentInspections();

    Equipment getStation();

    List<InspectionItem> getStationInspectionItems();

    List<InspectionPhoto> getCommonPhotos();

    float getInspectionPercent();
 }
