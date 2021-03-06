package ru.drsk.progserega.inspectionsheet.storages;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.LineSection;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.LineSectionModel;

public interface ILineSectionStorage {

    List<LineSection> getByLine(long lineUniqId);

    List<LineSection> getByLineStartWithTower(long lineUniqId, long towerUniqId);

    List<LineSection> getByLineEndWithTower(long lineUniqId, long towerUniqId);

    LineSection getById(long id);

    List<LineSection> getByIds(Long[] ids);

    void update(LineSection lineSection);
}
