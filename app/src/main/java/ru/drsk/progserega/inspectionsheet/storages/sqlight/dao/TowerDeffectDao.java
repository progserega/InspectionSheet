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

//    @Query("SELECT * FROM towers")
//    List<TowerModel> loadAll();
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    long insert(TowerModel lineTowerModel);
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    void insertAll(List<TowerModel> towerModels);
//
//    @Query("SELECT tw.* from towers tw\n" +
//            "LEFT JOIN line_towers l\n" +
//            "on l.tower_uniq_id = tw.uniq_id\n" +
//            "where l.line_uniq_id = :lineUniqId \n" +
//            "Order by l.start ASC")
//    List<TowerModel> getByLineUniqId(long lineUniqId);
//
//    @Query("select tw.* from towers tw\n"+
//            "LEFT JOIN line_towers lt\n"+
//            "on tw.uniq_id = lt.tower_uniq_id\n"+
//            "WHERE lt.start = 1 AND lt.line_uniq_id = :lineUniqId")
//    TowerModel getFirstTowerInLine(long lineUniqId);
//
//    @Query("select tw.* from towers tw\n"+
//            "LEFT JOIN line_towers lt\n"+
//            "on tw.uniq_id = lt.tower_uniq_id\n"+
//            "WHERE tw.name = :name AND lt.line_uniq_id = :lineUniqId")
//    TowerModel getTowerByNameInLine(String name, long lineUniqId);


    @Query("DELETE FROM tower_deffects")
    void deleteAll();
}
