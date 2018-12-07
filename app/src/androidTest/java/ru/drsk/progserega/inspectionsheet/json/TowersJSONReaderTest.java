package ru.drsk.progserega.inspectionsheet.json;


import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.Line;
import ru.drsk.progserega.inspectionsheet.entities.Tower;
import ru.drsk.progserega.inspectionsheet.storages.json.LineReader;
import ru.drsk.progserega.inspectionsheet.storages.json.TowerReader;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TowersJSONReaderTest {

    @Test
    public void shouldReadJSON() throws IOException {
        Context appContext = InstrumentationRegistry.getTargetContext();

        TowerReader towerReader = new TowerReader();
        List<Tower> towers =  towerReader.readTowers(appContext.getResources().openRawResource(R.raw.towers));

        assertNotNull(towers);
        assertFalse(towers.isEmpty());
        assertNotNull(towers.get(0).getMapPoint());
        assertTrue(towers.get(0).getMapPoint().getLat() > 0);
        assertTrue(towers.get(0).getMapPoint().getLon() > 0);
    }
}
