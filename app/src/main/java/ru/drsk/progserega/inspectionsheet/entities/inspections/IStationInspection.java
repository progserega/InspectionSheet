package ru.drsk.progserega.inspectionsheet.entities.inspections;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;

public interface IStationInspection {


    List<TransformerInspection> getTransformerInspections();

    Equipment getEquipment();

    List<InspectionItem> getStationInspectionItems();

    List<InspectionPhoto> getCommonPhotos();
 }
