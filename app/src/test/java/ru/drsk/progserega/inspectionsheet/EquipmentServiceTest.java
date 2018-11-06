package ru.drsk.progserega.inspectionsheet;

import android.util.Log;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;
import ru.drsk.progserega.inspectionsheet.entities.Line;
import ru.drsk.progserega.inspectionsheet.entities.Point;
import ru.drsk.progserega.inspectionsheet.entities.Tower;
import ru.drsk.progserega.inspectionsheet.entities.Voltage;
import ru.drsk.progserega.inspectionsheet.services.EquipmentService;
import ru.drsk.progserega.inspectionsheet.services.ILocation;
import ru.drsk.progserega.inspectionsheet.services.LocationServiceStub;
import ru.drsk.progserega.inspectionsheet.storages.ILineStorage;
import ru.drsk.progserega.inspectionsheet.storages.LineStorageStub;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class EquipmentServiceTest {

    @Test
    public void shouldFindLinesByTypeAndNamePart(){

        ILineStorage lineStorage = new LineStorageStub();
        ILocation loc = new LocationServiceStub();

        EquipmentService equipmentService = new EquipmentService(lineStorage, loc);

        List<Equipment> equipments = equipmentService.getByTypeAndName(EquipmentType.LINE, Voltage.VL_04KV, "test");
        assertFalse(equipments.isEmpty());
        assertEquals(equipments.size(), 6);
    }

    @Test
    public void shouldFindLinesByTowersCoordinatesInsideRadius(){

        LineStorageStub lineStorage = new LineStorageStub();
        lineStorage.setLines( initLinesWithTowersStub() );


        ILocation loc = new LocationServiceStub();

        EquipmentService equipmentService = new EquipmentService(lineStorage, loc);
        List<Equipment> equipments = equipmentService.getByTypeAndPoint(EquipmentType.LINE, new Point(1,1));

        assertNotNull(equipments);

        assertFalse(equipments.isEmpty());
    }

    private ArrayList<Line> initLinesWithTowersStub(){
        ArrayList<Line> lines = new ArrayList<>();

        //Tower

        return lines;
    }


}
