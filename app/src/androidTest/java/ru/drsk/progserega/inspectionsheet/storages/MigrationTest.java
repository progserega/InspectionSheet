package ru.drsk.progserega.inspectionsheet.storages;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.framework.FrameworkSQLiteOpenHelperFactory;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.testing.MigrationTestHelper;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectionItem;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.InspectionSheetDatabase;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.InspectionItemDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.InspectionItemModel;

import static org.junit.Assert.assertTrue;
import static ru.drsk.progserega.inspectionsheet.storages.sqlight.InspectionSheetDatabase.MIGRATION_1_2;

@RunWith(AndroidJUnit4.class)
public class MigrationTest {
    private static final String TEST_DB = "migration-test";

    private Context context;
    @Rule
    public MigrationTestHelper helper;

    public MigrationTest() {
        helper = new MigrationTestHelper(InstrumentationRegistry.getInstrumentation(),
                InspectionSheetDatabase.class.getCanonicalName(),
                new FrameworkSQLiteOpenHelperFactory());
    }

    // Helper for creating SQLite database in version 1
    private SupportSQLiteDatabase db;

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getTargetContext();
        db = helper.createDatabase(TEST_DB, 1);
    }

    @After
    public void tearDown() throws Exception {
        // Clear the database after every test
        context.deleteDatabase(TEST_DB);
    }

    @Test
    public void migrate1To2() throws IOException {


        // db has schema version 1. insert some data using SQL queries.
        // You cannot use DAO classes because they expect the latest schema.
        //db.execSQL("");

        // Prepare for the next version.
        //db.close();

        // Re-open the database with version 2 and provide
        // MIGRATION_1_2 as the migration process.
        db = helper.runMigrationsAndValidate(TEST_DB, 2, true, MIGRATION_1_2);
        // MigrationTestHelper automatically verifies the schema changes,
        // but you need to validate that the data was migrated properly.

        InspectionSheetDatabase migratedDB =  getMigratedRoomDatabase();
        InspectionItemDao inspectionItemDao  = migratedDB.inspectionItemDao();

        List<InspectionItemModel> allItems = inspectionItemDao.getAllInspections();
        assertTrue( allItems.size() == 0);
    }

    private InspectionSheetDatabase getMigratedRoomDatabase() {
        InspectionSheetDatabase database = Room.databaseBuilder(InstrumentationRegistry.getTargetContext(),
                InspectionSheetDatabase.class, TEST_DB)
                .addMigrations(MIGRATION_1_2)
                .build();
        // close the database and release any stream resources when the test finishes
        helper.closeWhenFinished(database);
        return database;
    }
}