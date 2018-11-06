package ru.drsk.progserega.inspectionsheet.services;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Line;
import ru.drsk.progserega.inspectionsheet.entities.Voltage;
import ru.drsk.progserega.inspectionsheet.storages.ILineStorage;

public class LinesService {

    private ILineStorage lineStorage;

    public LinesService(ILineStorage lineStorage){
        this.lineStorage = lineStorage;
    }

    public ArrayList<Line> getLinesByType(Voltage type){
        return lineStorage.getLinesByType(type);
    };
}
