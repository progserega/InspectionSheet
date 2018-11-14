package ru.drsk.progserega.inspectionsheet.storages;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;

import ru.drsk.progserega.inspectionsheet.entities.Line;
import ru.drsk.progserega.inspectionsheet.entities.LineTower;
import ru.drsk.progserega.inspectionsheet.entities.Point;
import ru.drsk.progserega.inspectionsheet.entities.Tower;
import ru.drsk.progserega.inspectionsheet.entities.Voltage;
import ru.drsk.progserega.inspectionsheet.services.EquipmentService;
import ru.drsk.progserega.inspectionsheet.services.ILocation;

public class LineStorageStub implements ILineStorage {

    private ILocation locationService;

    private ArrayList<Line> lines;

    public ArrayList<Line> getLines() {
        return lines;
    }

    public void setLines(ArrayList<Line> lines) {
        this.lines = lines;
    }

    public LineStorageStub(ILocation locationService) {
        this.locationService = locationService;

        lines = new ArrayList<>();

        List<LineTower> towers = new ArrayList<>();
        lines.add(new Line(1, "test 1 VL_04KV", Voltage.VL_04KV, towers));
        lines.add(new Line(1, "test 2 VL_04KV", Voltage.VL_04KV, towers));
        lines.add(new Line(1, "test 3 VL_04KV", Voltage.VL_04KV, towers));
        lines.add(new Line(1, "test 4 VL_04KV", Voltage.VL_04KV, towers));
        lines.add(new Line(1, "test 5 VL_04KV", Voltage.VL_04KV, towers));
        lines.add(new Line(1, "test 6 VL_04KV", Voltage.VL_04KV, towers));

        lines.add(new Line(1, "ololo 1 VL_04KV", Voltage.VL_04KV, towers));
        lines.add(new Line(1, "ololo 2 VL_04KV", Voltage.VL_04KV, towers));
        lines.add(new Line(1, "ololo 3 VL_04KV", Voltage.VL_04KV, towers));
        lines.add(new Line(1, "ololo 4 VL_04KV", Voltage.VL_04KV, towers));
        lines.add(new Line(1, "ololo 5 VL_04KV", Voltage.VL_04KV, towers));
        lines.add(new Line(1, "ololo 6 VL_04KV", Voltage.VL_04KV, towers));

        lines.add(new Line(1, "babababa 1 VL_04KV", Voltage.VL_04KV, towers));
        lines.add(new Line(1, "babababa 2 VL_04KV", Voltage.VL_04KV, towers));
        lines.add(new Line(1, "babababa 3 VL_04KV", Voltage.VL_04KV, towers));
        lines.add(new Line(1, "babababa 4 VL_04KV", Voltage.VL_04KV, towers));
        lines.add(new Line(1, "babababa 5 VL_04KV", Voltage.VL_04KV, towers));
        lines.add(new Line(1, "babababa 6 VL_04KV", Voltage.VL_04KV, towers));

        lines.add(new Line(1, "qwerty 1 VL_04KV", Voltage.VL_04KV, towers));
        lines.add(new Line(1, "qwerty 2 VL_04KV", Voltage.VL_04KV, towers));
        lines.add(new Line(1, "qwerty 3 VL_04KV", Voltage.VL_04KV, towers));
        lines.add(new Line(1, "qwerty 4 VL_04KV", Voltage.VL_04KV, towers));
        lines.add(new Line(1, "qwerty 5 VL_04KV", Voltage.VL_04KV, towers));
        lines.add(new Line(1, "qwerty 6 VL_04KV", Voltage.VL_04KV, towers));

        lines.add(new Line(1, "test 2 VL_6_10KV", Voltage.VL_6_10KV, towers));
        lines.add(new Line(1, "test 2 VL_6_10KV", Voltage.VL_6_10KV, towers));
        lines.add(new Line(1, "test 2 VL_6_10KV", Voltage.VL_6_10KV, towers));
        lines.add(new Line(1, "test 2 VL_6_10KV", Voltage.VL_6_10KV, towers));
        lines.add(new Line(1, "test 2 VL_6_10KV", Voltage.VL_6_10KV, towers));
        lines.add(new Line(1, "test 2 VL_6_10KV", Voltage.VL_6_10KV, towers));

        lines.add(new Line(1, "test 3 VL_35_110KV", Voltage.VL_35_110KV, towers));
        lines.add(new Line(1, "test 3 VL_35_110KV", Voltage.VL_35_110KV, towers));
        lines.add(new Line(1, "test 3 VL_35_110KV", Voltage.VL_35_110KV, towers));
        lines.add(new Line(1, "test 3 VL_35_110KV", Voltage.VL_35_110KV, towers));
        lines.add(new Line(1, "test 3 VL_35_110KV", Voltage.VL_35_110KV, towers));
        lines.add(new Line(1, "test 3 VL_35_110KV", Voltage.VL_35_110KV, towers));
    }

