package ru.drsk.progserega.inspectionsheet.storages.http.api_is_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SectionDeffectJson {

    @SerializedName("line_uniq_id")
    @Expose
    private long lineUniqId;

    @SerializedName("from_tower_uniq_id")
    @Expose
    private long fromTowerUniqId;

    @SerializedName("to_tower_uniq_id")
    @Expose
    private long toTowerUniqId;

    @SerializedName("deffect_type_id")
    @Expose
    private int deffectTypeId;

    @SerializedName("deffect_value")
    @Expose
    private int deffectValue;

    public SectionDeffectJson(long lineUniqId, long fromTowerUniqId, long toTowerUniqId, int deffectTypeId, int deffectValue) {
        this.lineUniqId = lineUniqId;
        this.fromTowerUniqId = fromTowerUniqId;
        this.toTowerUniqId = toTowerUniqId;
        this.deffectTypeId = deffectTypeId;
        this.deffectValue = deffectValue;
    }

    public long getLineUniqId() {
        return lineUniqId;
    }

    public void setLineUniqId(long lineUniqId) {
        this.lineUniqId = lineUniqId;
    }

    public long getFromTowerUniqId() {
        return fromTowerUniqId;
    }

    public void setFromTowerUniqId(long fromTowerUniqId) {
        this.fromTowerUniqId = fromTowerUniqId;
    }

    public long getToTowerUniqId() {
        return toTowerUniqId;
    }

    public void setToTowerUniqId(long toTowerUniqId) {
        this.toTowerUniqId = toTowerUniqId;
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
