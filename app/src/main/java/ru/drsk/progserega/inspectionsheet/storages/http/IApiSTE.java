package ru.drsk.progserega.inspectionsheet.storages.http;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.SteTPResponse;

public interface IApiSTE {
    @GET("/index.php")
    Call<SteTPResponse> getData(@Query("r") String tp);

//    http://api-ste.rs.int/index.php?r=api/all-tp-by-range&offset=1&limit=10


}
