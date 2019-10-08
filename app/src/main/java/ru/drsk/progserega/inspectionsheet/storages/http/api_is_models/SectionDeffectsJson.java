package ru.drsk.progserega.inspectionsheet.storages.http.api_is_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SectionDeffectsJson {

    @SerializedName("section_inspection_id")
    @Expose
    private long sectionInspectionId;

    @SerializedName("deffects")
    @Expose
    private List<SectionDeffectJson> deffects;

    public SectionDeffectsJson(long sectionInspectionId, List< SectionDeffectJson > deffects) {
        this.sectionInspectionId = sectionInspectionId;
        this.deffects = deffects;
    }

    public long getSectionInspectionId() {
        return sectionInspectionId;
    }

    public void setSectionInspectionId(long sectionInspectionId) {
        this.sectionInspectionId = sectionInspectionId;
    }

    public List< SectionDeffectJson > getDeffects() {
        return deffects;
    }

    public void setDeffects(List< SectionDeffectJson > deffects) {
        this.deffects = deffects;
    }
}
