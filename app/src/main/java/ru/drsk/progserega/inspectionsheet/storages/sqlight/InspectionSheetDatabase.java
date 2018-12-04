package ru.drsk.progserega.inspectionsheet.storages.sqlight;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.ResDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.SpWithResDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.dao.SPDao;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.Res;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.SP;

@Database(entities = {SP.class, Res.class}, version = 1)
public abstract class InspectionSheetDatabase extends RoomDatabase {

    private static InspectionSheetDatabase INSTANCE;

    public abstract SPDao spDao();

    public abstract ResDao resDao();

    public abstract SpWithResDao spWithResDao();
}