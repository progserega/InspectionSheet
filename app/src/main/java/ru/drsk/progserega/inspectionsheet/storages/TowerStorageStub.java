package ru.drsk.progserega.inspectionsheet.storages;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Tower;
import ru.drsk.progserega.inspectionsheet.services.TowersService;

public class TowerStorageStub implements ITowerStorage {

    @Override
    public List<Tower> getByLineId(int lineId) {

        return null;
    }
}
