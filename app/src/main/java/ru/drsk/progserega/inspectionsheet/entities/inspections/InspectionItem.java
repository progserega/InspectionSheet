package ru.drsk.progserega.inspectionsheet.entities.inspections;

public class InspectionItem {

    private long id;
    private int valueId;
    private String number;
    private String name;
    private InspectionItemType type;

    private Deffect deffect;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getValueId() {
        return valueId;
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


    public InspectionItem(int id, int valueId, String number, String name, InspectionItemType type) {
        this.number = number;
        this.id = id;
        this.valueId = valueId;
        this.name = name;
        this.type = type;
        this.deffect = new Deffect();
    }
}
