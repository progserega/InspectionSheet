package ru.drsk.progserega.inspectionsheet.storages.sqlight.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.InspectionModel;

@Dao
public interface InspectionDao {

    @Query("select * FROM inspections WHERE substation_id = :substationUniqId AND substation_type = :substationType AND equipment_id = :equipmentId")
    List<InspectionModel> getByEquipment(long substationUniqId, int substationType, long equipmentId);


    @Query("select * FROM inspections WHERE substation_id = :substationUniqId  AND equipment_id = 0")
    List<InspectionModel> getByStation(long substationUniqId);

//    @Query("select * FROM inspections WHERE substation_id IN(:substationIds) AND substation_type = :substationType ORDER BY substation_id")
//    List<InspectionModel> getBySubstations(List<Long> substationIds, int substationType);




    @Insert
    long insert(InspectionModel inspectionModel);

    @Update
    void update(InspectionModel inspectionModel);

    @Delete
    void delete(InspectionModel inspectionModel);

    @Query("DELETE FROM inspections")
    void deleteAll();
}
