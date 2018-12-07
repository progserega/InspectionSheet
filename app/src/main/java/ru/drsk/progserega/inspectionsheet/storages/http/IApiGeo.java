package ru.drsk.progserega.inspectionsheet.storages.http;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.GeoSubstationsResponse;

public interface IApiGeo {
    @GET("/index.php")
    Call<GeoSubstationsResponse> getAllSubstations(@Query("r") String apiMethod);
}
