package ru.drsk.progserega.inspectionsheet.services;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;

public interface IStationInspectionService {

    List<InspectionItem> getStationInspectionItems(Equipment station);

}
