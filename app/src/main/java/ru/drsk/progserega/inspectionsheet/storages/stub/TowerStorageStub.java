package ru.drsk.progserega.inspectionsheet.storages.stub;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Tower;
import ru.drsk.progserega.inspectionsheet.services.TowersService;
import ru.drsk.progserega.inspectionsheet.storages.ITowerStorage;

public class TowerStorageStub implements ITowerStorage {

    @Override
    public List<Tower> getByLineId(int lineId) {

        return null;
    }

    @Override
    public List<Tower> getByLineUniqId(long id) {
        return null;
    }

    @Override
    public Tower getFirstInLine(long lineUniqId) {
        return null;
    }

    @Override
    public Tower getByNumberInLine(String number, long lineUniqId) {
        return null;
    }

    @Override
    public void update(Tower tower) {

    }
}
