package ru.drsk.progserega.inspectionsheet.entities;

/**
 * Опора пренадлежащая линии
 */
public class LineTower {
    private Line line;      //ссылка на линию
    private Tower tower;    //ссылка на опору
    private String number;  //номер опоры в линии

    public Line getLine() {
        return line;
    }

    public Tower getTower() {
        return tower;
    }

    public String getNumber() {
        return number;
    }

    public LineTower(Line line, Tower tower, String number){
        this.line = line;
        this.tower = tower;
        this.number = number;
    }
}
