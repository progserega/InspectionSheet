package ru.drsk.progserega.inspectionsheet.storages;

public interface ILogStorage {

    void add(int level, String tag, String message);

}
