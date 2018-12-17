package ru.drsk.progserega.inspectionsheet.storages.sqlight.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TransformerInsideSubstaionModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TransformerSubstationEuipmentModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TransformerModel;

@Dao
public interface TransformerSubstationEquipmentDao {

    @Query("select tr.* from tp_transformers tpt LEFT JOIN transformers tr ON tpt.transformer_id = tr.id WHERE tpt.tp_id = :tpId")
    List<TransformerModel> getByTPId(long tpId);

    @Query("select tpt.id as equipment_id, tr.*, tpt.slot from tp_transformers tpt LEFT JOIN transformers tr ON tpt.transformer_id = tr.id WHERE tpt.tp_id = :tpId ORDER BY slot")
    List<TransformerInsideSubstaionModel> getBySubstation(long tpId);


    @Insert
    long insert(TransformerSubstationEuipmentModel transformer);

    @Update
    void update(TransformerSubstationEuipmentModel transformer);

    @Delete
    void delete(TransformerSubstationEuipmentModel transformer);

    @Query("DELETE FROM tp_transformers")
    void delete();
}
