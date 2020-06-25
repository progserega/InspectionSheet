package ru.drsk.progserega.inspectionsheet.storages.http.api_is_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/*
                    {
                     "substation_equipment_id": 27278,
                        "substation_uniq_id": 70924,
                        "slot_id": 27278,
                        "equipment_model_id": 964,
                        "equipment_toir_id": "PR0000324",
                        "manufacture_year": null,
                        "inspection_percent": null,
                        "inspection_date": null,
                        "slot_name": "Т-1"
                        }
 */
public class SubstationTransformerModel {

    @SerializedName("substation_equipment_id")
    @Expose
    private long equipmentId;

    @SerializedName("substation_uniq_id")
    @Expose
    private long substationUid;

    @SerializedName("slot_id")
    @Expose
    private int slotId;

    @SerializedName("equipment_model_id")
    @Expose
    private long equipmentModelId;

    @SerializedName("equipment_toir_id")
    @Expose
    private String equipmentToirId;

    @SerializedName("manufacture_year")
    @Expose
    private int manufactureYear;

    @SerializedName("slot_name")
    @Expose
    private String place;

    public SubstationTransformerModel(long equipmentId, long substationUid, int slotId, long equipmentModelId, String equipmentToirId, int manufactureYear, String place) {
        this.equipmentId = equipmentId;
        this.substationUid = substationUid;
        this.slotId = slotId;
        this.equipmentModelId = equipmentModelId;
        this.equipmentToirId = equipmentToirId;
        this.manufactureYear = manufactureYear;
        this.place = place;
    }

    public long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(long equipmentId) {
        this.equipmentId = equipmentId;
    }

    public long getSubstationUid() {
        return substationUid;
    }

    public void setSubstationUid(long substationUid) {
        this.substationUid = substationUid;
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public long getEquipmentModelId() {
        return equipmentModelId;
    }

    public void setEquipmentModelId(long equipmentModelId) {
        this.equipmentModelId = equipmentModelId;
    }

    public String getEquipmentToirId() {
        return equipmentToirId;
    }

    public void setEquipmentToirId(String equipmentToirId) {
        this.equipmentToirId = equipmentToirId;
    }

    public int getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(int manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
