package ru.drsk.progserega.inspectionsheet.storages.http;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.SteTPModel;

public interface IRemoteDataArrivedListener {
    void SteTPModelsArrived(List<SteTPModel> tpModels);
}
