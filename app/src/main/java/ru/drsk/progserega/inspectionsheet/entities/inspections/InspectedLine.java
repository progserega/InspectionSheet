package ru.drsk.progserega.inspectionsheet.entities.inspections;

import java.util.List;

/**
 * Линия со всеми данными осмотра
 */
public class InspectedLine {
    private LineInspection lineInspection;
    private List<InspectedTower> inspectedTowers;
    private List<InspectedSection> inspectedSections;

    public InspectedLine(LineInspection lineInspection, List< InspectedTower > inspectedTowers, List< InspectedSection > inspectedSections) {
        this.lineInspection = lineInspection;
        this.inspectedTowers = inspectedTowers;
        this.inspectedSections = inspectedSections;
    }

    public LineInspection getLineInspection() {
        return lineInspection;
    }

    public List< InspectedTower > getInspectedTowers() {
        return inspectedTowers;
    }

    public List< InspectedSection > getInspectedSections() {
        return inspectedSections;
    }
}
