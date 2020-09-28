package ru.drsk.progserega.inspectionsheet.storages.http.api_is_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeffectDescriptionJson {

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("deffect_id")
    @Expose
    private long deffectId;

    @SerializedName("object_type_id")
    @Expose
    private int objectTypeId;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("image_url")
    @Expose
    private String imageUrl;


    public DeffectDescriptionJson(long id, long deffectId, int objectTypeId, String description, String imageUrl) {
        this.id = id;
        this.deffectId = deffectId;
        this.objectTypeId = objectTypeId;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDeffectId() {
        return deffectId;
    }

    public void setDeffectId(long deffectId) {
        this.deffectId = deffectId;
    }

    public int getObjectTypeId() {
        return objectTypeId;
    }

    public void setObjectTypeId(int objectTypeId) {
        this.objectTypeId = objectTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFileName() {
        if (this.imageUrl == null || this.imageUrl.equals("")) {
            return "";
        }

        String[] parts = imageUrl.split("/");
        if (parts.length == 0) {
            return "";
        }

        return parts[parts.length - 1];
    }
}
