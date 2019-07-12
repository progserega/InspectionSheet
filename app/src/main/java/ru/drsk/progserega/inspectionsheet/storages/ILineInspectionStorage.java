package ru.drsk.progserega.inspectionsheet.storages;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Tower;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineSectionDeffect;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineSectionInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TowerDeffect;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TowerInspection;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TowerDeffectModel;

public interface ILineInspectionStorage {

    List<TowerDeffect> getTowerDeffects(long towerUniqId);

    TowerInspection getTowerInspection(long towerUniqId);

    Long addTowerDeffect(TowerDeffect deffect);

    void updateTowerDeffect(TowerDeffect deffect);

    void saveInspection(TowerInspection inspection);

    List<LineSectionDeffect> getSectionDeffects(long id);

    Long addSectionDeffect(LineSectionDeffect deffect);

    void updateSectionDeffect(LineSectionDeffect deffect);

    LineSectionInspection getSectionInspection(long sectionId);

    void saveSectionInspection(LineSectionInspection inspection);
}
