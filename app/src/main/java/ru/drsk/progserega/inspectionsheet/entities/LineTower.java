package ru.drsk.progserega.inspectionsheet.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Опора пренадлежащая линии
 */
public class LineTower {
    private long lineId;
    private Line line;      //ссылка на линию

    private long towerId;
    private Tower tower;    //ссылка на опору
    private String number;  //номер опоры в линии

    private LineTower prevTower;
    private List<LineTower> nextTowers;

    public Line getLine() {
        return line;
    }

    public Tower getTower() {
        return tower;
    }

    public String getNumber() {
        return number;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public void setTower(Tower tower) {
        this.tower = tower;
    }

    public void setPrevTower(LineTower prevTower) {
        this.prevTower = prevTower;
    }

    public void addNextTower(LineTower nextTower){
        nextTowers.add(nextTower);
    }

    public LineTower(Line line, Tower tower, String number) {
        this.line = line;
        this.lineId = line.getId();

        this.tower = tower;
        this.towerId = tower.getId();
        this.number = number;

        this.prevTower = null;
        this.nextTowers = new ArrayList<>();

    }

    public LineTower(long lineId, long towerId, String number) {
        this.lineId = lineId;
        this.towerId = towerId;
        this.number = number;
        this.line = null;
        this.tower = null;

        this.prevTower = null;
        this.nextTowers = new ArrayList<>();

    }
}
