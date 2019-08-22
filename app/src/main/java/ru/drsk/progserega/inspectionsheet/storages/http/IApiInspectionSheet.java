package ru.drsk.progserega.inspectionsheet.storages.http;

import java.util.List;


import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.LineInspectionJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.ResModel;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.SectionJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.SpModel;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.SubstationsResponse;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.TowerDeffectsJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.TowerInspectionJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.TowerJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.TransformerInspectionResult;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.TransformerType;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.UploadRes;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.TransformerInfo;

public interface IApiInspectionSheet {

    @GET("/api/allsp")
    Call< List< SpModel > > getAllSp();

    @GET("/api/allsp")
    Observable< List< SpModel > > getAllSpRx();

    @GET("/api/allres")
    Call< List< ResModel > > getAllRes();

    @GET("/api/allres")
    Observable< List< ResModel > > getAllResRx();

    @GET("/api/substations/transformers/types")
    Call< List< TransformerType > > getSubstationTransformersTypes();

    @GET("/api/substations")
    Call< SubstationsResponse > getSubstations(@Query("offset") int offset, @Query("limit") int limit);

    @POST("/api/inspection")
    Call< UploadRes > uploadInspection(@Body TransformerInspectionResult transformerInspectionResult);

    @Multipart
    @POST("/api/inspection/image")
    Call< UploadRes > uploadInspectionImage(@Part MultipartBody.Part file, @Part("file_info") RequestBody fileInfo);

    @Multipart
    @POST("/api/transformer/image")
    Call< UploadRes > uploadTransformerImage(@Part MultipartBody.Part file, @Part("file_info") RequestBody fileInfo);


    @POST("/api/transformer/info")
    Call< UploadRes > uploadTransformerInfo(@Body TransformerInfo transformerInspectionResult);

    //отправка результата осмотра линии
    @POST("/api/line/inspection")
    Call< UploadRes > uploadLineInspection(@Body LineInspectionJson lineInspection);

    @POST("/api/line/tower/inspection")
    Call< UploadRes > uploadLineTowerInspection(@Body TowerInspectionJson towerInspectionJson);

    @POST("/api/line/tower")
    Call< UploadRes > uploadLineTowerInfo(@Body TowerJson towerJson);

    @POST("/api/line/tower/deffects")
    Call< UploadRes > uploadLineTowerDeffects(@Body TowerDeffectsJson towerDeffectsJson);

    @Multipart
    @POST("/api/line/tower/inspection/image")
    Call< UploadRes > uploadTowerInspectionImage(@Part MultipartBody.Part file, @Part("file_info") RequestBody fileInfo);

    @POST("/api/line/section")
    Call< UploadRes > uploadLineSectionInfo(@Body SectionJson sectionJson);

}
