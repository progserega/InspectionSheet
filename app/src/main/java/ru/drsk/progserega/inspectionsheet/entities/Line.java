package ru.drsk.progserega.inspectionsheet.entities;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.catalogs.Material;
import ru.drsk.progserega.inspectionsheet.entities.catalogs.TowerType;


public class Line extends Equipment{

   // private long uniqId;
    private Voltage voltage;
    private List<Tower> towers;
    private int startExploitationYear;


    private Material cachedTowerMaterial = null;
    private TowerType cachedTowerType = null;
    private Material cachedSectionMaterial = null;



    public long getUniqId() {
        return uniqId;
    }

    public List<Tower> getTowers() {
        return towers;
    }

    public void setTowers(List<Tower> towers) {
        this.towers = towers;
    }

    public Voltage getVoltage() {
        return voltage;
    }

    public int getStartExploitationYear() {
        return startExploitationYear;
    }

    public void setStartExploitationYear(int startExploitationYear) {
        this.startExploitationYear = startExploitationYear;
    }

    public void setVoltage(Voltage voltage) {
        this.voltage = voltage;
    }

    public Material getCachedTowerMaterial() {
        return cachedTowerMaterial;
    }

    public void setCachedTowerMaterial(Material cachedTowerMaterial) {
        this.cachedTowerMaterial = cachedTowerMaterial;
    }

    public TowerType getCachedTowerType() {
        return cachedTowerType;
    }

    public void setCachedTowerType(TowerType cachedTowerType) {
        this.cachedTowerType = cachedTowerType;
    }

    public Material getCachedSectionMaterial() {
        return cachedSectionMaterial;
    }

    public void setCachedSectionMaterial(Material cachedSectionMaterial) {
        this.cachedSectionMaterial = cachedSectionMaterial;
    }

    public Line(long id, long uniqId, String name, Voltage type, int startExploitationYear, List<Tower> towers){
        this.id = id;
        this.uniqId = uniqId;
        this.name = name;
        this.voltage = type;
        this.towers = towers;
        this.type = EquipmentType.LINE;
        this.startExploitationYear = startExploitationYear;
    }

    @Override
    public Point getLocation() {
        return null;
    }

    public Tower getTowerByName(String name){
        String nameLowerCase = name.toLowerCase();
        for(Tower tower: this.towers){

            if(tower.getName().toLowerCase().equals( nameLowerCase)){
                return tower;
            }
        }
        return null;
    }
}
