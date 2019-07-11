package ru.drsk.progserega.inspectionsheet.storages;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.inspections.LineDeffectType;

public interface ILineDeffectTypesStorage {

    List<LineDeffectType> loadTowerDeffects();

    LineDeffectType getTowerDeffectById(long id);

    List<LineDeffectType> loadSectionDeffects();

    LineDeffectType getSectionDeffectById(long id);

}
