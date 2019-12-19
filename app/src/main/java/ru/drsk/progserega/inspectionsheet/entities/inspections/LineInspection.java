package ru.drsk.progserega.inspectionsheet.entities.inspections;

import java.util.Date;

import ru.drsk.progserega.inspectionsheet.entities.Line;
import ru.drsk.progserega.inspectionsheet.entities.LineTower;
import ru.drsk.progserega.inspectionsheet.entities.Substation;
import ru.drsk.progserega.inspectionsheet.entities.catalogs.InspectionType;

public class LineInspection {
    private long id;

    private Line line;

    private InspectionType inspectionType;

    private String inspectorName;

    private Date inspectionDate;

    private float inspectionPercent = 0.0f;

    public LineInspection(long id, Line line) {
        this.id = id;
        this.line = line;
    }

    public LineInspection(long id, Line line, InspectionType inspectionType, String inspectorName, Date inspectionDate) {
        this.id = id;
        this.line = line;
        this.inspectionType = inspectionType;
        this.inspectorName = inspectorName;
        this.inspectionDate = inspectionDate;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public InspectionType getInspectionType() {
        return inspectionType;
    }

    public void setInspectionType(InspectionType inspectionType) {
        this.inspectionType = inspectionType;
    }

    public String getInspectorName() {
        return inspectorName;
    }

    public void setInspectorName(String inspectorName) {
        this.inspectorName = inspectorName;
    }

    public Date getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(Date inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public float getInspectionPercent() {
        return inspectionPercent;
    }

    public void setInspectionPercent(float inspectionPercent) {
        this.inspectionPercent = inspectionPercent;
    }
}
