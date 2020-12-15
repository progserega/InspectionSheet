package ru.drsk.progserega.inspectionsheet.storages;

public interface IFileStorage {

    void removeAllInspectionsPhotos();

    void removePhoto(String path);

}
