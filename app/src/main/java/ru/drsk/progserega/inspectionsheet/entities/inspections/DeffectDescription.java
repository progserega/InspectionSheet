package ru.drsk.progserega.inspectionsheet.entities.inspections;

public class DeffectDescription {

    private long deffectId;
    private String title;
    private InspectionPhoto photo;
    private String description;

    public DeffectDescription(long deffectId,  String title, InspectionPhoto photo, String description) {
        this.deffectId = deffectId;
        this.title = title;
        this.photo = photo;
        this.description = description;
    }

    public long getDeffectId() {
        return deffectId;
    }

    public String getTitle() {
        return title;
    }

    public InspectionPhoto getPhoto() {
        return photo;
    }

    public String getDescription() {
        return description;
    }
}
