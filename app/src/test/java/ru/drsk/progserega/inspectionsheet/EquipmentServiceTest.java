package ru.drsk.progserega.inspectionsheet;

import android.support.v4.app.NavUtils;

import org.junit.Test;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;
import ru.drsk.progserega.inspectionsheet.entities.Point;
import ru.drsk.progserega.inspectionsheet.entities.Voltage;
import ru.drsk.progserega.inspectionsheet.services.EquipmentService;
import ru.drsk.progserega.inspectionsheet.services.ILocation;
import ru.drsk.progserega.inspectionsheet.services.LocationServiceStub;
import ru.drsk.progserega.inspectionsheet.storages.stub.LineStorageStub;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static ru.drsk.progserega.inspectionsheet.storages.stub.LineStorageStub.initLinesWithTowersStub;

public class EquipmentServiceTest {


    @Test
    public void shouldFindLinesByTowersCoordinatesInsideRadius() {

        ILocation loc = new LocationServiceStub();
        LineStorageStub lineStorage = new LineStorageStub(loc);
        lineStorage.setLines(initLinesWithTowersStub());

        EquipmentService equipmentService = new EquipmentService(lineStorage, null, null);
        equipmentService.addFilter(EquipmentService.FILTER_TYPE, EquipmentType.LINE);
        equipmentService.addFilter(EquipmentService.FILTER_VOLTAGE, Voltage.VL_04KV);

        equipmentService.addFilter(EquipmentService.FILTER_POSITION, new Point(1, 1));
        equipmentService.addFilter(EquipmentService.FILTER_POSITION_RADIUS, 0.5f);
        List<Equipment> equipments = equipmentService.getEquipments();
        assertNotNull(equipments);
        assertFalse(equipments.isEmpty());
        assertEquals(equipments.size(), 1);

        equipmentService.addFilter(EquipmentService.FILTER_POSITION, new Point(1, 1));
        equipmentService.addFilter(EquipmentService.FILTER_POSITION_RADIUS, 1f);
        equipments = equipmentService.getEquipments();

        assertNotNull(equipments);
        assertFalse(equipments.isEmpty());
        assertEquals(equipments.size(), 3);

        equipmentService.addFilter(EquipmentService.FILTER_POSITION, new Point(10, 10));
        equipmentService.addFilter(EquipmentService.FILTER_POSITION_RADIUS, 1f);
        equipments = equipmentService.getEquipments();

        assertNotNull(equipments);
        assertTrue(equipments.isEmpty());

        equipmentService.addFilter(EquipmentService.FILTER_POSITION, new Point(2, 6));
        equipments = equipmentService.getEquipments();
        assertNotNull(equipments);
        assertFalse(equipments.isEmpty());
        assertEquals(equipments.size(), 1);
        assertEquals(equipments.get(0).getName(), "line 3");

        equipmentService.addFilter(EquipmentService.FILTER_POSITION, new Point(2, 6));
        equipmentService.addFilter(EquipmentService.FILTER_POSITION_RADIUS, 3f);
        equipments = equipmentService.getEquipments();

        assertNotNull(equipments);
        assertFalse(equipments.isEmpty());
        assertEquals(equipments.size(), 2);
        assertEquals(equipments.get(0).getName(), "line 2");
        assertEquals(equipments.get(1).getName(), "line 3");

    }



    @Test
    public void shouldContainFilters() {

        LineStorageStub lineStorage = new LineStorageStub(null);
        lineStorage.setLines(initLinesWithTowersStub());


        EquipmentService equipmentService = new EquipmentService(lineStorage, null, null);

        assertNotNull(equipmentService.getFilters());

    }

    @Test
    public void canAddFilter() {
        EquipmentService equipmentService = new EquipmentService(null, null, null);

        equipmentService.addFilter(EquipmentService.FILTER_TYPE, EquipmentType.LINE);

        assertFalse(equipmentService.getFilters().isEmpty());

    }

    @Test
    public void canRemoveFilter() {
        EquipmentService equipmentService = new EquipmentService(null, null, null);

        equipmentService.addFilter(EquipmentService.FILTER_TYPE, EquipmentType.LINE);
        equipmentService.removeFilter(EquipmentService.FILTER_TYPE);
        equipmentService.removeFilter(EquipmentService.FILTER_TYPE);

        assertTrue(equipmentService.getFilters().isEmpty());
    }

    @Test
    public void shouldAcceptFiltersType() {
        ILocation loc = new LocationServiceStub();
        LineStorageStub lineStorage = new LineStorageStub(loc);

        EquipmentService equipmentService = new EquipmentService(lineStorage, null, null);

        equipmentService.addFilter(EquipmentService.FILTER_TYPE, EquipmentType.LINE);
        List<Equipment> equipments = equipmentService.getEquipments();
        assertFalse(equipments.isEmpty());
    }

    @Test
    public void shouldAcceptFiltersTypeVoltage() {
        ILocation loc = new LocationServiceStub();
        LineStorageStub lineStorage = new LineStorageStub(loc);
        EquipmentService equipmentService = new EquipmentService(lineStorage, null, null);

        equipmentService.addFilter(EquipmentService.FILTER_TYPE, EquipmentType.LINE);
        equipmentService.addFilter(EquipmentService.FILTER_VOLTAGE, Voltage.VL_6_10KV);
        List<Equipment> equipments = equipmentService.getEquipments();
        assertFalse(equipments.isEmpty());
        assertEquals(equipments.size(), 6);

    }

    @Test
    public void shouldAcceptFiltersTypeVoltageName() {
        ILocation loc = new LocationServiceStub();
        LineStorageStub lineStorage = new LineStorageStub(loc);
        EquipmentService equipmentService = new EquipmentService(lineStorage, null, null);

        equipmentService.addFilter(EquipmentService.FILTER_TYPE, EquipmentType.LINE);
        equipmentService.addFilter(EquipmentService.FILTER_VOLTAGE, Voltage.VL_04KV);
        equipmentService.addFilter(EquipmentService.FILTER_NAME, "olo");
        List<Equipment> equipments = equipmentService.getEquipments();
        assertFalse(equipments.isEmpty());
        assertEquals(equipments.size(), 6);

    }

    @Test
    public void shouldAcceptFiltersTypeName() {
        ILocation loc = new LocationServiceStub();
        LineStorageStub lineStorage = new LineStorageStub(loc);
        EquipmentService equipmentService = new EquipmentService(lineStorage, null, null);

        equipmentService.addFilter(EquipmentService.FILTER_TYPE, EquipmentType.LINE);

        equipmentService.addFilter(EquipmentService.FILTER_NAME, "olo");
        List<Equipment> equipments = equipmentService.getEquipments();
        assertFalse(equipments.isEmpty());
        assertEquals(equipments.size(), 6);

        equipmentService.addFilter(EquipmentService.FILTER_NAME, "test");
        equipments = equipmentService.getEquipments();
        assertFalse(equipments.isEmpty());
        assertEquals(equipments.size(), 18);

    }


}
