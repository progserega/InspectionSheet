package ru.drsk.progserega.inspectionsheet.entities.inspections;

import ru.drsk.progserega.inspectionsheet.storages.json.models.InspectionItemResultValues;

public class InspectionItem {

    private long id;
    private int valueId;
    private String number;
    private String name;
    private InspectionItemType type;

    private InspectionItemResult result;


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

    public InspectionItemResult getResult() {
        return result;
    }

    public void setResult(InspectionItemResult result) {
        this.result = result;
    }


    public InspectionItem(int id, int valueId, String number, String name, InspectionItemType type, InspectionItemResultValues values, InspectionItemResultValues subresultValues) {
        this.number = number;
        this.id = id;
        this.valueId = valueId;
        this.name = name;
        this.type = type;
        this.result = new InspectionItemResult(values, subresultValues);
    }
}
