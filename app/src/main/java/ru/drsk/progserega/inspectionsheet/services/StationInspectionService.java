package ru.drsk.progserega.inspectionsheet.services;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;
import ru.drsk.progserega.inspectionsheet.entities.Transformer;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionPhoto;
import ru.drsk.progserega.inspectionsheet.entities.inspections.StationEquipmentInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TransformerInspection;
import ru.drsk.progserega.inspectionsheet.storages.IInspectionStorage;
import ru.drsk.progserega.inspectionsheet.storages.IStationDeffectTypesStorage;
import ru.drsk.progserega.inspectionsheet.storages.IStationEquipmentStorage;
import ru.drsk.progserega.inspectionsheet.storages.IStationPhotoStorage;

public class StationInspectionService implements IStationInspectionService {

    private IStationDeffectTypesStorage deffectTypesStorage;
    private IInspectionStorage inspectionStorage;
    private IStationEquipmentStorage equipmentStorage;
private IStationPhotoStorage stationPhotoStorage;

    public StationInspectionService(IStationDeffectTypesStorage deffectTypesStorage, IInspectionStorage inspectionStorage, IStationEquipmentStorage equipmentStorage, IStationPhotoStorage stationPhotoStorage) {
        this.deffectTypesStorage = deffectTypesStorage;
        this.inspectionStorage = inspectionStorage;
        this.equipmentStorage = equipmentStorage;
        this.stationPhotoStorage = stationPhotoStorage;
    }

    @Override
    public List<InspectionItem> getStationInspectionItems(Equipment station) {
        List<InspectionItem> templates = deffectTypesStorage.getDeffectTypes(station.getType());
        inspectionStorage.loadStationInspections(station.getUniqId(), templates);

        return templates;
    }

    @Override
    public List<StationEquipmentInspection> getStationEquipmentWithInspections(Equipment station) {
        List<Equipment> stationEquipments = equipmentStorage.getStationEquipment(station);

        return buildInspectionsList(station, stationEquipments);
    }

    @Override
    public List<InspectionPhoto> getStationCommonPhotos(Equipment station) {
        return  stationPhotoStorage.loadStationCommonPhotos(station);
    }

    private List<StationEquipmentInspection> buildInspectionsList(Equipment station, List<Equipment> equipments) {
        List<StationEquipmentInspection> inspectionList = new ArrayList<>();

        for (Equipment equipment : equipments) {

            StationEquipmentInspection inspection = new StationEquipmentInspection(station, equipment);
            inspection.setInspectionItems(loadInspectionTemplates(equipment.getType()));
            inspectionList.add(inspection);

            //Загрузка значений из бд
            inspectionStorage.loadInspections(inspection);
        }

        return inspectionList;
    }

    public List<InspectionItem> loadInspectionTemplates(EquipmentType equipmentType) {

        List<InspectionItem> template  = deffectTypesStorage.getDeffectTypes(equipmentType);

        return template;
    }


}
