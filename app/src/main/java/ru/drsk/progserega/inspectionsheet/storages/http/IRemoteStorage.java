package ru.drsk.progserega.inspectionsheet.storages.http;

import android.util.Pair;

import java.util.List;

import io.reactivex.Observer;
import ru.drsk.progserega.inspectionsheet.entities.inspections.IStationInspection;
import ru.drsk.progserega.inspectionsheet.entities.inspections.InspectedLine;
import ru.drsk.progserega.inspectionsheet.entities.inspections.LineInspection;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.AppVersionJson;
import ru.drsk.progserega.inspectionsheet.ui.interfaces.IProgressListener;
import ru.drsk.progserega.inspectionsheet.entities.inspections.TransformerInspection;

public interface IRemoteStorage {

    void clearStorage();

    void loadOrganization();

    void loadLines(long resId);

//    @Deprecated
//    void loadRemoteData();

    void loadTP(long spId, long resId);

    void loadSubstations(long spId, long resId);

    void setProgressListener(IProgressListener progressListener);

    void exportTransformersInspections(List<TransformerInspection> transformerInspections);

    void exportStationsInspections(List< IStationInspection > stationInspections);

    void exportLinesInspections( List< InspectedLine > inspectedLines, long resId);

    void setServerUrls(List<String> serverUrls);

    void loadDeffectTypes();

    void selectActiveServer();

    void getAppVersion(Observer< AppVersionJson > observer);
}
