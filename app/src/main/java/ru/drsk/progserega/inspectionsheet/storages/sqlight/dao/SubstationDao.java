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

import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.SubstationModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TransformerSubstationModel;

@Deprecated
@Dao
public interface SubstationDao {

    @Query("SELECT * FROM substations")
    List<SubstationModel> loadAll();

    @RawQuery
    List<SubstationModel> getByFilters(SupportSQLiteQuery query);

    @Query("SELECT * FROM substations WHERE id = :id ")
    SubstationModel getById(long id);


    @Query("SELECT * FROM substations WHERE inspection_percent > 0")
    List<SubstationModel> loadInspected();

    @Insert
    long insert(SubstationModel substationModel);

    @Query("UPDATE substations SET inspection_date = :date, inspection_percent = :percent WHERE id = :id ")
    void updateInspectionInfo(long id, Date date, float percent);

    @Update
    void update(SubstationModel substationModel);

    @Delete
    void delete(SubstationModel substationModel);

    @Query("DELETE FROM substations")
    void delete();
}
