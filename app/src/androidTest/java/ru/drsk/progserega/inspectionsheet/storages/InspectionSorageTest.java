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

import ru.drsk.progserega.inspectionsheet.R;
import ru.drsk.progserega.inspectionsheet.entities.Equipment;
import ru.drsk.progserega.inspectionsheet.entities.Substation;
import ru.drsk.progserega.inspectionsheet.entities.Transformer;
import ru.drsk.progserega.inspectionsheet.entities.EquipmentModel;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TransformerInspection;
import ru.drsk.progserega.inspectionsheet.storages.json.TransfInspectionListReader;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.InspectionSheetDatabase;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.InspectionStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.TransformerDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.TransformerSubstationDao;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class InspectionSorageTest {


    private InspectionSheetDatabase mDb;
    private TransformerDao transformerDao;
    private TransformerSubstationDao transformerSubstationDao;
    private Context context;

    @Before
    public void createDb() {
        context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, InspectionSheetDatabase.class).build();

    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void shouldSaveAndLoadInspections() throws IOException {

        InspectionStorage storage = new InspectionStorage(mDb, context);

        Equipment substation = new Substation(1, 1,"Substation 1", null, 0, 0,0);
        Transformer transformer = new Transformer(1, 1, new EquipmentModel( 1, "Transformerr name"));
        TransformerInspection transformerInspection = new TransformerInspection(substation, transformer);
        TransfInspectionListReader inspectionListReader = new TransfInspectionListReader();
        transformerInspection.setInspectionItems(inspectionListReader.readInspections(context.getResources().openRawResource(R.raw.substation_transormer_deffect_types)));

        storage.saveInspection(transformerInspection);
        assertTrue(transformerInspection.getInspectionItems().get(1).getId() > 0);

        //загружаем новый список -- эмулируем новый запуск программы
        transformerInspection.setInspectionItems(inspectionListReader.readInspections(context.getResources().openRawResource(R.raw.substation_transormer_deffect_types)));
        assertTrue(transformerInspection.getInspectionItems().get(1).getId() == 0);

        //загружаем из БД
        storage.loadInspections(transformerInspection);
        assertTrue(transformerInspection.getInspectionItems().get(1).getId() > 0);

    }
}
