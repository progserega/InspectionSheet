package ru.drsk.progserega.inspectionsheet.storages.sqlight.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.LogModel;

@Dao
public interface LogDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(LogModel logModel);

}
