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
}
