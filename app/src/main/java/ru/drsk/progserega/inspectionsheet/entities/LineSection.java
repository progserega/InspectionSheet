package ru.drsk.progserega.inspectionsheet.entities;

import ru.drsk.progserega.inspectionsheet.entities.catalogs.Material;

public class LineSection extends Equipment {

    private long lineUniqId;

    private long towerFromUniqId;

    private long towerToUniqId;

    private Material material;


    public LineSection(long id, String name, long lineUniqId, long towerFromUniqId, long towerToUniqId, Material material) {
        super(id, id, name, EquipmentType.LINE_SECTION);
        this.lineUniqId = lineUniqId;
        this.towerFromUniqId = towerFromUniqId;
        this.towerToUniqId = towerToUniqId;
        this.material = material;
    }

    public long getLineUniqId() {
        return lineUniqId;
    }

    public void setLineUniqId(long lineUniqId) {
        this.lineUniqId = lineUniqId;
    }

    public long getTowerFromUniqId() {
        return towerFromUniqId;
    }

    public void setTowerFromUniqId(long towerFromUniqId) {
        this.towerFromUniqId = towerFromUniqId;
    }

    public long getTowerToUniqId() {
        return towerToUniqId;
    }

    public void setTowerToUniqId(long towerToUniqId) {
        this.towerToUniqId = towerToUniqId;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public Point getLocation() {
        return null;
    }
}
