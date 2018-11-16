package ru.drsk.progserega.inspectionsheet.entities;

import ru.drsk.progserega.inspectionsheet.entities.catalogs.InspectionType;

public class Inspection {
    private Line line;
    private LineTower lineTower;
    private InspectionType inspectionType;

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public LineTower getLineTower() {
        return lineTower;
    }

    public void setLineTower(LineTower lineTower) {
        this.lineTower = lineTower;
    }

    public InspectionType getInspectionType() {
        return inspectionType;
    }

    public void setInspectionType(InspectionType inspectionType) {
        this.inspectionType = inspectionType;
    }

    public Inspection() {
    }

    public Inspection(Line line) {
        this.line = line;
    }
}
