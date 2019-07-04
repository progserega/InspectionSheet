package ru.drsk.progserega.inspectionsheet.storages;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.inspections.LineTowerDeffectType;

public interface ILineTowerDeffectTypesStorage {

    public List<LineTowerDeffectType> load();
}
