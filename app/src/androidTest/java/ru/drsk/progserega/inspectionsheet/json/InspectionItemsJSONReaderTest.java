package ru.drsk.progserega.inspectionsheet.json;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.Transformer;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.storages.json.TransfInspectionListReader;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class InspectionItemsJSONReaderTest {

    @Test
    public void shouldReadInspectionsJSON() throws IOException {
        Context appContext = InstrumentationRegistry.getTargetContext();

        TransfInspectionListReader reader = new TransfInspectionListReader();
        List<InspectionItem> response = reader.readInspections(appContext.getResources().openRawResource(R.raw.transormator_inspection_list));


        assertNotNull(response);
        assertFalse(response.isEmpty());

    }
}
