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

import ru.drsk.progserega.inspectionsheet.storages.sqlight.InspectionSheetDatabase;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.SubstationDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.SubstationEquipmentDao;
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
    private SubstationDao substationDao;
    private SubstationEquipmentDao substationEquipmentDao;

    @Before
    public void createDb() {
        context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, InspectionSheetDatabase.class).build();
        transformerDao = mDb.transformerDao();
        transformerSubstationDao = mDb.transformerSubstationDao();
        transformerSubstationEquipmentDao = mDb.transfSubstationEquipmentDao();
        substationDao = mDb.substationDao();
        substationEquipmentDao = mDb.substationEquipmentDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void shouldLoadInspectionsForEquipmentType() throws IOException {

//        InspectionStorage storage = new InspectionStorage(mDb, context);
//        ITransformerStorage transformerStorage = new TransformerStorage(mDb, context);
//
//        Equipment substation = new Substation(1, "Substation 1", null, 0);
//        substationDao.insert(new SubstationModel(1, "Substation 1", "", "", 0, 0, 0, 0));
//
//        transformerDao.insert(new TransformerModel(1, "type", "desc", "substation"));
//        substationEquipmentDao.insert(new SubstationEquipmentModel(1, 1, 1, 1, 0));
//
//        //Добавить в БД трансформаторы
//        TransformerInSlot transformer = new TransformerInSlot(1, 1, new TransformerType(1, "Transformerr name"));
//        TransformerInspection transformerInspection = new TransformerInspection(substation, transformer);
//        TransfInspectionListReader inspectionListReader = new TransfInspectionListReader();
//        transformerInspection.setInspectionItems(inspectionListReader.readInspections(context.getResources().openRawResource(R.raw.substation_transormer_deffect_types)));
//        transformerInspection.getSubstation().setInspectionPercent(10);
//
//        storage.saveInspection(transformerInspection);
//        assertTrue(transformerInspection.getInspectionItems().get(1).getId() > 0);
//
//
//        InspectionService inspectionService = new InspectionService(mDb, transformerStorage, storage, context);
//        List<TransformerInspection> inspections = inspectionService.getInspectionByEquipment(EquipmentType.SUBSTATION);
//
//        assertFalse(inspections == null);
//        assertFalse(inspections.isEmpty());

    }
}
