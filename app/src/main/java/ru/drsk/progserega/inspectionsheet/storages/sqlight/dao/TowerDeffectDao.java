package ru.drsk.progserega.inspectionsheet.storages.sqlight.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.inspections.TowerDeffect;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TowerDeffectModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TowerModel;

@Dao
public interface TowerDeffectDao {

    @Query("SELECT * FROM tower_deffects WHERE tower_uniq_id = :towerUniqId")
    List<TowerDeffectModel> getTowerDeffects(long towerUniqId);

    @Insert
    Long addDeffect(TowerDeffectModel deffect);

    @Update
    void updateDeffect(TowerDeffectModel deffect);

    @Query("DELETE FROM tower_deffects")
    void deleteAll();
}
