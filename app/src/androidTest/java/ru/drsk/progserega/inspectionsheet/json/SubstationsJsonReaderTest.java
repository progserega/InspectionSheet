package ru.drsk.progserega.inspectionsheet.json;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.GeoSubstationsResponse;
import ru.drsk.progserega.inspectionsheet.storages.json.SubstationReader;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;


@RunWith(AndroidJUnit4.class)
public class SubstationsJsonReaderTest {

    @Test
    public void shouldReadSubstationJSON() throws IOException {
        Context appContext = InstrumentationRegistry.getTargetContext();

        SubstationReader reader = new SubstationReader();
        GeoSubstationsResponse response = reader.readSubstations(appContext.getResources().openRawResource(R.raw.substations));

        assertNotNull(response);
        assertFalse(response.getData().isEmpty());

    }
}
