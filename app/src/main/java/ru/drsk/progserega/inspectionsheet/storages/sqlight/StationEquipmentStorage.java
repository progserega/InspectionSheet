package ru.drsk.progserega.inspectionsheet.storages.sqlight;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.EquipmentModel;
import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;
import ru.drsk.progserega.inspectionsheet.entities.OtherStationEquipment;
import ru.drsk.progserega.inspectionsheet.entities.Transformer;
import ru.drsk.progserega.inspectionsheet.storages.IStationEquipmentStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.EquipmentInsideStaionModel;

public class StationEquipmentStorage implements IStationEquipmentStorage {
    private InspectionSheetDatabase db;

    public StationEquipmentStorage(InspectionSheetDatabase db) {
        this.db = db;
    }

    @Override
    public List<Equipment> getStationEquipment(Equipment station) {

        List<EquipmentInsideStaionModel> equipments = db.stationEquipmentDao().getByStation(station.getUniqId());

        List<Equipment> stationEquipments = new ArrayList<>();
        for (EquipmentInsideStaionModel equipmentInStation : equipments) {

            EquipmentType type = EquipmentType.fromInt((int) equipmentInStation.getEquipment().getTypeId());
            switch (type) {
                case SUBSTATION_TRANSFORMER:
                case TP_TRANSFORMER:
                    stationEquipments.add(new Transformer(
                            equipmentInStation.getEquipment().getId(),
                            equipmentInStation.getEquipment().getUniqId(),
                            equipmentInStation.getEquipment().getPlace(),
                            new EquipmentModel(equipmentInStation.getEquipment().getModelId(), equipmentInStation.getName()),
                            equipmentInStation.getEquipment().getManufactureYear(),
                            equipmentInStation.getEquipment().getInspectionDate(),
                            type
                    ));

                    break;
                default:
                    stationEquipments.add(new OtherStationEquipment(
                            equipmentInStation.getEquipment().getId(),
                            equipmentInStation.getEquipment().getUniqId(),
                            equipmentInStation.getName(),
                            type
                    ));
                    break;
            }


        }

        return stationEquipments;

    }

    @Override
    public void updateManufactureYear(int year, long equipmentId) {
        this.db.stationEquipmentDao().updateManufactureYear(year, equipmentId);
    }

    @Override
    public void updateInspectionDate(Date date, long equipmentId) {
        this.db.stationEquipmentDao().updateInspectionDate(date, equipmentId);

    }
}
