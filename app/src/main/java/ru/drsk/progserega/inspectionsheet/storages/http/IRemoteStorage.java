package ru.drsk.progserega.inspectionsheet.storages.http;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.activities.IProgressListener;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TransformerInspection;

public interface IRemoteStorage {

    void loadRemoteData();

    void setProgressListener(IProgressListener progressListener);

    void uploadTransformersInspections(List<TransformerInspection> transformerInspections);
}
