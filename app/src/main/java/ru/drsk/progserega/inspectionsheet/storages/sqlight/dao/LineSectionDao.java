package ru.drsk.progserega.inspectionsheet.storages.sqlight.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.LineSectionModel;

@Dao
public interface LineSectionDao {

    @Query("SELECT * FROM line_section WHERE id = :id")
    LineSectionModel getById(long id);

    @Query("SELECT * FROM line_section")
    List<LineSectionModel> loadAll();

    @Query("SELECT * FROM line_section WHERE id IN(:ids) ")
    List<LineSectionModel> getByIds(Long[] ids);

    @Query("SELECT * FROM line_section WHERE line_uniq_id = :lineUniqId AND from_tower_uniq_id = :towerUniqId")
    List<LineSectionModel> getByLineTower(long lineUniqId, long towerUniqId);

    @Query("SELECT * FROM line_section WHERE line_uniq_id = :lineUniqId")
    List<LineSectionModel> getByLine(long lineUniqId);

    @Insert
    long insert(LineSectionModel lineSectionModel);

    @Update
    void update(LineSectionModel lineSectionModel);

    @Insert
    void insertAll(List<LineSectionModel> sections);

    @Query("DELETE FROM line_section")
    void deleteAll();
}
