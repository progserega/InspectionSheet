package ru.drsk.progserega.inspectionsheet.entities;

public class Settings {

    //наименования полей в хранилище
    public static final String FIO = "fio";
    public static final String POSITION = "position";
    public static final String RES_ID = "res_id";

    private String fio;
    private String position;
    private int resId;

    public Settings(String fio, String position, int resId) {
        this.fio = fio;
        this.position = position;
        this.resId = resId;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