    public static ArrayList<Line> initLinesWithTowersStub() {
        ArrayList<Line> lines = new ArrayList<>();

        Tower t1 = new Tower(1, new Point(1, 1));
        Tower t2 = new Tower(2, new Point(2, 1));
        Tower t3 = new Tower(3, new Point(2, 2));
        Tower t4 = new Tower(4, new Point(1, 2));
        Tower t5 = new Tower(5, new Point(1, 3));
        Tower t6 = new Tower(6, new Point(1, 4));
        Tower t7 = new Tower(7, new Point(4, 4));
        Tower t8 = new Tower(8, new Point(5, 1));
        Tower t9 = new Tower(9, new Point(5, 5));
        Tower t10 = new Tower(10, new Point(2, 5));

        Line l1 = new Line(1, "line 1", Voltage.VL_04KV, null);
        Line l2 = new Line(2, "line 2", Voltage.VL_04KV, null);
        Line l3 = new Line(3, "line 3", Voltage.VL_04KV, null);

        List<LineTower> l1Towers = new ArrayList<>();
        l1Towers.add(new LineTower(l1, t1, "1-1"));
        l1Towers.add(new LineTower(l1, t2, "1-2"));
        l1Towers.add(new LineTower(l1, t8, "1-3"));
        l1.setTowers(l1Towers);

        List<LineTower> l2Towers = new ArrayList<>();
        l2.setTowers(l2Towers);
        l2Towers.add(new LineTower(l2, t2, "2-1"));
        l2Towers.add(new LineTower(l2, t3, "2-2"));
        l2Towers.add(new LineTower(l2, t7, "2-3"));
        l2Towers.add(new LineTower(l2, t9, "2-4"));


        List<LineTower> l3Towers = new ArrayList<>();
        l3.setTowers(l3Towers);
        l3Towers.add(new LineTower(l3, t3, "3-1"));
        l3Towers.add(new LineTower(l3, t4, "3-2"));
        l3Towers.add(new LineTower(l3, t5, "3-3"));
        l3Towers.add(new LineTower(l3, t6, "3-4"));
        l3Towers.add(new LineTower(l3, t10, "3-5"));


        lines.add(l1);
        lines.add(l2);
        lines.add(l3);


        return lines;
    }
//    @Override
//    public ArrayList<Line> getLinesByType(Voltage voltage) {
//
//        ArrayList<Line> result = new ArrayList<>();
//        for (Line line : lines) {
//            if (line.getVoltage().equals(voltage)) {
//                result.add(line);
//            }
//        }
//
//        return result;
//    }
//
//    @Override
//    public ArrayList<Line> getLinesByTypeAndName(Voltage voltage, String name) {
//
//        ArrayList<Line> result = new ArrayList<>();
//        for (Line line : lines) {
//            if (line.getVoltage().equals(voltage) && line.getName().toLowerCase().contains(name.toLowerCase())) {
//                result.add(line);
//            }
//        }
//
//        return result;
//    }

    @Override
    public List<Line> getByFilters(Map<String, Object> filters) {


        Voltage voltage = (Voltage) filters.get(EquipmentService.FILTER_VOLTAGE);
        boolean useVoltage = voltage != null;

        String name = (String) filters.get(EquipmentService.FILTER_NAME);
        boolean useName = name != null;

        Point center = (Point) filters.get(EquipmentService.FILTER_POSITION);
        boolean usePosition = center != null;


        List<Line> result = new ArrayList<>();
        for (Line line : lines) {
            Voltage lineVoltage = line.getVoltage();
            String lineName = line.getName();

            if(!useVoltage && !useName){
                result.add(line);
                continue;
            }

            boolean satisfyVoltage = false;
            if(useVoltage && lineVoltage.equals(voltage)){
                satisfyVoltage = true;
            }

            boolean satisfyName = false;
            if(useName && lineName.toLowerCase().contains(name.toLowerCase())){
                satisfyName = true;
            }

            if(useVoltage && useName){
                if(satisfyVoltage && satisfyName){
                    result.add(line);
                }
                continue;
            }

            if(useVoltage && satisfyVoltage){
                result.add(line);
            }

            if(useName && satisfyName){
                result.add(line);
            }
        }

        if(usePosition){
            Float radius =  (Float) filters.get(EquipmentService.FILTER_POSITION_RADIUS);
            radius = (radius != null)? radius : locationService.defaultSearchRadius();
            result = filterByPoint(result, center, radius);
        }

        return result;


    }

    private List<Line> filterByPoint(List<Line> sourceLines, Point center, float radius) {
        List<Line> lines = new ArrayList<>();

        for (Line line : sourceLines) {
            for (LineTower lineTower : line.getTowers()) {
                if (locationService.distanceBetween(lineTower.getTower().getMapPoint(), center) <= radius) {
                    lines.add(line);
                    break;
                }
            }
        }

        return lines;
    }

    @Override
    public Line getById(long id) {

        for (Line line: lines){
            if(line.getId() == id){
                return line;
            }
        }
        return null;
    }
}
