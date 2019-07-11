package ru.drsk.progserega.inspectionsheet.entities.inspections;

/**
 * Деффект у пролета
 */
public class LineSectionDeffect {
    private long id;
    private long sectionId;
    private LineDeffectType deffectType;
    private int Value;

    public LineSectionDeffect(long id, long sectionId, LineDeffectType deffectType, int value) {
        this.id = id;
        this.sectionId = sectionId;
        this.deffectType = deffectType;
        Value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public long getSectionId() {
        return sectionId;
    }

    public void setSectionId(long sectionId) {
        this.sectionId = sectionId;
    }
}
