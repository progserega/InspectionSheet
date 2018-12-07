package ru.drsk.progserega.inspectionsheet.storages.sqlight.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TransformerModel;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.TransformerSubstationModel;

@Dao
public interface TransformerDao {

    @Query("SELECT * FROM transformers")
    List<TransformerModel> loadAllTransformers();

    @Query("SELECT * FROM transformers WHERE id = :id ")
    TransformerModel getById(long id);

    @Insert
    long insert(TransformerModel transformer);

    @Update
    void update(TransformerModel transformer);

    @Delete
    void delete(TransformerModel transformer);

    @Query("DELETE FROM transformers")
    void delete();
}
