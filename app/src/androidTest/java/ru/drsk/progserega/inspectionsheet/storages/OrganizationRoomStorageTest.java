package ru.drsk.progserega.inspectionsheet.storages;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;


import ru.drsk.progserega.inspectionsheet.storages.sqlight.InspectionSheetDatabase;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.ResDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.SpWithResDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.SPDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.Res;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.SpWithRes;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.SP;

@RunWith(AndroidJUnit4.class)
public class OrganizationRoomStorageTest {

    private SPDao spDao;
    private InspectionSheetDatabase mDb;
    private ResDao resDao;
    private SpWithResDao spWithResDao;

    @BeforeClass
    public static void setUp() throws Exception {
        //do your setUp
       // Log.d(TAG, "setUp()");
    }

    @AfterClass
    public static void tearDown() throws Exception {
        //do your tearDown
       // Log.d(TAG, "tearDown()");
    }

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, InspectionSheetDatabase.class).build();
        spDao = mDb.spDao();
        resDao = mDb.resDao();
        spWithResDao = mDb.spWithResDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }


    @Test
    public void shouldSelectAllSP() {

        SP enterprise = new SP(1, "Test 1", "Test 1 full");

        spDao.insertAll(enterprise);
        List<SP> allEnterprises = spDao.getAll();
        assertTrue(allEnterprises.get(0).getName().equals("Test 1"));
    }

    @Test
    public void souldInsertNetworkAreasAsArray() {


        SP enterprise = new SP(2, "Test 2", "Test 2 full");
        spDao.insertAll(enterprise);

        Res area1 = new Res(1, "RES 1", "RES 1", 2);
        Res area2 = new Res(2, "RES 2", "RES 2", 2);
        Res area3 = new Res(3, "RES 3", "RES 3", 2);
        resDao.insertAll(new Res[]{area1, area2, area3});

        List<Res> areas = resDao.getAll();
        assertTrue(areas.size() == 3);
    }

    @Test
    public void souldSelectEnterpriseWithNetworkAreas() {
        SP enterprise = new SP(2, "Test 2", "Test 2 full");
        spDao.insertAll(enterprise);

        Res area1 = new Res(1, "RES 1", "RES 1", 2);
        Res area2 = new Res(2, "RES 2", "RES 2", 2);
        Res area3 = new Res(3, "RES 3", "RES 3", 2);
        resDao.insertAll(area1, area2, area3);

        SpWithRes spWithENAS = spWithResDao.loadEnterpriseById(2);
        assertTrue(spWithENAS != null);
        assertTrue(spWithENAS.getNetworkAreas().size() == 3);
    }

    @Test
    public void souldSelectAllEnterprisesWithNetworkAreas(){
        SP enterprise3 = new SP(3, "Test 3", "Test 3 full");
        SP enterprise1 = new SP(1, "Test 1", "Test 1 full");
        spDao.insertAll(enterprise1, enterprise3);

        List<SpWithRes> enterprises = spWithResDao.loadEnterprises();
        assertFalse(enterprises.isEmpty());
        assertTrue(enterprises.size() == 2);

    }
}
