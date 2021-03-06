package ru.drsk.progserega.inspectionsheet.storages.sqlight.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.Res;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.SP;

@Dao
public interface SPDao {

    @Query("SELECT * FROM sp")
    List<SP> getAll();

    @Query("SELECT * FROM sp WHERE id = :id ")
    SP getById(long id);

    @Insert
    void insertAll(SP... enterprises);

    @Insert
    long insert(SP enterprise);

    @Delete
    void delete(SP enterprise);

    @Query("DELETE FROM SP")
    void delete();

}
