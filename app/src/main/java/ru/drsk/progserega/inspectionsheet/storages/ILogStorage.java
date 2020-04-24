package ru.drsk.progserega.inspectionsheet.storages;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.storages.sqlight.LogStorage;
import ru.drsk.progserega.inspectionsheet.storages.sqlight.entities.LogModel;

public interface ILogStorage {

    void add(int level, String tag, String message);

    void getMessages(final LogStorage.IDataFetchedListener listener);

    List<LogModel> getAllMessages();

}
