package ru.drsk.progserega.inspectionsheet.storages.http.api_is_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppVersionJson {
    @SerializedName("code")
    @Expose
    private int code;

    @SerializedName("version_name")
    @Expose
    private String versionName;

    @SerializedName("file_name")
    @Expose
    private String fileName;

    @SerializedName("description")
    @Expose
    private String description;


    public AppVersionJson(int code, String versionName, String fileName, String description) {
        this.code = code;
        this.versionName = versionName;
        this.fileName = fileName;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
