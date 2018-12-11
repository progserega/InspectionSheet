package ru.drsk.progserega.inspectionsheet.storages.sqlight;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ru.drsk.progserega.inspectionsheet.entities.Line;
import ru.drsk.progserega.inspectionsheet.entities.Voltage;
import ru.drsk.progserega.inspectionsheet.storages.ILineStorage;

public class LineStorage implements ILineStorage {

    @Override
    public List<Line> getByFilters(Map<String, Object> filters) {
        return null;
    }

    @Override
    public Line getById(long id) {
        return null;
    }

//    @Override
//    public ArrayList<Line> getLinesByType(Voltage voltage) {
//        return null;
//    }
//
//    @Override
//    public ArrayList<Line> getLinesByTypeAndName(Voltage voltage, String name) {
//        return null;
//    }
}
