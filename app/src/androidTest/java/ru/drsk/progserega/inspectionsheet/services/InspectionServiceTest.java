package ru.drsk.progserega.inspectionsheet.services;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;
import ru.drsk.progserega.inspectionsheet.entities.Substation;
import ru.drsk.progserega.inspectionsheet.entities.Transformer;
import ru.drsk.progserega.inspectionsheet.entities.TransformerInSlot;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TransformerInspection;
import ru.drsk.progserega.inspectionsheet.storages.ITransformerStorage;
import ru.drsk.progserega.inspectionsheet.storages.json.TransfInspectionListReader;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.InspectionSheetDatabase;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.InspectionStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.TransformerStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.TransformerDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.TransformerSubstationDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.TransformerSubstationEquipmentDao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class InspectionServiceTest {

    private InspectionSheetDatabase mDb;
    private Context context;
    private TransformerDao transformerDao;
    private TransformerSubstationDao transformerSubstationDao;
    private TransformerSubstationEquipmentDao transformerSubstationEquipmentDao;

    @Before
    public void createDb() {
        context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, InspectionSheetDatabase.class).build();
        transformerDao = mDb.transformerDao();
        transformerSubstationDao = mDb.transformerSubstationDao();
        transformerSubstationEquipmentDao = mDb.transfSubstationEquipmentDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void shouldLoadInspectionsForEquipmentType() throws IOException {

        InspectionStorage storage = new InspectionStorage(mDb, context);
        ITransformerStorage transformerStorage = new TransformerStorage(mDb);

        Equipment substation = new Substation(1, "Substation 1", null, 0);
        TransformerInSlot transformer = new TransformerInSlot(1, 1, new Transformer( 1, "Transformerr name"));
        TransformerInspection transformerInspection = new TransformerInspection(substation, transformer);
        TransfInspectionListReader inspectionListReader = new TransfInspectionListReader();
        transformerInspection.setInspectionItems(inspectionListReader.readInspections(context.getResources().openRawResource(R.raw.transormator_inspection_list)));
        transformerInspection.getSubstation().setInspectionPercent(10);

        storage.saveInspection(transformerInspection);
        assertTrue(transformerInspection.getInspectionItems().get(1).getId() > 0);


        InspectionService inspectionService = new InspectionService(mDb, transformerStorage, storage, context );
        List<TransformerInspection> inspections = inspectionService.getInspectionByEquipment(EquipmentType.SUBSTATION);

        assertFalse(inspections == null);
        assertFalse(inspections.isEmpty());

    }
}
