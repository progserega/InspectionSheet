package ru.drsk.progserega.inspectionsheet.storages.sqlight;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ru.drsk.progserega.inspectionsheet.entities.Substation;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.ResDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.SpWithResDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.SPDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.SubstationDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.SubstationEquipmentDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.TransformerSubstationEquipmentDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.TransformerDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.TransformerSubstationDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.Res;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.SP;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.SubstationEquipmentModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.SubstationModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TransformerSubstationEuipmentModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TransformerModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TransformerSubstationModel;

@Database(entities = {
        SP.class,
        Res.class,
        TransformerModel.class,
        TransformerSubstationModel.class,
        TransformerSubstationEuipmentModel.class,
        SubstationModel.class,
        SubstationEquipmentModel.class
}, version = 1)
public abstract class InspectionSheetDatabase extends RoomDatabase {

    private static InspectionSheetDatabase INSTANCE;

    public abstract SPDao spDao();

    public abstract ResDao resDao();

    public abstract SpWithResDao spWithResDao();

    public abstract TransformerSubstationDao transformerSubstationDao();

    public abstract TransformerDao transformerDao();

    public abstract TransformerSubstationEquipmentDao transfSubstationEquipmentDao();

    public abstract SubstationDao substationDao();

    public abstract SubstationEquipmentDao substationEquipmentDao();

}