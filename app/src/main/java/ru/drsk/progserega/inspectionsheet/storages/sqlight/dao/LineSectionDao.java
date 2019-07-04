package ru.drsk.progserega.inspectionsheet.storages.sqlight.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.LineSectionModel;

@Dao
public interface LineSectionDao {

    @Query("SELECT * FROM line_section")
    List<LineSectionModel> loadAll();

    @Insert
    long insert(LineSectionModel lineSectionModel);

    @Insert
    void insertAll(List<LineSectionModel> sections);

    @Query("DELETE FROM lines")
    void deleteAll();
}
