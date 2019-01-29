package ru.drsk.progserega.inspectionsheet.storages.http.api_is_models;

public class UploadObject {
    private String success;
    public UploadObject(String success) {
        this.success = success;
    }
    public String getSuccess() {
        return success;
    }
}
