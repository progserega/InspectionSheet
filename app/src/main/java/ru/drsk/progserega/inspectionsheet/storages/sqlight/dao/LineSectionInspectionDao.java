package ru.drsk.progserega.inspectionsheet.storages.sqlight.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.LineInspectionModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.LineSectionInspectionModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TowerInspectionModel;

@Dao
public interface LineSectionInspectionDao {

    @Query("SELECT * FROM line_section_inspection WHERE section_id = :sectionId")
    LineSectionInspectionModel getBySectionId(long sectionId);

    @Query("SELECT si.* FROM line_section_inspection si\n" +
            "LEFT JOIN line_section ls\n" +
            "ON si.section_id = ls.id\n" +
            "WHERE ls.line_uniq_id =:lineUniqId")
    List< LineSectionInspectionModel > getByLine(long lineUniqId);


    @Insert
    Long addInspection(LineSectionInspectionModel inspectionModel);

    @Update
    void updateInspection(LineSectionInspectionModel inspectionModel);

    @Query("DELETE FROM line_section_inspection")
    void deleteAll();

    @Query("DELETE FROM line_section_inspection where id IN (:inspectionIds)")
    void delete(List<Long> inspectionIds);

}
