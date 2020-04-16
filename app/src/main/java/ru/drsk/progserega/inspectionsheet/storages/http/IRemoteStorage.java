package ru.drsk.progserega.inspectionsheet.storages.http;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectedLine;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineInspection;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.IProgressListener;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TransformerInspection;

public interface IRemoteStorage {

    void clearStorage();

    void loadOrganization();

    void loadLines(long resId);

//    @Deprecated
//    void loadRemoteData();

    void loadTP();

    void loadSubstations();

    void setProgressListener(IProgressListener progressListener);

    void exportTransformersInspections(List<TransformerInspection> transformerInspections);

    void exportLinesInspections( List< InspectedLine > inspectedLines, long resId);

    void setServerUrls(List<String> serverUrls);

    void loadDeffectTypes();

    void selectActiveServer();
}
