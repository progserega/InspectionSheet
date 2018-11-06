package ru.drsk.progserega.inspectionsheet.interfaces;

import ru.drsk.progserega.inspectionsheet.entities.EquipmentType;

public interface IListItem {
    public String getName();
    public EquipmentType getType();
    public int getId();
}
