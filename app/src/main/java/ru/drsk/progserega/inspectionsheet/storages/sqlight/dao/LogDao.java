package ru.drsk.progserega.inspectionsheet.storages.sqlight.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.LogModel;

@Dao
public interface LogDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(LogModel logModel);

    @Query("SELECT * FROM log_storage ORDER BY date desc")
    List<LogModel> fetchAllMessages();

    @Query("SELECT * FROM log_storage")
    List<LogModel> allMessages();


}
