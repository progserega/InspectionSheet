package ru.drsk.progserega.inspectionsheet.json;


import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.LineTower;
import ru.drsk.progserega.inspectionsheet.entities.Tower;
import ru.drsk.progserega.inspectionsheet.storages.json.LineTowerReader;
import ru.drsk.progserega.inspectionsheet.storages.json.TowerReader;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class LineTowersJSONReaderTest {

    @Test
    public void shouldReadJSON() throws IOException {
        Context appContext = InstrumentationRegistry.getTargetContext();

        LineTowerReader towerReader = new LineTowerReader();
        List<LineTower> towers =  towerReader.readLineTowers(appContext.getResources().openRawResource(R.raw.line_towers));

        assertNotNull(towers);
        assertFalse(towers.isEmpty());
    }
}
