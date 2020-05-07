package ru.drsk.progserega.inspectionsheet.storages;

import android.content.Context;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TransformerInspection;

public interface IInspectionStorage {

    void saveInspection(TransformerInspection inspection);

    void updateSubstationInspectionInfo(TransformerInspection inspection);

    void loadInspections(TransformerInspection inspection);

    void loadStationInspections(long stationUniqId, List<InspectionItem> inspectionItems);
}
