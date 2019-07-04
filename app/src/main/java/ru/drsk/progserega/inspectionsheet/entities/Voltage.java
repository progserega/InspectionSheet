package ru.drsk.progserega.inspectionsheet.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public enum Voltage {
    V_04KV("0.4кВ"),
    V_6_10KV("6-10кВ"),
    V_35_110KV("35-110кВ"),
    V_6KV("6кВ"),
    V_10KV("10кВ"),
    V_35KV("35кВ"),
    V_110KV("110кВ");

    private String voltage;

    Voltage(String voltage) {
        this.voltage = voltage;
    }

    public String getVoltage() {
        return voltage;
    }

    public static ArrayList<String> names() {
        Voltage[] voltages = values();
        ArrayList<String> names = new ArrayList<>();
        for (int i = 0; i < voltages.length; i++) {
            names.add(voltages[i].getVoltage());
        }

        return names;
    }

    //Lookup table
    private static final Map<String, Voltage> lookup = new HashMap<>();

    //Populate the lookup table on loading time
    static
    {
        for(Voltage voltage1 : Voltage.values())
        {
            lookup.put(voltage1.getVoltage(), voltage1);
        }
    }

    //This method can be used for reverse lookup purpose
    public static Voltage get(String voltageValue)
    {
        return lookup.get(voltageValue);
    }

    public static Voltage fromVolt(int valuesV)
    {
        switch (valuesV){
            case 400:
                return Voltage.V_04KV;
            case 6000:
                return Voltage.V_6KV;
            case 10000:
                return Voltage.V_10KV;
            case 35000:
                return Voltage.V_35KV;
            case 110000:
                return Voltage.V_110KV;
        }

        return null;
    }

    public int getValueVolt(){
        switch (voltage){
            case "0.4кВ":
                return 400;
            case "6кВ":
                return 6000;
            case "10кВ":
                return 10000;
            case "35кВ":
                return 35000;
            case "110кВ":
                return 110000;
        }
        return 0;
    }



}
