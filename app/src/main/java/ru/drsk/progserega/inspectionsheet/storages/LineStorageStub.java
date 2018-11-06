package ru.drsk.progserega.inspectionsheet.storages;

import java.util.ArrayList;
import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.Line;
import ru.drsk.progserega.inspectionsheet.entities.Voltage;

public class LineStorageStub implements ILineStorage {

    private  ArrayList<Line>  lines;

    public ArrayList<Line> getLines() {
        return lines;
    }

    public void setLines(ArrayList<Line> lines) {
        this.lines = lines;
    }

    public LineStorageStub(){
        lines = new ArrayList<>();

        lines.add(new Line(1, "test 1 VL_04KV", Voltage.VL_04KV, null));
        lines.add(new Line(1, "test 2 VL_04KV", Voltage.VL_04KV, null));
        lines.add(new Line(1, "test 3 VL_04KV", Voltage.VL_04KV, null));
        lines.add(new Line(1, "test 4 VL_04KV", Voltage.VL_04KV, null));
        lines.add(new Line(1, "test 5 VL_04KV", Voltage.VL_04KV, null));
        lines.add(new Line(1, "test 6 VL_04KV", Voltage.VL_04KV, null));

        lines.add(new Line(1, "ololo 1 VL_04KV", Voltage.VL_04KV, null));
        lines.add(new Line(1, "ololo 2 VL_04KV", Voltage.VL_04KV, null));
        lines.add(new Line(1, "ololo 3 VL_04KV", Voltage.VL_04KV, null));
        lines.add(new Line(1, "ololo 4 VL_04KV", Voltage.VL_04KV, null));
        lines.add(new Line(1, "ololo 5 VL_04KV", Voltage.VL_04KV, null));
        lines.add(new Line(1, "ololo 6 VL_04KV", Voltage.VL_04KV, null));

        lines.add(new Line(1, "babababa 1 VL_04KV", Voltage.VL_04KV, null));
        lines.add(new Line(1, "babababa 2 VL_04KV", Voltage.VL_04KV, null));
        lines.add(new Line(1, "babababa 3 VL_04KV", Voltage.VL_04KV, null));
        lines.add(new Line(1, "babababa 4 VL_04KV", Voltage.VL_04KV, null));
        lines.add(new Line(1, "babababa 5 VL_04KV", Voltage.VL_04KV, null));
        lines.add(new Line(1, "babababa 6 VL_04KV", Voltage.VL_04KV, null));

        lines.add(new Line(1, "qwerty 1 VL_04KV", Voltage.VL_04KV, null));
        lines.add(new Line(1, "qwerty 2 VL_04KV", Voltage.VL_04KV, null));
        lines.add(new Line(1, "qwerty 3 VL_04KV", Voltage.VL_04KV, null));
        lines.add(new Line(1, "qwerty 4 VL_04KV", Voltage.VL_04KV, null));
        lines.add(new Line(1, "qwerty 5 VL_04KV", Voltage.VL_04KV, null));
        lines.add(new Line(1, "qwerty 6 VL_04KV", Voltage.VL_04KV, null));

        lines.add(new Line(1, "test 2 VL_6_10KV", Voltage.VL_6_10KV, null));
        lines.add(new Line(1, "test 2 VL_6_10KV", Voltage.VL_6_10KV, null));
        lines.add(new Line(1, "test 2 VL_6_10KV", Voltage.VL_6_10KV, null));
        lines.add(new Line(1, "test 2 VL_6_10KV", Voltage.VL_6_10KV, null));
        lines.add(new Line(1, "test 2 VL_6_10KV", Voltage.VL_6_10KV, null));
        lines.add(new Line(1, "test 2 VL_6_10KV", Voltage.VL_6_10KV, null));

        lines.add(new Line(1, "test 3 VL_35_110KV", Voltage.VL_35_110KV, null));
        lines.add(new Line(1, "test 3 VL_35_110KV", Voltage.VL_35_110KV, null));
        lines.add(new Line(1, "test 3 VL_35_110KV", Voltage.VL_35_110KV, null));
        lines.add(new Line(1, "test 3 VL_35_110KV", Voltage.VL_35_110KV, null));
        lines.add(new Line(1, "test 3 VL_35_110KV", Voltage.VL_35_110KV, null));
        lines.add(new Line(1, "test 3 VL_35_110KV", Voltage.VL_35_110KV, null));
    }

    @Override
    public ArrayList<Line> getLinesByType(Voltage voltage) {

        ArrayList<Line>  result = new ArrayList<>();
        for(Line line: lines){
            if(line.getVoltage().equals(voltage)){
                result.add(line);
            }
        }

        return result;
    }

    @Override
    public ArrayList<Line> getLinesByTypeAndName(Voltage voltage, String name) {

        ArrayList<Line>  result = new ArrayList<>();
        for(Line line: lines){
            if(line.getVoltage().equals(voltage) && line.getName().toLowerCase().contains(name.toLowerCase())){
                result.add(line);
            }
        }

        return result;
    }
}
