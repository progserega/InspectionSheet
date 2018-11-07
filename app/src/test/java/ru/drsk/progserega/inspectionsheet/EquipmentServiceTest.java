package ru.drsk.progserega.inspectionsheet;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;
import ru.drsk.progserega.inspectionsheet.entities.Line;
import ru.drsk.progserega.inspectionsheet.entities.LineTower;
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
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class EquipmentServiceTest {


    @Test
    public void shouldFindLinesByTowersCoordinatesInsideRadius() {

        ILocation loc = new LocationServiceStub();
        LineStorageStub lineStorage = new LineStorageStub(loc);
        lineStorage.setLines(initLinesWithTowersStub());

        EquipmentService equipmentService = new EquipmentService(lineStorage, loc);
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

    private ArrayList<Line> initLinesWithTowersStub() {
        ArrayList<Line> lines = new ArrayList<>();

        Tower t1 = new Tower(1, new Point(1, 1));
        Tower t2 = new Tower(2, new Point(2, 1));
        Tower t3 = new Tower(3, new Point(2, 2));
        Tower t4 = new Tower(4, new Point(1, 2));
        Tower t5 = new Tower(5, new Point(1, 3));
        Tower t6 = new Tower(6, new Point(1, 4));
        Tower t7 = new Tower(7, new Point(4, 4));
        Tower t8 = new Tower(8, new Point(5, 1));
        Tower t9 = new Tower(9, new Point(5, 5));
        Tower t10 = new Tower(10, new Point(2, 5));

        Line l1 = new Line(1, "line 1", Voltage.VL_04KV, null);
        Line l2 = new Line(2, "line 2", Voltage.VL_04KV, null);
        Line l3 = new Line(3, "line 3", Voltage.VL_04KV, null);

        List<LineTower> l1Towers = new ArrayList<>();
        l1Towers.add(new LineTower(l1, t1, "1-1"));
        l1Towers.add(new LineTower(l1, t2, "1-2"));
        l1Towers.add(new LineTower(l1, t8, "1-3"));
        l1.setTowers(l1Towers);

        List<LineTower> l2Towers = new ArrayList<>();
        l2.setTowers(l2Towers);
        l2Towers.add(new LineTower(l2, t2, "2-1"));
        l2Towers.add(new LineTower(l2, t3, "2-2"));
        l2Towers.add(new LineTower(l2, t7, "2-3"));
        l2Towers.add(new LineTower(l2, t9, "2-4"));


        List<LineTower> l3Towers = new ArrayList<>();
        l3.setTowers(l3Towers);
        l3Towers.add(new LineTower(l3, t3, "3-1"));
        l3Towers.add(new LineTower(l3, t4, "3-2"));
        l3Towers.add(new LineTower(l3, t5, "3-3"));
        l3Towers.add(new LineTower(l3, t6, "3-4"));
        l3Towers.add(new LineTower(l3, t10, "3-5"));


        lines.add(l1);
        lines.add(l2);
        lines.add(l3);


        return lines;
    }

    @Test
    public void shouldContainFilters() {

        LineStorageStub lineStorage = new LineStorageStub(null);
        lineStorage.setLines(initLinesWithTowersStub());


        EquipmentService equipmentService = new EquipmentService(lineStorage, null);

        assertNotNull(equipmentService.getFilters());

    }

    @Test
    public void canAddFilter() {
        EquipmentService equipmentService = new EquipmentService(null, null);

        equipmentService.addFilter(EquipmentService.FILTER_TYPE, EquipmentType.LINE);

        assertFalse(equipmentService.getFilters().isEmpty());

    }

    @Test
    public void canRemoveFilter() {
        EquipmentService equipmentService = new EquipmentService(null, null);

        equipmentService.addFilter(EquipmentService.FILTER_TYPE, EquipmentType.LINE);
        equipmentService.removeFilter(EquipmentService.FILTER_TYPE);
        equipmentService.removeFilter(EquipmentService.FILTER_TYPE);

        assertTrue(equipmentService.getFilters().isEmpty());
    }

    @Test
    public void shouldAcceptFiltersType() {
        ILocation loc = new LocationServiceStub();
        LineStorageStub lineStorage = new LineStorageStub(loc);

        EquipmentService equipmentService = new EquipmentService(lineStorage, loc);

        equipmentService.addFilter(EquipmentService.FILTER_TYPE, EquipmentType.LINE);
        List<Equipment> equipments = equipmentService.getEquipments();
        assertFalse(equipments.isEmpty());
    }

    @Test
    public void shouldAcceptFiltersTypeVoltage() {
        ILocation loc = new LocationServiceStub();
        LineStorageStub lineStorage = new LineStorageStub(loc);
        EquipmentService equipmentService = new EquipmentService(lineStorage, loc);

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
        EquipmentService equipmentService = new EquipmentService(lineStorage, loc);

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
        EquipmentService equipmentService = new EquipmentService(lineStorage, loc);

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
