package ru.drsk.progserega.inspectionsheet.entities;

public class Settings {

    //наименования полей в хранилище
    public static final String FIO = "fio";
    public static final String POSITION = "position";
    public static final String RES_ID = "res_id";
    public static final String SERVER_URL = "server_url";
    public static final String SERVER_URL_VALUE = "http://arm-is.prim.drsk.ru";

    private String fio;
    private String position;
    private int resId;
    private String serverUrl;


    public Settings(String fio, String position, int resId, String serverUrl) {
        this.fio = fio;
        this.position = position;
        this.resId = resId;
        this.serverUrl = serverUrl;
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

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }
}
