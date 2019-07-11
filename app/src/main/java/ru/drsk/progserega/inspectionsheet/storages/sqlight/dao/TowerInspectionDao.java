package ru.drsk.progserega.inspectionsheet.storages.sqlight.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TowerInspectionModel;

@Dao
public interface TowerInspectionDao {

    @Query("SELECT * FROM tower_inspection WHERE tower_uniq_id = :towerUniqId")
    TowerInspectionModel getByTower(long towerUniqId);

    @Insert
    Long addInspection(TowerInspectionModel inspectionModel);

    @Update
    void updateInspection(TowerInspectionModel inspectionModel);

    @Query("DELETE FROM tower_inspection")
    void deleteAll();
}
