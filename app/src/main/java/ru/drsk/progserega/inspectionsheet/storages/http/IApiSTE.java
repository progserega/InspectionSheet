package ru.drsk.progserega.inspectionsheet.storages.http;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.QueryName;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.SteTPResponse;

public interface IApiSTE {

    @GET("/index.php")
    Call<SteTPResponse> getTPbyRange(@Query("r") String apiMethod, @Query("offset") int offset, @Query("limit") int limit);
}
