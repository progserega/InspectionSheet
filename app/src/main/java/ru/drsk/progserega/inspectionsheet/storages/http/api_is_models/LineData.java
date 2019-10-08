package ru.drsk.progserega.inspectionsheet.storages.http.api_is_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LineData {

    @SerializedName("line")
    @Expose
    private LineInfo lineInfo;

    @SerializedName("towers")
    @Expose
    private List<TowerJson> towers;

    @SerializedName("sections")
    @Expose
    private List<SectionJson> sections;


    public LineData(LineInfo lineInfo, List< TowerJson > towers, List< SectionJson > sections) {
        this.lineInfo = lineInfo;
        this.towers = towers;
        this.sections = sections;
    }

    public LineInfo getLineInfo() {
        return lineInfo;
    }

    public void setLineInfo(LineInfo lineInfo) {
        this.lineInfo = lineInfo;
    }

    public List< TowerJson > getTowers() {
        return towers;
    }

    public void setTowers(List< TowerJson > towers) {
        this.towers = towers;
    }

    public List< SectionJson > getSections() {
        return sections;
    }

    public void setSections(List< SectionJson > sections) {
        this.sections = sections;
    }
}
