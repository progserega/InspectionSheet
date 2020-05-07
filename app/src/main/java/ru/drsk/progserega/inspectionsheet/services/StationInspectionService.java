package ru.drsk.progserega.inspectionsheet.services;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.storages.IInspectionStorage;
import ru.drsk.progserega.inspectionsheet.storages.IStationDeffectTypesStorage;

public class StationInspectionService implements IStationInspectionService {

    private IStationDeffectTypesStorage deffectTypesStorage;
    private IInspectionStorage inspectionStorage;


    public StationInspectionService(IStationDeffectTypesStorage deffectTypesStorage, IInspectionStorage inspectionStorage) {
        this.deffectTypesStorage = deffectTypesStorage;
        this.inspectionStorage = inspectionStorage;
    }

    @Override
    public List<InspectionItem> getStationInspectionItems(Equipment station) {
        List<InspectionItem> templates = deffectTypesStorage.getDeffectTypes(station.getType());
        inspectionStorage.loadStationInspections(station.getUniqId(), templates);

        return templates;
    }
}
