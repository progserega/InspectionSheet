package ru.drsk.progserega.inspectionsheet.entities.inspections;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Tower;

public class InspectedTower {

    private Tower tower;
    private TowerInspection inspection;
    private List<TowerDeffect> deffects;

    public InspectedTower(Tower tower, TowerInspection inspection, List< TowerDeffect > deffects) {
        this.tower = tower;
        this.inspection = inspection;
        this.deffects = deffects;
    }

    public Tower getTower() {
        return tower;
    }

    public void setTower(Tower tower) {
        this.tower = tower;
    }

    public TowerInspection getInspection() {
        return inspection;
    }

    public void setInspection(TowerInspection inspection) {
        this.inspection = inspection;
    }

    public List< TowerDeffect > getDeffects() {
        return deffects;
    }

    public void setDeffects(List< TowerDeffect > deffects) {
        this.deffects = deffects;
    }
}
