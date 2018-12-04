package ru.drsk.progserega.inspectionsheet.storages.sqlight.entities;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

/**
 * Модель для получения СП с содержащимися в них РЭС
 */
public class SpWithRes {

    @Embedded
    public SP sp;

    @Relation(parentColumn = "id", entity = Res.class, entityColumn = "sp_id")
    public List<Res> networkAreas;

    public SP getSp() {
        return sp;
    }

    public void setSp(SP sp) {
        this.sp = sp;
    }

    public List<Res> getNetworkAreas() {
        return networkAreas;
    }

    public void setNetworkAreas(List<Res> networkAreas) {
        this.networkAreas = networkAreas;
    }
}
