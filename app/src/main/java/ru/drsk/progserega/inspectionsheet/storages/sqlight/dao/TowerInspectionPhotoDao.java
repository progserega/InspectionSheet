package ru.drsk.progserega.inspectionsheet.storages.sqlight.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TowerInspectionPhotoModel;

@Dao
public interface TowerInspectionPhotoDao {

    @Query("SELECT * FROM tower_inspections_photos WHERE inspection_id = :inspectionId")
    List<TowerInspectionPhotoModel> getByInspection(long inspectionId);

    @Insert
    long insert(TowerInspectionPhotoModel photoModel);

    @Update
    void update(TowerInspectionPhotoModel photoModel);

    @Delete
    void delete(TowerInspectionPhotoModel photoModel);

    @Query("DELETE FROM tower_inspections_photos WHERE id =:photoId")
    void deleteById(long photoId);

    @Query("DELETE FROM tower_inspections_photos")
    void deleteAll();

    @Query("DELETE FROM tower_inspections_photos WHERE inspection_id = :inspectionId")
    void deleteByInspection(long inspectionId);


}
