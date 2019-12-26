package ru.drsk.progserega.inspectionsheet.storages.sqlight.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.InspectionItemModel;

@Deprecated
@Dao
public interface InspectionItemDao {

    @Query("SELECT * FROM inspection_items")
    List<InspectionItemModel> getAllInspections();


    @Insert
    long insert(InspectionItemModel inspectionItemModel);

    @Update
    void update(InspectionItemModel inspectionItemModel);

    @Delete
    void delete(InspectionItemModel inspectionItemModel);

    @Query("DELETE FROM inspection_items")
    void deleteAll();
}
