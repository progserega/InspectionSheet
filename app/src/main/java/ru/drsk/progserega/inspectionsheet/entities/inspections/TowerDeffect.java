package ru.drsk.progserega.inspectionsheet.entities.inspections;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.catalogs.TowerDeffectType;

/**
 * Деффект у опоры
 */
public class TowerDeffect {
    private long id;
    private long towerId;
    private LineTowerDeffectType deffectType;
    private int Value;
    //private List<InspectionPhoto> photos;


    public TowerDeffect(long id, long towerId, LineTowerDeffectType deffectType, int value) {
        this.id = id;
        this.towerId = towerId;
        this.deffectType = deffectType;
        Value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTowerId() {
        return towerId;
    }

    public void setTowerId(long towerId) {
        this.towerId = towerId;
    }

    public LineTowerDeffectType getDeffectType() {
        return deffectType;
    }

    public void setDeffectType(LineTowerDeffectType deffectType) {
        this.deffectType = deffectType;
    }

    public int getValue() {
        return Value;
    }

    public void setValue(int value) {
        Value = value;
    }
}
