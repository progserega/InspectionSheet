package ru.drsk.progserega.inspectionsheet.storages.sqlight.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.EquipmentPhotoModel;

@Dao
public interface EquipmentPhotoDao {

    @Query("SELECT * FROM equipment_photos  WHERE equipment_id = :equipmentId AND substation_type =:substationType")
    List<EquipmentPhotoModel> getByEquipment(long equipmentId, int substationType);

    @Insert
    long insert(EquipmentPhotoModel photoModel);

    @Update
    void update(EquipmentPhotoModel photoModel);

    @Delete
    void delete(EquipmentPhotoModel photoModel);

    @Query("DELETE FROM equipment_photos")
    void deleteAll();

    @Query("DELETE FROM equipment_photos WHERE id =:photoId")
    void deleteById(long photoId);

    @Query("DELETE FROM equipment_photos WHERE equipment_id = :equipmentId AND substation_type =:substationType")
    void deleteByEquipment(long equipmentId, int substationType);
}
