package ru.drsk.progserega.inspectionsheet.entities.inspections;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.LineSection;
import ru.drsk.progserega.inspectionsheet.entities.Tower;

public class InspectedSection {

    private LineSection section;
    private LineSectionInspection inspection;
    private List<LineSectionDeffect> deffects;

    public InspectedSection(LineSection section, LineSectionInspection inspection, List< LineSectionDeffect > deffects) {
        this.section = section;
        this.inspection = inspection;
        this.deffects = deffects;
    }

    public LineSection getSection() {
        return section;
    }

    public void setSection(LineSection section) {
        this.section = section;
    }

    public LineSectionInspection getInspection() {
        return inspection;
    }

    public void setInspection(LineSectionInspection inspection) {
        this.inspection = inspection;
    }

    public List< LineSectionDeffect > getDeffects() {
        return deffects;
    }

    public void setDeffects(List< LineSectionDeffect > deffects) {
        this.deffects = deffects;
    }
}
