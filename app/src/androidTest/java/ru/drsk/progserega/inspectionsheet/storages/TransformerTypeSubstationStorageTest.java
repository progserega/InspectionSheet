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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;
import ru.drsk.progserega.inspectionsheet.entities.TransformerSubstation;
import ru.drsk.progserega.inspectionsheet.services.EquipmentService;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.InspectionSheetDatabase;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.TransformerSubstationStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.TransformerDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.TransformerSubstationDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TransformerSubstationModel;


@RunWith(AndroidJUnit4.class)
public class TransformerTypeSubstationStorageTest {


    private InspectionSheetDatabase mDb;
    private TransformerDao transformerDao;
    private TransformerSubstationDao transformerSubstationDao;


    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, InspectionSheetDatabase.class).build();
        transformerDao = mDb.transformerDao();
        transformerSubstationDao = mDb.transformerSubstationDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void shouldSelectAllSubstationWithoutFilters() {

        createSubstationsTetSet();

        ITransformerSubstationStorage substationStorage = new TransformerSubstationStorage(mDb, null);

        List<TransformerSubstation> substations = substationStorage.getByFilters(null);

        assertFalse(substations.isEmpty());
        assertTrue(substations.size() == 3);

    }

    @Test
    public void shouldSelectSubstationByFiltersALL() {

        createSubstationsTetSet();

        ITransformerSubstationStorage substationStorage = new TransformerSubstationStorage(mDb, null);

        Map<String, Object> filters = new HashMap<>();
        filters.put(EquipmentService.FILTER_TYPE, EquipmentType.TRANS_SUBSTATION);

        List<TransformerSubstation> substations = substationStorage.getByFilters(filters);

        assertFalse(substations.isEmpty());
        assertTrue(substations.size() == 3);

    }

    @Test
    public void shouldSelectSubstationByFiltersName() {

        createSubstationsTetSet();

        ITransformerSubstationStorage substationStorage = new TransformerSubstationStorage(mDb, null);

        Map<String, Object> filters = new HashMap<>();
        filters.put(EquipmentService.FILTER_TYPE, EquipmentType.TRANS_SUBSTATION);
        filters.put(EquipmentService.FILTER_NAME, "olo");

        List<TransformerSubstation> substations = substationStorage.getByFilters(filters);

        assertFalse(substations.isEmpty());
        assertTrue(substations.size() == 1);
        assertTrue(substations.get(0).getName().equals("ololo"));


    }

    @Test
    public void shouldSelectSubstationByFiltersSPandRES() {

        createSubstationsTetSet();

        ITransformerSubstationStorage substationStorage = new TransformerSubstationStorage(mDb, null);

        Map<String, Object> filters = new HashMap<>();
        filters.put(EquipmentService.FILTER_ENTERPRISE, 1);

        List<TransformerSubstation> substations = substationStorage.getByFilters(filters);

        assertFalse(substations.isEmpty());
        assertTrue(substations.size() == 2);
        assertTrue(substations.get(0).getName().equals("TP 1"));
        assertTrue(substations.get(1).getName().equals("ololo"));


        filters.put(EquipmentService.FILTER_AREA, 2);
        substations = substationStorage.getByFilters(filters);
        assertFalse(substations.isEmpty());
        assertTrue(substations.size() == 1);
        assertTrue(substations.get(0).getName().equals("TP 1"));

    }

    @Test
    public void shouldSelectSubstationByFiltersSPandRESandName() {

        createSubstationsTetSet();

        ITransformerSubstationStorage substationStorage = new TransformerSubstationStorage(mDb, null);

        Map<String, Object> filters = new HashMap<>();
        filters.put(EquipmentService.FILTER_ENTERPRISE, 1);
        filters.put(EquipmentService.FILTER_AREA, 3);
        filters.put(EquipmentService.FILTER_NAME, "olo");

        List<TransformerSubstation> substations = substationStorage.getByFilters(filters);

        assertFalse(substations.isEmpty());
        assertTrue(substations.size() == 1);
        assertTrue(substations.get(0).getName().equals("ololo"));
    }

    private void createSubstationsTetSet(){
        TransformerSubstationModel tp1 = initSimpleModel(111, "TP 1", 1, 2);
        TransformerSubstationModel tp2 = initSimpleModel(222, "TrPr", 2, 4);
        TransformerSubstationModel tp3 = initSimpleModel(333, "ololo", 1, 3);

        transformerSubstationDao.insert(tp1);
        transformerSubstationDao.insert(tp2);
        transformerSubstationDao.insert(tp3);
    }

    private TransformerSubstationModel initSimpleModel(long uniqId, String name, long spId, long resId) {
        return new TransformerSubstationModel(
                0,
                uniqId,
                "center",
                name,
                spId,
                resId,
                0,
                0
        );
    }


}
