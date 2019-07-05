package ru.drsk.progserega.inspectionsheet.storages;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.inspections.TowerDeffect;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TowerDeffectModel;

public interface ILineInspectionStorage {

    List<TowerDeffect> getTowerDeffects(long towerUniqId);

    Long addTowerDeffect(TowerDeffect deffect);

    void updateTowerDeffect(TowerDeffect deffect);
}
