package ru.drsk.progserega.inspectionsheet.storages.http;

import ru.drsk.progserega.inspectionsheet.activities.IProgressListener;

public interface IRemoteStorage {

    void loadTrasformerSubstations();

    void loadSubstations();

    void setProgressListener(IProgressListener progressListener);
}
