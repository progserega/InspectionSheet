package ru.drsk.progserega.inspectionsheet.json;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.storages.json.SubstationTransformersReader;
import ru.drsk.progserega.inspectionsheet.storages.json.TransfInspectionListReader;
import ru.drsk.progserega.inspectionsheet.storages.json.models.SubstationTransformerJson;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;


@RunWith(AndroidJUnit4.class)
public class SubstationTransformersJSONreaderTest {

    @Test
    public void shouldReadTransformersJSON() throws IOException {
        Context appContext = InstrumentationRegistry.getTargetContext();

        SubstationTransformersReader reader = new SubstationTransformersReader();
        List<SubstationTransformerJson> response = reader.readSubstationTransformers(appContext.getResources().openRawResource(R.raw.substation_transformers));


        assertNotNull(response);
        assertFalse(response.isEmpty());

    }
}