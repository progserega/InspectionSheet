package ru.drsk.progserega.inspectionsheet.storages;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.inspections.LineDeffectType;

public interface ILineDeffectTypesStorage {

    List<LineDeffectType> loadTowerDeffects(int voltage);

    LineDeffectType getTowerDeffectById(long id, int voltage);

    List<LineDeffectType> loadSectionDeffects(int voltage);

    LineDeffectType getSectionDeffectById(long id, int voltage);

}
