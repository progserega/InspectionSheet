package ru.drsk.progserega.inspectionsheet.storages.http;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.SteTPResponse;

public interface IApiSTE {

    @GET("/index.php")
    Call<SteTPResponse> getTPbyRange(@Query("r") String apiMethod, @Query("offset") int offset, @Query("limit") int limit);

    @GET("/index.php")
    Single<SteTPResponse> getTPbyRangeRx(@Query("r") String apiMethod, @Query("offset") int offset, @Query("limit") int limit);
}
