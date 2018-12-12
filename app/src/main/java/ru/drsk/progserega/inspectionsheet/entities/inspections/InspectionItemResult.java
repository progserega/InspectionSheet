package ru.drsk.progserega.inspectionsheet.entities.inspections;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.storages.json.models.InspectionItemResultValues;

public class InspectionItemResult {
    private String comment;

    private InspectionItemResultValues resultValues;
    private InspectionItemResultValues subresultValues;

    private List<String> values;
    private List<String> subValues;

    private List<DeffectPhoto> photos;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<DeffectPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<DeffectPhoto> photos) {
        this.photos = photos;
    }

    public InspectionItemResultValues getResultValues() {
        return resultValues;
    }

    public InspectionItemResultValues getSubresultValues() {
        return subresultValues;
    }

    public List<String> getValues() {
        return values;
    }

    public List<String> getSubValues() {
        return subValues;
    }

    public InspectionItemResult(InspectionItemResultValues resultValues, InspectionItemResultValues subresultValues) {
        this.resultValues = resultValues;
        this.subresultValues = subresultValues;
        comment = "";
        values = new ArrayList<>();
        subValues = new ArrayList<>();
        photos = new ArrayList<>();
    }
}
