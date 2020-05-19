package ru.drsk.progserega.inspectionsheet.storages;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Equipment;

public interface IStationEquipmentStorage {

    List<Equipment> getStationEquipment(Equipment station);
}
