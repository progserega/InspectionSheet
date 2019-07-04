package ru.drsk.progserega.inspectionsheet.storages.sqlight.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.LineSectionModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.LineTowerModel;

@Dao
public interface LineTowerDao {

    @Query("SELECT * FROM line_towers")
    List<LineTowerModel> loadAll();

    @Insert
    long insert(LineTowerModel lineTowerModel);

    @Insert
    void insertAll(List<LineTowerModel> lineTowerModels);

    @Query("DELETE FROM line_towers")
    void deleteAll();
}
