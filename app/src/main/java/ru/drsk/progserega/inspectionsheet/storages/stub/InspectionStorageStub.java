package ru.drsk.progserega.inspectionsheet.storages.stub;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.entities.inspections.StationEquipmentInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TransformerInspection;
import ru.drsk.progserega.inspectionsheet.storages.IInspectionStorage;

public class InspectionStorageStub implements IInspectionStorage {
    @Override
    public void saveInspection(TransformerInspection inspection) {

    }

    @Override
    public void updateSubstationInspectionInfo(TransformerInspection inspection) {

    }

    @Override
    public void loadInspections(TransformerInspection inspection) {

    }

    @Override
    public void loadStationInspections(long stationUniqId, List<InspectionItem> inspectionItems) {

    }

    @Override
    public void loadInspections(StationEquipmentInspection inspection) {

    }

    @Override
    public void saveInspection(StationEquipmentInspection inspection) {

    }
}
