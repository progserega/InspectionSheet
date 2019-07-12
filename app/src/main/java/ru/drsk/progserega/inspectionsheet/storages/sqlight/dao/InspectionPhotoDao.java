package ru.drsk.progserega.inspectionsheet.storages.sqlight.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.InspectionPhotoModel;

@Dao
public interface InspectionPhotoDao {

    @Query("SELECT * FROM inspections_photos WHERE inspection_id = :inspectionId AND type = :type")
    List<InspectionPhotoModel> getByInspection(long inspectionId, String type);

    @Insert
    long insert(InspectionPhotoModel photoModel);

    @Update
    void update(InspectionPhotoModel photoModel);

    @Delete
    void delete(InspectionPhotoModel photoModel);

    @Query("DELETE FROM inspections_photos WHERE id =:photoId")
    void deleteById(long photoId);

    @Query("DELETE FROM inspections_photos")
    void deleteAll();

    @Query("DELETE FROM inspections_photos WHERE inspection_id = :inspectionId AND type = :type")
    void deleteByInspection(long inspectionId, String type);
}
