package ru.drsk.progserega.inspectionsheet.storages.sqlight.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.Date;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.SubstationEquipmentModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TransformerInsideSubstaionModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TransformerModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TransformerSubstationEuipmentModel;

@Dao
public interface SubstationEquipmentDao {

    @Query("select tr.* from substation_equipments se LEFT JOIN transformers tr ON se.transformer_id = tr.id WHERE se.substation_id = :tpId")
    List<TransformerModel> getByTPId(long tpId);

    @Query("select se.id as equipment_id, tr.*, se.slot, se.manufacture_year, se.inspection_date from substation_equipments se LEFT JOIN transformers tr ON se.transformer_id = tr.id WHERE se.substation_id = :substationUniqId ORDER BY slot")
    List<TransformerInsideSubstaionModel> getBySubstation(long substationUniqId);

    @Insert
    long insert(SubstationEquipmentModel transformer);

    @Update
    void update(SubstationEquipmentModel transformer);

    @Query("Update substation_equipments SET manufacture_year  =:year, inspection_date = :inspectionDate WHERE id = :equipmentId")
    void updateTransformerCommonInfo(int year, Date inspectionDate, long equipmentId);

    @Delete
    void delete(SubstationEquipmentModel transformer);

    @Query("DELETE FROM substation_equipments")
    void delete();

}
