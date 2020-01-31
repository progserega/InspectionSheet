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

@Dao
public interface TransformerSubstationDao {

    @Query("SELECT * FROM tp")
    List<TransformerSubstationModel> loadAllTp();


    @RawQuery
    List<TransformerSubstationModel> getByFilters(SupportSQLiteQuery query);

    @Query("SELECT * FROM tp WHERE id = :id ")
    TransformerSubstationModel getById(long id);

    @Query("SELECT * FROM tp WHERE uniq_id = :unqId ")
    TransformerSubstationModel getByUniqId(long unqId);

    @Query("SELECT * FROM tp WHERE inspection_percent > 0")
    List< TransformerSubstationModel > loadInspected();

    @Insert
    long insert(TransformerSubstationModel tp);

    @Update
    void update(TransformerSubstationModel tp);

    @Query("UPDATE tp SET inspection_date = :date, inspection_percent = :percent WHERE id = :id ")
    void updateInspectionInfo(long id, Date date, float percent);

    @Delete
    void delete(TransformerSubstationModel tp);

    @Query("DELETE FROM tp")
    void delete();
}
