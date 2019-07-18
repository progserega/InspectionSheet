package ru.drsk.progserega.inspectionsheet.entities;

import java.util.List;


public class Line extends Equipment{

    private long uniqId;
    private Voltage voltage;
    private List<Tower> towers;
    private int startExploitationYear;

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

    public Line(long id, long uniqId, String name, Voltage type, int startExploitationYear, List<Tower> towers){
        this.id = id;
        this.uniqId = uniqId;
        this.name = name;
        this.voltage = type;
        this.towers = towers;
        this.type = EquipmentType.LINE;
        this.startExploitationYear = startExploitationYear;
    }
}
