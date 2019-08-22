package ru.drsk.progserega.inspectionsheet.storages.http.api_is_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TowerDeffectsJson {


    @SerializedName("tower_inspection_id")
    @Expose
    private long towerInspectionId;

    @SerializedName("deffects")
    @Expose
    private List<TowerDeffectJson> deffects;

    public TowerDeffectsJson(long towerInspectionId, List< TowerDeffectJson > deffects) {
        this.towerInspectionId = towerInspectionId;
        this.deffects = deffects;
    }

    public long getTowerInspectionId() {
        return towerInspectionId;
    }

    public void setTowerInspectionId(long towerInspectionId) {
        this.towerInspectionId = towerInspectionId;
    }

    public List< TowerDeffectJson > getDeffects() {
        return deffects;
    }

    public void setDeffects(List< TowerDeffectJson > deffects) {
        this.deffects = deffects;
    }
}
