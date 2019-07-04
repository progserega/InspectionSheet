package ru.drsk.progserega.inspectionsheet.services;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Line;
import ru.drsk.progserega.inspectionsheet.entities.LineTower;
import ru.drsk.progserega.inspectionsheet.entities.Tower;
import ru.drsk.progserega.inspectionsheet.storages.ILineStorage;
import ru.drsk.progserega.inspectionsheet.storages.ITowerStorage;

public class TowersService {

    private ITowerStorage towerStorage;
    private ILineStorage  lineStorage;

    public TowersService(ITowerStorage towerStorage, ILineStorage lineStorage){
        this.towerStorage = towerStorage;
        this.lineStorage = lineStorage;

    }

//    public List<LineTower> getTowersByLine(long lineId){
//        Line line = lineStorage.getById(lineId);
//        if(line == null){
//            return new ArrayList<>();
//        }
//        return line.getTowers();
//    }
}
