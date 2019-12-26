package ru.drsk.progserega.inspectionsheet.storages.sqlight.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TowerDeffectTypesModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TransformerDeffectTypesModel;


@Dao
public interface TowerDeffectTypesDao {

    @Query("SELECT * FROM tower_deffect_types WHERE id=:id")
    TowerDeffectTypesModel  getById(long id);

    @Query("SELECT * FROM tower_deffect_types WHERE voltage=:voltage ORDER BY 'order' ASC")
    List< TowerDeffectTypesModel > getByVoltage(String voltage);

    @Insert
    long insert(TowerDeffectTypesModel deffectTypesModel);

    @Insert
    void insertAll(List<TowerDeffectTypesModel> deffectTypesModels);

    @Update
    void update(TowerDeffectTypesModel deffectTypesModel);

    @Delete
    void delete(TowerDeffectTypesModel deffectTypesModel);

    @Query("DELETE FROM tower_deffect_types")
    void deleteAll();
}
