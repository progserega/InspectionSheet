package ru.drsk.progserega.inspectionsheet.entities;

import ru.drsk.progserega.inspectionsheet.entities.catalogs.Material;
import ru.drsk.progserega.inspectionsheet.entities.catalogs.TowerType;

/**
 * Опора
 */
public class Tower extends Equipment {
    private Point mapPoint;

    private Material material;
    private TowerType towerType;

    public Point getMapPoint() {
        return mapPoint;
    }

    public Material getMaterial() {
        return material;
    }

    public TowerType getTowerType() {
        return towerType;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public void setTowerType(TowerType towerType) {
        this.towerType = towerType;
    }

    public Tower(long id, Point point, Material material, TowerType towerType ){
        this.id = id;
        this.name = "опора";
        this.type = EquipmentType.TOWER;
        this.mapPoint = point;
        this.material = material;
        this.towerType = towerType;
    }
}
