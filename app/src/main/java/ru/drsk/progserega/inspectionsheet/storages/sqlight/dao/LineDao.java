package ru.drsk.progserega.inspectionsheet.storages.sqlight.dao;

import android.arch.persistence.db.SupportSQLiteQuery;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RawQuery;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.LineModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.SubstationModel;

@Dao
public interface LineDao {

    @Query("SELECT * FROM lines")
    List<LineModel> loadAll();

    @RawQuery
    List<LineModel> getByFilters(SupportSQLiteQuery query);

    @Query("SELECT * FROM lines WHERE id = :id ")
    LineModel getById(long id);

    @Query("SELECT * FROM lines WHERE uniq_id = :id ")
    LineModel getByUniqId(long id);


    @Query("update lines set bbox_top_lat = :topLat, bbox_top_lon = :topLon, bbox_bottom_lat = :bottomLat, bbox_botttom_lon =:bottomLon WHERE uniq_id = :uniqId")
    void updateBoundingBox(double topLat, double topLon, double bottomLat, double bottomLon, long uniqId);

    @Query("update lines set start_exploitation_year = :year WHERE id = :lineId")
    void updateStartExploitationYesr(long lineId, int year);

    @Insert
    long insert(LineModel line);

    @Insert
    void insertAll(List<LineModel> lines);

    @Query("DELETE FROM lines")
    void deleteAll();
}
