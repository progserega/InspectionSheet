package ru.drsk.progserega.inspectionsheet.storages.sqlight.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TowerInspectionModel;

@Dao
public interface TowerInspectionDao {

    @Query("SELECT * FROM tower_inspection WHERE tower_uniq_id = :towerUniqId")
    TowerInspectionModel getByTower(long towerUniqId);

    @Query("SELECT ti.* from tower_inspection ti\n" +
            "LEFT JOIN line_towers lt\n" +
            "ON lt.tower_uniq_id = ti.tower_uniq_id\n" +
            "WHERE lt.line_uniq_id = :lineUniqId")
    List<TowerInspectionModel> getByLine(long lineUniqId);

    @Insert
    Long addInspection(TowerInspectionModel inspectionModel);

    @Update
    void updateInspection(TowerInspectionModel inspectionModel);

    @Query("DELETE FROM tower_inspection")
    void deleteAll();

    @Query("DELETE FROM tower_inspection where id  IN (:inspectionIds)")
    void delete(List<Long> inspectionIds);
}
