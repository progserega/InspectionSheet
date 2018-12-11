package ru.drsk.progserega.inspectionsheet.entities;

public enum EquipmentType {
    LINE(0),
    SUBSTATION(1),
    TRANS_SUBSTATION(2),
    TOWER(3),
    TRANSFORMER(4);

    private final int value;
    private EquipmentType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static EquipmentType fromInt(int _id)
    {
        EquipmentType[] As = EquipmentType.values();
        for(int i = 0; i < As.length; i++)
        {
            if(As[i].Compare(_id))
                return As[i];
        }
        return null;
    }
    public boolean Compare(int i){return value == i;}
}
