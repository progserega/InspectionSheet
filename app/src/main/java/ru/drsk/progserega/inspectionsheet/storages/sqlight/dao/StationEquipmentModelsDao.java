package ru.drsk.progserega.inspectionsheet.storages.sqlight.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.StationEquipmentModels;

@Dao
public interface StationEquipmentModelsDao {

    @Insert
    long insert(StationEquipmentModels models);

    @Query("DELETE FROM station_equipment_models")
    void deleteAll();
}
