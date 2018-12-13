package ru.drsk.progserega.inspectionsheet.storages;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ru.drsk.progserega.inspectionsheet.entities.Line;
import ru.drsk.progserega.inspectionsheet.entities.Voltage;

public interface ILineStorage {

    public List<Line> getByFilters(Map<String, Object> filters);

    public Line getById(long id);

}