package ru.drsk.progserega.inspectionsheet.entities;

public class Settings {

    //наименования полей в хранилище
    public static final String FIO = "fio";
    public static final String POSITION = "position";
    public static final String RES_ID = "res_id";
    public static final String SERVER_URL = "server_url";
    public static final String SERVER_URL_VALUE = "http://arm-is.prim.drsk.ru";

    public static final String SERVER_ALT_URL = "server_alt_url";
    public static final String SERVER_ALT_URL_VALUE = "http://172.21.251.98";

    private String fio;
    private String position;
    private int resId;
    private String serverUrl;
    private String serverAltUrl;


    public Settings(String fio, String position, int resId, String serverUrl, String serverAltUrl) {
        this.fio = fio;
        this.position = position;
        this.resId = resId;
        this.serverUrl = serverUrl;
        this.serverAltUrl = serverAltUrl;
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

    public String getServerAltUrl() {
        return serverAltUrl;
    }

    public void setServerAltUrl(String serverAltUrl) {
        this.serverAltUrl = serverAltUrl;
    }
}
