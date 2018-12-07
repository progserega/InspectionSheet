package ru.drsk.progserega.inspectionsheet.storages.sqlight.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.SpWithRes;

@Dao
public interface SpWithResDao {

    @Query("SELECT * FROM sp")
    List<SpWithRes> loadEnterprises();

    @Query("SELECT * FROM sp WHERE id = :spId")
    SpWithRes loadEnterpriseById(int spId);

    @Query("SELECT * FROM sp WHERE name = :name")
    SpWithRes loadEnterpriseByName(String name);


}
