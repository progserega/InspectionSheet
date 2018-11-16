package ru.drsk.progserega.inspectionsheet;

import org.junit.Test;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.LineTower;
import ru.drsk.progserega.inspectionsheet.services.TowersService;
import ru.drsk.progserega.inspectionsheet.storages.ILineStorage;
import ru.drsk.progserega.inspectionsheet.storages.ITowerStorage;
import ru.drsk.progserega.inspectionsheet.storages.stub.LineStorageStub;
import ru.drsk.progserega.inspectionsheet.storages.stub.TowerStorageStub;

import static org.junit.Assert.assertFalse;
import static ru.drsk.progserega.inspectionsheet.storages.stub.LineStorageStub.initLinesWithTowersStub;

public class TowersServiceTest {

    @Test
    public void shouldFindAllLineTowers() {

        LineStorageStub lineStorage = new LineStorageStub(null);
        lineStorage.setLines(initLinesWithTowersStub());
        ITowerStorage towerStorage = new TowerStorageStub();
        TowersService towersService = new TowersService(towerStorage, (ILineStorage) lineStorage);

        List<LineTower> towers = towersService.getTowersByLine(1);

        assertFalse(towers.isEmpty());
    }



}
