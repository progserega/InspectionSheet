package ru.drsk.progserega.inspectionsheet.entities;

import java.util.List;


public class Line extends Equipment{
    private Voltage voltage;
    private List<LineTower> towers;


    public List<LineTower> getTowers() {
        return towers;
    }

    public void setTowers(List<LineTower> towers) {
        this.towers = towers;
    }

    public Voltage getVoltage() {
        return voltage;
    }

    public Line(int id, String name, Voltage type, List<LineTower> towers){
        this.id = id;
        this.name = name;
        this.voltage = type;
        this.towers = towers;
        this.type = EquipmentType.LINE;
    }
}
