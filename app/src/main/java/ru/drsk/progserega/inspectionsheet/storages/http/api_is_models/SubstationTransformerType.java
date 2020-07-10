package ru.drsk.progserega.inspectionsheet.storages.http.api_is_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubstationTransformerType {
    @SerializedName("equipment_model_id")
    @Expose
    private long id;

    @SerializedName("equipment_model_name")
    @Expose
    private String name;

    @SerializedName("equipment_model_toir_id")
    @Expose
    private String description;

    public SubstationTransformerType(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

