package ru.drsk.progserega.inspectionsheet.storages;

import java.util.Date;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Equipment;

public interface IStationEquipmentStorage {

    List<Equipment> getStationEquipment(Equipment station);

    void updateManufactureYear(int year, long equipmentId);

    void updateInspectionDate(Date date, long equipmentId);

}
