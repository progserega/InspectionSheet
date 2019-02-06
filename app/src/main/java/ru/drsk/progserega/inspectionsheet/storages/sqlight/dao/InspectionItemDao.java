package ru.drsk.progserega.inspectionsheet.storages.sqlight.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.InspectionItemModel;

@Dao
public interface InspectionItemDao {

    @Insert
    long insert(InspectionItemModel inspectionItemModel);

    @Update
    void update(InspectionItemModel inspectionItemModel);

    @Delete
    void delete(InspectionItemModel inspectionItemModel);

    @Query("DELETE FROM inspection_items")
    void deleteAll();
}
