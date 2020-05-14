package ru.drsk.progserega.inspectionsheet.storages.sqlight.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.Date;

import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.StationEquipment;

@Dao
public interface StationEquipmentDao {

//    @Query("select tr.* from station_equipments se LEFT JOIN transformers tr ON se.transformer_id = tr.id WHERE se.substation_id = :tpId")
//    List<TransformerModel> getByTPId(long tpId);
//
//    @Query("select se.id as equipment_id, tr.*, se.slot, se.manufacture_year, se.inspection_date from station_equipments se LEFT JOIN transformers tr ON se.transformer_id = tr.id WHERE se.substation_id = :substationUniqId ORDER BY slot")
//    List<TransformerInsideSubstaionModel> getBySubstation(long substationUniqId);

    @Insert
    long insert(StationEquipment equipmentModel);

    @Update
    void update(StationEquipment equipmentModel);

    @Query("Update station_equipments SET manufacture_year  =:year, inspection_date = :inspectionDate WHERE id = :equipmentId")
    void updateCommonInfo(int year, Date inspectionDate, long equipmentId);

    @Delete
    void delete(StationEquipment equipmentModel);

    @Query("DELETE FROM station_equipments")
    void delete();

}
