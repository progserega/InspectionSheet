package ru.drsk.progserega.inspectionsheet.entities.inspections;

import ru.drsk.progserega.inspectionsheet.entities.Line;
import ru.drsk.progserega.inspectionsheet.entities.LineTower;
import ru.drsk.progserega.inspectionsheet.entities.Substation;
import ru.drsk.progserega.inspectionsheet.entities.catalogs.InspectionType;

public class EquipmentInspection {
    private Line line;
    private LineTower lineTower;
    private InspectionType inspectionType;
    private Substation substation;

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

    public Substation getSubstation() {
        return substation;
    }

    public void setInspectionType(InspectionType inspectionType) {
        this.inspectionType = inspectionType;
    }

    public EquipmentInspection() {
    }

    public EquipmentInspection(Line line) {
        this.line = line;
    }

    public EquipmentInspection(Substation substation) {
        this.substation = substation;
    }

}
