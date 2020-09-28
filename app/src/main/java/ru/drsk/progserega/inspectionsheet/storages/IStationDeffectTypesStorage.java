package ru.drsk.progserega.inspectionsheet.storages;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;

/**
 * Хранилище пунктов листа осмотра (типы деффектов)
 */
public interface IStationDeffectTypesStorage {

    List< InspectionItem > getDeffectTypes(EquipmentType equipmentType);

}
