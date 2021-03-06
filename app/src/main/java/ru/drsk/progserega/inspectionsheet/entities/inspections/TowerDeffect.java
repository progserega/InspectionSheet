package ru.drsk.progserega.inspectionsheet.entities.inspections;

/**
 * Деффект у опоры
 */
public class TowerDeffect {
    private long id;
    private long towerUniqId;
    private LineDeffectType deffectType;
    private int Value;
    //private List<InspectionPhoto> photos;


    public TowerDeffect(long id, long towerUniqId, LineDeffectType deffectType, int value) {
        this.id = id;
        this.towerUniqId = towerUniqId;
        this.deffectType = deffectType;
        Value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTowerUniqId() {
        return towerUniqId;
    }

    public void setTowerUniqId(long towerUniqId) {
        this.towerUniqId = towerUniqId;
    }

    public LineDeffectType getDeffectType() {
        return deffectType;
    }

    public void setDeffectType(LineDeffectType deffectType) {
        this.deffectType = deffectType;
    }

    public int getValue() {
        return Value;
    }

    public void setValue(int value) {
        Value = value;
    }
}
