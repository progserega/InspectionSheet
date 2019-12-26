package ru.drsk.progserega.inspectionsheet.storages.sqlight.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.InspectionItemModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TransformerDeffectTypesModel;


@Dao
public interface TransformerDeffectTypesDao {

    @Query("SELECT * FROM transformer_deffect_types WHERE equipment_type='substation' ORDER BY 'order' ASC")
    List< TransformerDeffectTypesModel > getSubstationDeffectTypes();

    @Query("SELECT * FROM transformer_deffect_types WHERE equipment_type='tp' ORDER BY 'order' ASC")
    List< TransformerDeffectTypesModel > getTPDeffectTypes();


    @Insert
    long insert(TransformerDeffectTypesModel deffectTypesModel);

    @Insert
    void insertAll(List<TransformerDeffectTypesModel> deffectTypesModels);

    @Update
    void update(TransformerDeffectTypesModel deffectTypesModel);

    @Delete
    void delete(TransformerDeffectTypesModel deffectTypesModel);

    @Query("DELETE FROM transformer_deffect_types")
    void deleteAll();
}
