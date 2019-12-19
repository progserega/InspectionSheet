package ru.drsk.progserega.inspectionsheet.storages;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Line;
import ru.drsk.progserega.inspectionsheet.entities.Tower;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineSectionDeffect;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineSectionInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TowerDeffect;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TowerInspection;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TowerDeffectModel;

//TODO отрефакторить. разбить на отдельные интерфейсы
public interface ILineInspectionStorage {

    List< TowerDeffect > getTowerDeffects(long towerUniqId, Line line);

    TowerInspection getTowerInspection(long towerUniqId);

    List<TowerInspection> getTowerInspectionByLine(long lineUniqId);


    Long addTowerDeffect(TowerDeffect deffect);

    void updateTowerDeffect(TowerDeffect deffect);

    void saveToweInspection(TowerInspection inspection);

    List< LineSectionDeffect > getSectionDeffects(long id,  Line line);

    Long addSectionDeffect(LineSectionDeffect deffect);

    void updateSectionDeffect(LineSectionDeffect deffect);

    LineSectionInspection getSectionInspection(long sectionId);

    void saveSectionInspection(LineSectionInspection inspection);

    List<LineSectionInspection> getSectionInspectionByLine(long lineUniqId);

    LineInspection getLineInspection(long lineId);

    List<LineInspection> getAllLineInspections();

    long saveLineInspection(LineInspection inspection);

}
