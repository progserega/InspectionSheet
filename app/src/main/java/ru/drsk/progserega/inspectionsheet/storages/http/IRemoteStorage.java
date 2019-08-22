package ru.drsk.progserega.inspectionsheet.storages.http;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectedLine;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineInspection;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.IProgressListener;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TransformerInspection;

public interface IRemoteStorage {

    void loadRemoteData();

    void setProgressListener(IProgressListener progressListener);

    void exportTransformersInspections(List<TransformerInspection> transformerInspections);

    void exportLinesInspections( List< InspectedLine > inspectedLines);
}
