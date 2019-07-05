package ru.drsk.progserega.inspectionsheet.entities.inspections;

/**
 * информация о осмотре опоры
 */
public class LineTowerInspectionItem {
    private long id;
    private LineTowerDeffectType type;
    private boolean isActive;

    public LineTowerInspectionItem(long id, LineTowerDeffectType type, boolean isActive) {
        this.id = id;
        this.type = type;
        this.isActive = isActive;
    }

    public long getId() {
        return id;
    }

    public LineTowerDeffectType getType() {
        return type;
    }

    public boolean isActive() {
        return isActive;
    }
}
