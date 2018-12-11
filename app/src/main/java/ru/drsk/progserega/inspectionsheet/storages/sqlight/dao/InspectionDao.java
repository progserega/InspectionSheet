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

    @Query("select * FROM inspections WHERE substation_id = :substationId AND substation_type = :substationType AND equipment_id = :equipmentId")
    List<InspectionModel> getByEquipment(long substationId, int substationType, long equipmentId);

    @Insert
    long insert(InspectionModel inspectionModel);

    @Update
    void update(InspectionModel inspectionModel);

    @Delete
    void delete(InspectionModel inspectionModel);

    @Query("DELETE FROM inspections")
    void deleteAll();
}
