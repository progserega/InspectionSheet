package ru.drsk.progserega.inspectionsheet.entities.inspections;

public class InspectionItem {

    private int id;
    private String number;
    private String name;
    private InspectionItemType type;

    private Deffect deffect;


    public int getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public InspectionItemType getType() {
        return type;
    }

    public Deffect getDeffect() {
        return deffect;
    }

    public void setDeffect(Deffect deffect) {
        this.deffect = deffect;
    }

    public InspectionItem(int id, String number, String name, InspectionItemType type) {
        this.number = number;
        this.id = id;
        this.name = name;
        this.type = type;
        this.deffect = new Deffect();
    }
}
