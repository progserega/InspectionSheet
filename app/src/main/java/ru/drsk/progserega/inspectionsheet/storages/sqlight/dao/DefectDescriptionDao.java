package ru.drsk.progserega.inspectionsheet.storages.sqlight.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.DefectDescriptionModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.DefectDescriptionWithPhoto;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.EquipmentPhotoModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.StationEquipmentsDeffectType;

@Dao
public interface DefectDescriptionDao {

//    @Query("SELECT * FROM equipment_photos  WHERE equipment_id = :equipmentId AND substation_type =:substationType")
//    List<EquipmentPhotoModel> getByEquipment(long equipmentId, int substationType);

    @Query("select df.deffect_id, df.description, ph.photo_path from defects_descriptions df\n" +
            "left join defect_description_photos ph on\n" +
            "df.id = ph.description_id\n" +
            "WHERE df.object_type_id =:objectTypeId")
   List< DefectDescriptionWithPhoto > getByObjectType(int objectTypeId);

    @Insert
    long insert(DefectDescriptionModel descriptionModel);

    @Insert
    void insertAll(List< DefectDescriptionModel > descriptionModels);

    @Update
    void update(DefectDescriptionModel descriptionModel);

    @Delete
    void delete(DefectDescriptionModel descriptionModel);

    @Query("DELETE FROM defects_descriptions")
    void deleteAll();

    @Query("DELETE FROM defects_descriptions WHERE id =:descriptionId")
    void deleteById(long descriptionId);


}
