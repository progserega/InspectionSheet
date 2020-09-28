package ru.drsk.progserega.inspectionsheet.entities;

public enum EquipmentType {
    LINE(0),
    SUBSTATION(1),
    TP(2),
    TOWER(3),
    TRANSFORMER(4),
    LINE_SECTION(5),
    SUBSTATION_TRANSFORMER(6),
    TP_TRANSFORMER(7);

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

    public static EquipmentType getByName(String name){

        switch (name.toUpperCase()){
            case "SUBSTATION":
                return SUBSTATION;
            case "LINE":
                return LINE;
            case "TP":
                return TP;
            case "TOWER":
                return TOWER;
            case "LINE_SECTION":
                return LINE_SECTION;
            case "TRANSFORMER":
                return TRANSFORMER;
            case "SUBSTATION_TRANSFORMER":
                return SUBSTATION_TRANSFORMER;
            case "TP_TRANSFORMER":
                return TP_TRANSFORMER;
            default:
                return null;
        }

    }

    public int getArmObjecTypeId(){
        switch (this) {
            case LINE:
                return -1; //не используется
            case SUBSTATION:
                return 5;
            case TP:
                return 6;
            case TOWER:
                return -2; //не используется
            case TRANSFORMER:
                return 9;
            case LINE_SECTION:
                return -3; //не используется
            case TP_TRANSFORMER:
                return 7;
            case SUBSTATION_TRANSFORMER:
                return 8;
        }

          return 0;

    }
}
