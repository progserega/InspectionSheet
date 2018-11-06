package ru.drsk.progserega.inspectionsheet;

import android.util.Log;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Line;
import ru.drsk.progserega.inspectionsheet.entities.Voltage;
import ru.drsk.progserega.inspectionsheet.services.LinesService;
import ru.drsk.progserega.inspectionsheet.storages.ILineStorage;
import ru.drsk.progserega.inspectionsheet.storages.LineStorageStub;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class LinesServiceTest {

    @Test
    public void getLinesByType04kv() {

        ILineStorage lineStorage = new LineStorageStub();

        LinesService linesService = new LinesService(lineStorage);

        ArrayList<Line> lines = linesService.getLinesByType(Voltage.VL_04KV);
        assertNotEquals(lines, null);
        assertFalse(lines.isEmpty());

       assertEquals(lines.get(0).getVoltage().name(), Voltage.VL_04KV.name());

    }

    @Test
    public void getLinesByType6_10kv() {

        ILineStorage lineStorage = new LineStorageStub();

        LinesService linesService = new LinesService(lineStorage);

        ArrayList<Line> lines = linesService.getLinesByType(Voltage.VL_6_10KV);
        assertNotEquals(lines, null);
        assertFalse(lines.isEmpty());
        assertEquals(lines.get(0).getVoltage().name(), Voltage.VL_6_10KV.name());
    }

    @Test
    public void getLinesByType35_110kv() {

        ILineStorage lineStorage = new LineStorageStub();

        LinesService linesService = new LinesService(lineStorage);

        ArrayList<Line> lines = linesService.getLinesByType(Voltage.VL_35_110KV);
        assertNotEquals(lines, null);
        assertFalse(lines.isEmpty());
        assertEquals(lines.get(0).getVoltage().name(), Voltage.VL_35_110KV.name());
    }

}
