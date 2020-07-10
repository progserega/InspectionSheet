package ru.drsk.progserega.inspectionsheet.storages.sqlight.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.StationEquipmentsDeffectType;


@Dao
public interface StationEquipmentsDeffectTypesDao {

//    @Query("SELECT * FROM station_equipments_deffect_types WHERE equipment_type='substation' ORDER BY 'order' ASC")
//    List< StationEquipmentsDeffectType > getSubstationDeffectTypes();
//
//    @Query("SELECT * FROM station_equipments_deffect_types WHERE equipment_type='tp' ORDER BY 'order' ASC")
//    List< StationEquipmentsDeffectType > getTPDeffectTypes();

    @Query("SELECT * FROM station_equipments_deffect_types WHERE equipment_type=:equipmentTypeId ORDER BY 'order' ASC")
    List< StationEquipmentsDeffectType > getDeffectByType(long equipmentTypeId);

    @Insert
    long insert(StationEquipmentsDeffectType deffectTypesModel);

    @Insert
    void insertAll(List< StationEquipmentsDeffectType > deffectTypesModels);

    @Update
    void update(StationEquipmentsDeffectType deffectTypesModel);

    @Delete
    void delete(StationEquipmentsDeffectType deffectTypesModel);

    @Query("DELETE FROM station_equipments_deffect_types")
    void deleteAll();
}
