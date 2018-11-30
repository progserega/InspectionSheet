package ru.drsk.progserega.inspectionsheet;


import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.SteTPResponse;
import ru.drsk.progserega.inspectionsheet.storages.json.TPReader;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TPJSONReaderTest {

    @Test
    public void shouldReadTPJSON() throws IOException {
        Context appContext = InstrumentationRegistry.getTargetContext();

        TPReader tpReader = new TPReader();
        SteTPResponse response = tpReader.readTP(appContext.getResources().openRawResource(R.raw.tp));

        assertNotNull(response);
        assertFalse(response.getData().isEmpty());

    }
}
