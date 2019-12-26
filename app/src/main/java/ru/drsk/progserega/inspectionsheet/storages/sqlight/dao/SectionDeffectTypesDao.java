package ru.drsk.progserega.inspectionsheet.storages.sqlight.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.SectionDeffectTypesModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TowerDeffectTypesModel;


@Dao
public interface SectionDeffectTypesDao {

    @Query("SELECT * FROM section_deffect_types WHERE id=:id")
    SectionDeffectTypesModel  getById(long id);

    @Query("SELECT * FROM section_deffect_types WHERE voltage=:voltage ORDER BY 'order' ASC")
    List< SectionDeffectTypesModel > getByVoltage(String voltage);

    @Insert
    long insert(SectionDeffectTypesModel deffectTypesModel);

    @Insert
    void insertAll(List<SectionDeffectTypesModel> deffectTypesModels);

    @Update
    void update(SectionDeffectTypesModel deffectTypesModel);

    @Delete
    void delete(SectionDeffectTypesModel deffectTypesModel);

    @Query("DELETE FROM section_deffect_types")
    void deleteAll();
}
