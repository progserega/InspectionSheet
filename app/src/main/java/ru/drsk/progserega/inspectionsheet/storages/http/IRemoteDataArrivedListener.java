package ru.drsk.progserega.inspectionsheet.storages.http;

import java.util.List;

import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.GeoSubstation;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.SteTPModel;
import ru.drsk.progserega.inspectionsheet.storages.json.models.SubstationTransformerJson;

public interface IRemoteDataArrivedListener {
    void SteTPModelsArrived(List<SteTPModel> tpModels);

    void GeoSubstationsArrived(List<GeoSubstation> substations);

    void SubstationTransformersArrived(List<SubstationTransformerJson> transformers);
}
