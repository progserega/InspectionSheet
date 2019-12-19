package ru.drsk.progserega.inspectionsheet.storages.http;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.GeoLineDetailResponse;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.GeoLinesResponse;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.GeoSubstationsResponse;

/**
 * Работа с API карты
 *
 * http://wiki.rs.int/doku.php/osm:api#список_всех_линий
 * http://wiki.rs.int/doku.php/osm:api#данные_линии_по_имени
 *
 */
@Deprecated
public interface IApiGeo {
    @GET("/index.php")
    Call<GeoSubstationsResponse> getAllSubstations(@Query("r") String apiMethod);

    @GET("/index.php")
    Call<GeoLinesResponse> getAllLines(@Query("r") String apiMethod);

    // http://api-geo.rs.int/index.php?r=api/get-line-by-name&name=ВЛ-0,4%20кВ%20Ф-2%20КТП-7076
    @GET("/index.php")
    Call<GeoLineDetailResponse> getLineDetails(@Query("r") String apiMethod, @Query("name") String name);


}
