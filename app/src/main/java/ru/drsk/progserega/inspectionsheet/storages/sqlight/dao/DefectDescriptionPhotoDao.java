package ru.drsk.progserega.inspectionsheet.storages.sqlight.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.DefectDescriptionPhotoModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.EquipmentPhotoModel;

@Dao
public interface DefectDescriptionPhotoDao {

    @Query("SELECT * FROM defect_description_photos  WHERE description_id =:descriptionId ")
    List<DefectDescriptionPhotoModel> getByDescription(long descriptionId);

    @Query("SELECT * FROM defect_description_photos")
    List<DefectDescriptionPhotoModel> getAll();

    @Insert
    long insert(DefectDescriptionPhotoModel photoModel);

    @Update
    void update(DefectDescriptionPhotoModel photoModel);

    @Delete
    void delete(DefectDescriptionPhotoModel photoModel);

    @Query("DELETE FROM defect_description_photos")
    void deleteAll();

    @Query("DELETE FROM defect_description_photos WHERE id =:photoId")
    void deleteById(long photoId);

    @Query("DELETE FROM defect_description_photos WHERE description_id =:descriptionId ")
    void deleteByDescription(long descriptionId);
}
