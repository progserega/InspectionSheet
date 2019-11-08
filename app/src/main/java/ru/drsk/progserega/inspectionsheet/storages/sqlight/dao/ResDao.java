package ru.drsk.progserega.inspectionsheet.storages.sqlight.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.Res;

@Dao
public interface ResDao {

    @Query("SELECT * FROM Res")
    List<Res> getAll();

    @Query("SELECT * FROM res WHERE id = :id ")
    Res getById(long id);

    @Insert
    void insertAll(Res... areas);

    @Insert
    long insert(Res res);

    @Delete
    void delete(Res area);

    @Query("DELETE FROM res")
    void delete();
}
