package ru.drsk.progserega.inspectionsheet.storages.http;

import android.util.Pair;

import java.util.List;


import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.AppVersionJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.DeffectDescriptionJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.LineInspectionJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.LinesResponseJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.ResModel;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.SectionDeffectTypesJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.SectionDeffectsJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.SectionInspectionJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.SectionJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.SpModel;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.StationInspectionJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.SubstationTransformerType;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.SubstationsResponse;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.TPResponse;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.TowerDeffectTypesJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.TowerDeffectsJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.TowerInspectionJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.TowerJson;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.StationDeffectTypesJson;
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
    Call< List< SubstationTransformerType > > getSubstationTransformersTypes();

    @GET("/api/tp/transformers/types")
    Call< List< TransformerType > > geTpTransformersTypes();


    //------------- справочники типов деффектов ------------------------
    @GET("/api/deffects/towers")
    Call< List< TowerDeffectTypesJson > > getTowerDeffectsTypes();

    @GET("/api/deffects/sections")
    Call< List< SectionDeffectTypesJson > > getSectionDeffectsTypes();

    @GET("/api/deffects/transformers")
    Call< List< StationDeffectTypesJson > > getTransformersDeffectsTypes();

    @GET("/api/deffects/transformers")
    //заменить на /api/deffects/station
    Call< List< StationDeffectTypesJson > > getStationDeffectsTypes();

    @GET("/api/all/deffects/descriptions")
    Call< List< DeffectDescriptionJson > > getDeffectDescriptions();

    @GET
    @Streaming
    Call< ResponseBody > downloadFileWithDynamicUrl(@Url String fileUrl);


    @GET("/api/substations")
    Call< SubstationsResponse > getSubstations(@Query("sp_id") long spId, @Query("res_id") long resId, @Query("offset") int offset, @Query("limit") int limit);

    @GET("/api/tp")
    Call< TPResponse > getTP(@Query("sp_id") long spId, @Query("res_id") long resId, @Query("offset") int offset, @Query("limit") int limit);

    @POST("/api/inspection")
    Call< UploadRes > uploadInspection(@Body TransformerInspectionResult transformerInspectionResult);

    @Multipart
    @POST("/api/inspection/image")
    Call< UploadRes > uploadInspectionImage(@Part MultipartBody.Part file, @Part("file_info") RequestBody fileInfo);

    @Multipart
    @POST("/api/station/image")
    Call< UploadRes > uploadStationImage(@Part MultipartBody.Part file, @Part("file_info") RequestBody fileInfo);


    @POST("/api/transformer/info")
    Call< UploadRes > uploadTransformerInfo(@Body TransformerInfo transformerInspectionResult);

    @POST("/api/station/inspection")
    Call< UploadRes > uploadStationInspection(@Body StationInspectionJson stationInspectionJson);

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

    @POST("/api/line/section/inspection")
    Call< UploadRes > uploadLineSectionInspection(@Body SectionInspectionJson sectionInspectionJson);

    @POST("/api/line/section/deffects")
    Call< UploadRes > uploadLineSectionDeffects(@Body SectionDeffectsJson sectionDeffectsJson);

    @Multipart
    @POST("/api/line/section/inspection/image")
    Call< UploadRes > uploadSectionInspectionImage(@Part MultipartBody.Part file, @Part("file_info") RequestBody fileInfo);

   // @Headers("Accept-Encoding: identity")
    @GET("/api/lines")
    Call< LinesResponseJson > getLines(@Query("res_id") long resId, @Query("offset") int offset, @Query("limit") int limit);

    @GET("/api/appversion")
    Call< AppVersionJson > getVersion();


}
