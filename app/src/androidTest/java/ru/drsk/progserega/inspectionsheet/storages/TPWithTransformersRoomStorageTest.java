package ru.drsk.progserega.inspectionsheet.storages;

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

import ru.drsk.progserega.inspectionsheet.storages.sqlight.InspectionSheetDatabase;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.TransformerSubstationEquipmentDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.TransformerDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.TransformerSubstationDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TransformerInsideSubstaionModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TransformerSubstationEuipmentModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TransformerModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TransformerSubstationModel;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TPWithTransformersRoomStorageTest {


    private InspectionSheetDatabase mDb;
    private TransformerDao transformerDao;
    private TransformerSubstationDao transformerSubstationDao;
    private TransformerSubstationEquipmentDao transformerSubstationEquipmentDao;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
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
    public void shouldInsertAndSelectTransformerSubstation() {

        TransformerSubstationModel tp = initSimpleModel(111, "TP 1");
        long insertID = transformerSubstationDao.insert(tp);
        assertTrue(insertID == 1);

        List<TransformerSubstationModel> allTp = transformerSubstationDao.loadAllTp();

        assertFalse(allTp.isEmpty());
        assertTrue(allTp.size() == 1);
        assertTrue(allTp.get(0).getDispName().equals("TP 1"));
    }

    @Test
    public void shouldInsertAndSelectTransformerSubstationByUniqId() {

        TransformerSubstationModel tp = initSimpleModel(111, "TP 1");
        long insertID = transformerSubstationDao.insert(tp);
        assertTrue(insertID == 1);

        TransformerSubstationModel Tp = transformerSubstationDao.getByUniqId(111);

        assertFalse(Tp == null);
        assertTrue(Tp.getDispName().equals("TP 1"));
    }


    @Test
    public void shouldInsertAndSelectTransformers() {
        TransformerModel trans = new TransformerModel(1, "type", "descr 1", "s");
        long insertId = transformerDao.insert(trans);
        assertTrue(insertId == 1);

        List<TransformerModel> transformer = transformerDao.loadAllTransformers();
        assertFalse(transformer.isEmpty());
        assertTrue(transformer.size() == 1);
        assertTrue(transformer.get(0).getDesc().equals("descr 1"));

    }

    @Test
    public void shouldInsertAndSelectTransformersBySubstationId() {
        TransformerModel trans = new TransformerModel(1, "type", "descr 1", "s");
        long insertId = transformerDao.insert(trans);
        assertTrue(insertId == 1);

        TransformerModel trans2 = new TransformerModel(2, "type", "descr 2", "s");
        insertId = transformerDao.insert(trans2);
        assertTrue(insertId == 2);

        TransformerSubstationEuipmentModel tpTransformer = new TransformerSubstationEuipmentModel(0, 1, 1, 1);
        transformerSubstationEquipmentDao.insert(tpTransformer);
        TransformerSubstationEuipmentModel tpTransformer2 = new TransformerSubstationEuipmentModel(0, 1, 2, 2);
        transformerSubstationEquipmentDao.insert(tpTransformer2);
        List<TransformerModel> transformers = transformerSubstationEquipmentDao.getByTPId(1);
        assertFalse(transformers.isEmpty());
        assertTrue(transformers.size() == 2);
        assertTrue(transformers.get(1).getDesc().equals("descr 2"));

    }

    @Test
    public void shouldInsertAndSelectTransformersBySubstationIdWithEquipmentID() {
        TransformerModel trans = new TransformerModel(1, "type", "descr 1", "");
        long insertId = transformerDao.insert(trans);
        assertTrue(insertId == 1);

        TransformerModel trans2 = new TransformerModel(2, "type", "descr 2", "");
        insertId = transformerDao.insert(trans2);
        assertTrue(insertId == 2);

        TransformerSubstationEuipmentModel tpTransformer = new TransformerSubstationEuipmentModel(0, 1, 1, 1);
        transformerSubstationEquipmentDao.insert(tpTransformer);
        TransformerSubstationEuipmentModel tpTransformer2 = new TransformerSubstationEuipmentModel(0, 1, 2, 2);
        transformerSubstationEquipmentDao.insert(tpTransformer2);
        List<TransformerInsideSubstaionModel> transformers = transformerSubstationEquipmentDao.getBySubstation(1);
        assertFalse(transformers.isEmpty());
        assertTrue(transformers.size() == 2);
        assertTrue(transformers.get(1).getTransformer().getDesc().equals("descr 2"));

    }

    @Test
    public void shouldInsertTransformersWithDefinedId() {
        TransformerModel trans = new TransformerModel(255, "type", "descr 1", "");
        long insertId = transformerDao.insert(trans);
        assertTrue(insertId == 255);

    }


    private TransformerSubstationModel initSimpleModel(long uniqId, String name) {
        return new TransformerSubstationModel(
                0,
                uniqId,
                "center",
                name,
                1,
                1,
                0,
                0
        );
    }

}
