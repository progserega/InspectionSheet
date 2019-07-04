package ru.drsk.progserega.inspectionsheet.entities.inspections;

import ru.drsk.progserega.inspectionsheet.entities.Line;
import ru.drsk.progserega.inspectionsheet.entities.LineTower;
import ru.drsk.progserega.inspectionsheet.entities.Substation;
import ru.drsk.progserega.inspectionsheet.entities.catalogs.InspectionType;

public class LineInspection {
    private Line line;

    private InspectionType inspectionType;


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

    public LineInspection() {
    }

    public LineInspection(Line line) {
        this.line = line;
    }

}
