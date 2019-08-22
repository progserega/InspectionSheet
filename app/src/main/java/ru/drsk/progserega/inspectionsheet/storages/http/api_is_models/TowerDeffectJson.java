package ru.drsk.progserega.inspectionsheet.storages.http.api_is_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TowerDeffectJson {

    @SerializedName("tower_uniq_id")
    @Expose
    private long towerUniqId;

    @SerializedName("deffect_type_id")
    @Expose
    private int deffectTypeId;

    @SerializedName("deffect_value")
    @Expose
    private int deffectValue;

    public TowerDeffectJson(long towerUniqId, int deffectTypeId, int deffectValue) {
        this.towerUniqId = towerUniqId;
        this.deffectTypeId = deffectTypeId;
        this.deffectValue = deffectValue;
    }

    public long getTowerUniqId() {
        return towerUniqId;
    }

    public void setTowerUniqId(long towerUniqId) {
        this.towerUniqId = towerUniqId;
    }

    public int getDeffectTypeId() {
        return deffectTypeId;
    }

    public void setDeffectTypeId(int deffectTypeId) {
        this.deffectTypeId = deffectTypeId;
    }

    public int getDeffectValue() {
        return deffectValue;
    }

    public void setDeffectValue(int deffectValue) {
        this.deffectValue = deffectValue;
    }
}
