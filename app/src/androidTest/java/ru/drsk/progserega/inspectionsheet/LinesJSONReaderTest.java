package ru.drsk.progserega.inspectionsheet;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Line;
import ru.drsk.progserega.inspectionsheet.storages.LineStorageJSON;
import ru.drsk.progserega.inspectionsheet.storages.json.LineReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class LinesJSONReaderTest {

    @Test
    public void shouldReadJSON() throws IOException {
        Context appContext = InstrumentationRegistry.getTargetContext();

        LineReader lineReader = new LineReader();
        List<Line> lines =  lineReader.readLines(appContext.getResources().openRawResource(R.raw.lines));

        assertNotNull(lines);
        assertFalse(lines.isEmpty());
    }
}
