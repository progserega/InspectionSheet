package ru.drsk.progserega.inspectionsheet.storages;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ru.drsk.progserega.inspectionsheet.entities.Line;
import ru.drsk.progserega.inspectionsheet.entities.Tower;
import ru.drsk.progserega.inspectionsheet.entities.Voltage;

public interface ILineStorage {

    List<Line> getByFilters(Map<String, Object> filters);

    Line getById(long id);

    void updateStartExploitationYear(long lineId, int year);
}
