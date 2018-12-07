package ru.drsk.progserega.inspectionsheet.storages.http;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.GeoSubstationsResponse;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.SteTPResponse;

public interface IApiSTE {

    @GET("/index.php")
    Call<SteTPResponse> getTPbyRange(@Query("r") String apiMethod, @Query("offset") int offset, @Query("limit") int limit);
}
