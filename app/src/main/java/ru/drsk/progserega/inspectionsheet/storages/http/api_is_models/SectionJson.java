package ru.drsk.progserega.inspectionsheet.storages.http.api_is_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SectionJson {

    @SerializedName("line_uniq_id")
    @Expose
    private long lineUniqId;

    @SerializedName("from_tower_uniq_id")
    @Expose
    private long fromTowerUniqId;

    @SerializedName("to_tower_uniq_id")
    @Expose
    private long toTowerUniqId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("material")
    @Expose
    private int material;

    public SectionJson(long lineUniqId, long fromTowerUniqId, long toTowerUniqId, String name, int material) {
        this.lineUniqId = lineUniqId;
        this.fromTowerUniqId = fromTowerUniqId;
        this.toTowerUniqId = toTowerUniqId;
        this.name = name;
        this.material = material;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaterial() {
        return material;
    }

    public void setMaterial(int material) {
        this.material = material;
    }
}
