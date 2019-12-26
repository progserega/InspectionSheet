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










}
