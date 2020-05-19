package ru.drsk.progserega.inspectionsheet.storages.sqlight.dao;

import android.arch.persistence.db.SupportSQLiteQuery;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RawQuery;
import android.arch.persistence.room.Update;

import java.util.Date;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.StationModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.StationModel;

@Dao
public interface StationDao {

    @Query("SELECT * FROM stations WHERE type_id = :typeId")
    List<StationModel> loadAllByType(long typeId);

    @RawQuery
    List<StationModel> getByFilters(SupportSQLiteQuery query);

    @Query("SELECT * FROM stations WHERE uniq_id = :uid ")
    StationModel getById(long uid);


    @Query("SELECT * FROM stations WHERE inspection_percent > 0")
    List<StationModel> loadInspected();

    @Insert
    long insert(StationModel stationModel);

    @Query("UPDATE stations SET inspection_date = :date, inspection_percent = :percent WHERE uniq_id = :uid ")
    void updateInspectionInfo(long uid, Date date, float percent);

    @Update
    void update(StationModel stationModel);

    @Delete
    void delete(StationModel stationModel);

    @Query("DELETE FROM stations")
    void deleteAll();
}
