package ru.drsk.progserega.inspectionsheet.storages.sqlight.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.LineInspectionModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.LineSectionInspectionModel;

@Dao
public interface LineInspectionDao {

    @Query("SELECT * FROM line_inspection WHERE line_uniq_id = :lineUniqId")
    LineInspectionModel getByLineUniqId(long lineUniqId);

    @Insert
    Long addInspection(LineInspectionModel inspectionModel);

    @Update
    void updateInspection(LineInspectionModel inspectionModel);

    @Query("DELETE FROM line_inspection")
    void deleteAll();
}
