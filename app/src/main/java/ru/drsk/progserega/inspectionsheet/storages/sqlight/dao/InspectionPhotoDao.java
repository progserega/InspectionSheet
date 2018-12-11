package ru.drsk.progserega.inspectionsheet.storages.sqlight.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.InspectionPhotoModel;

@Dao
public interface InspectionPhotoDao {

    @Insert
    long insert(InspectionPhotoModel photoModel);

    @Update
    void update(InspectionPhotoModel photoModel);

    @Delete
    void delete(InspectionPhotoModel photoModel);

    @Query("DELETE FROM inspections_photos")
    void deleteAll();
}
