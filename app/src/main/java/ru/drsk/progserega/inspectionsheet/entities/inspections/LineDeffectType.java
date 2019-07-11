package ru.drsk.progserega.inspectionsheet.entities.inspections;



public class LineDeffectType {

    private long id;
    private int order;
    private String name;

    public LineDeffectType(long id, int order, String name) {
        this.id = id;
        this.order = order;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
