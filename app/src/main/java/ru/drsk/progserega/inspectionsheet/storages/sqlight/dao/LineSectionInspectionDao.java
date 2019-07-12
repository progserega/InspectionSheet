package ru.drsk.progserega.inspectionsheet.storages.sqlight.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.LineSectionInspectionModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TowerInspectionModel;

@Dao
public interface LineSectionInspectionDao {

    @Query("SELECT * FROM line_section_inspection WHERE section_id = :sectionId")
    LineSectionInspectionModel getBySectionId(long sectionId);

    @Insert
    Long addInspection(LineSectionInspectionModel inspectionModel);

    @Update
    void updateInspection(LineSectionInspectionModel inspectionModel);

    @Query("DELETE FROM line_section_inspection")
    void deleteAll();
}
