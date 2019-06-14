package ru.drsk.progserega.inspectionsheet.entities.inspections;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.storages.json.models.InspectionItemPossibleResult;

public class InspectionItemResult {

    private InspectionItemPossibleResult possibleResult;
    private InspectionItemPossibleResult possibleSubresult;

    private List<String> values;
    private List<String> subValues;
    private String comment;
    private List<InspectionPhoto> photos;

    public void setValues(List<String> values) {
        this.values = values;
    }

    public void setSubValues(List<String> subValues) {
        this.subValues = subValues;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<InspectionPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<InspectionPhoto> photos) {
        this.photos = photos;
    }

    public InspectionItemPossibleResult getPossibleResult() {
        return possibleResult;
    }

    public InspectionItemPossibleResult getPossibleSubresult() {
        return possibleSubresult;
    }

    public List<String> getValues() {
        return values;
    }

    public List<String> getSubValues() {
        return subValues;
    }

    public InspectionItemResult(InspectionItemPossibleResult possibleResult, InspectionItemPossibleResult possibleSubresult) {
        this.possibleResult = possibleResult;
        this.possibleSubresult = possibleSubresult;
        comment = "";
        values = new ArrayList<>();
        subValues = new ArrayList<>();
        photos = new ArrayList<>();
    }

    public boolean isEmpty(){

        if (!comment.isEmpty() || !values.isEmpty() || !subValues.isEmpty() || !photos.isEmpty()) {
            return false;
        }

        return true;
    }
}
