package ru.drsk.progserega.inspectionsheet.storages.sqlight.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.inspections.LineSectionDeffect;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.LineSectionDeffectModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TowerDeffectModel;

@Dao
public interface LineSectionDeffectDao {

    @Query("SELECT * FROM line_section_deffects WHERE section_id = :sectionId")
    List<LineSectionDeffectModel> getLineSectionDeffects(long sectionId);

    @Insert
    Long addDeffect(LineSectionDeffectModel deffect);

    @Update
    void updateDeffect(LineSectionDeffectModel deffect);

    @Query("DELETE FROM line_section_deffects")
    void deleteAll();

    @Query("DELETE FROM line_section_deffects where id in (:sectionDeffectsIds)")
    void delete(List<Long> sectionDeffectsIds);
}
