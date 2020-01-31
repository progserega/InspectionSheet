package ru.drsk.progserega.inspectionsheet.storages;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;

public interface ITransformerDeffectTypesStorage {

    List< InspectionItem > getSubstationDeffects();
    List< InspectionItem > getTPDeffects();

}
