package ru.drsk.progserega.inspectionsheet.storages.http;

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
import retrofit2.http.Path;
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

    /**
     * Запрашивает список СП
     * @return
     */
    @GET("/api/allsp")
    Call< List< SpModel > > getAllSp();

    //не используется
    @Deprecated
    @GET("/api/allsp")
    Observable< List< SpModel > > getAllSpRx();

    /**
     * Запрашивает список всех РЭС
     * @return
     */
    @GET("/api/allres")
    Call< List< ResModel > > getAllRes();

    //Не используется
    @Deprecated
    @GET("/api/allres")
    Observable< List< ResModel > > getAllResRx();

    /**
     * Список типов трансформаторов подстанций
     * @return
     */
    @GET("/api/substations/transformers/types")
    Call< List< SubstationTransformerType > > getSubstationTransformersTypes();

    /**
     * Список типов трансформаторов ТП
     * @return
     */
    @GET("/api/tp/transformers/types")
    Call< List< TransformerType > > geTpTransformersTypes();


    //------------- справочники типов деффектов ------------------------

    /**
     * Типы дефектов опор
     * @return
     */
    @GET("/api/deffects/towers")
    Call< List< TowerDeffectTypesJson > > getTowerDeffectsTypes();

    /**
     * Типы дефектов пролетов линий
     * @return
     */
    @GET("/api/deffects/sections")
    Call< List< SectionDeffectTypesJson > > getSectionDeffectsTypes();

    /**
     * Получаем Типы дефектов оборудования подстанций и ТП
     * @return
     */
    @GET("/api/deffects/transformers")
    Call< List< StationDeffectTypesJson > > getTransformersDeffectsTypes();

    /**
     * Лишний метод! сейчас все дефекты загружаются  getTransformersDeffectsTypes();
     * Получаем Типы дефектов оборудования подстанций и ТП
     * @return
     */

    @GET("/api/deffects/transformers")
    //заменить на /api/deffects/station
    Call< List< StationDeffectTypesJson > > getStationDeffectsTypes();

    /**
     * Получаем описание дефектов (справочная информация)
     * @return
     */
    @GET("/api/all/deffects/descriptions")
    Call< List< DeffectDescriptionJson > > getDeffectDescriptions();

    /**
     * Скачивание файла с сервера используется для картинок
     * @param fileUrl
     * @return
     */
    @GET
    @Streaming
    Call< ResponseBody > downloadFileWithDynamicUrl(@Url String fileUrl);

    /**
     * Получение списка подстанций
     * @param spId  СП
     * @param resId  РЭС
     * @param offset смещение в БД
     * @param limit  количество записей которые получать за раз
     * @return
     */
    @GET("/api/substations")
    Call< SubstationsResponse > getSubstations(@Query("sp_id") long spId, @Query("res_id") long resId, @Query("offset") int offset, @Query("limit") int limit);

    /**
     * Получение списка ТП
     * @param spId    СП
     * @param resId   РЭС
     * @param offset смещение в БД
     * @param limit  количество записей которые получать за раз
     * @return
     */
    @GET("/api/tp")
    Call< TPResponse > getTP(@Query("sp_id") long spId, @Query("res_id") long resId, @Query("offset") int offset, @Query("limit") int limit);


    /**
     * Отправка осмотра трансформатора
     * @param transformerInspectionResult модель осмотра отправляется как JSON в боди
     * @return
     */
    @POST("/api/inspection")
    Call< UploadRes > uploadInspection(@Body TransformerInspectionResult transformerInspectionResult);

    /**
     * отправка картинки на сервер Фотография дефекта
     * @param file        файл
     * @param fileInfo    JSON с информацией о картинке
     * @return
     */
    @Multipart
    @POST("/api/inspection/image")
    Call< UploadRes > uploadInspectionImage(@Part MultipartBody.Part file, @Part("file_info") RequestBody fileInfo);

    /**
     * отправка картинки на сервер Фотография оборудования
     * @param file        файл
     * @param fileInfo    JSON с информацией о картинке
     * @return
     */
    @Multipart
    @POST("/api/station/image")
    Call< UploadRes > uploadStationImage(@Part MultipartBody.Part file, @Part("file_info") RequestBody fileInfo);

    /**
     * Отправка информации о трансформаторе
     * @param transformerInspectionResult
     * @return
     */
    @POST("/api/transformer/info")
    Call< UploadRes > uploadTransformerInfo(@Body TransformerInfo transformerInspectionResult);

    /**
     * Отправка информации о осмотре Подстанции и ТП
     * @param stationInspectionJson
     * @return
     */
    @POST("/api/station/inspection")
    Call< UploadRes > uploadStationInspection(@Body StationInspectionJson stationInspectionJson);

    /**
     * Отправка информации о осмотре линии
     * @param lineInspection
     * @return
     */
    @POST("/api/line/inspection")
    Call< UploadRes > uploadLineInspection(@Body LineInspectionJson lineInspection);

    /**
     * Отправка информации о осмотре опоры кто и когда для получени ID осмотра
     * @param towerInspectionJson
     * @return
     */
    @POST("/api/line/tower/inspection")
    Call< UploadRes > uploadLineTowerInspection(@Body TowerInspectionJson towerInspectionJson);

    /**
     * Отправка информации о опоре материал, тип, координаты
     * @param towerJson
     * @return
     */
    @POST("/api/line/tower")
    Call< UploadRes > uploadLineTowerInfo(@Body TowerJson towerJson);

    /**
     * Отправка информации о дефектах опоры
     * @param towerDeffectsJson
     * @return
     */
    @POST("/api/line/tower/deffects")
    Call< UploadRes > uploadLineTowerDeffects(@Body TowerDeffectsJson towerDeffectsJson);

    /**
     * Отправка фотографии осмотра опоры
     * @param file          файл
     * @param fileInfo     JSON с информацией о файле
     * @return
     */
    @Multipart
    @POST("/api/line/tower/inspection/image")
    Call< UploadRes > uploadTowerInspectionImage(@Part MultipartBody.Part file, @Part("file_info") RequestBody fileInfo);

    /**
     * Отправка информации о пролете
     * @param sectionJson
     * @return
     */
    @POST("/api/line/section")
    Call< UploadRes > uploadLineSectionInfo(@Body SectionJson sectionJson);

    /**
     * Отправка информации о осмотре пролета
     * @param sectionInspectionJson
     * @return
     */
    @POST("/api/line/section/inspection")
    Call< UploadRes > uploadLineSectionInspection(@Body SectionInspectionJson sectionInspectionJson);


    /**
     * Отправка дефектов пролета
     * @param sectionDeffectsJson
     * @return
     */

    @POST("/api/line/section/deffects")
    Call< UploadRes > uploadLineSectionDeffects(@Body SectionDeffectsJson sectionDeffectsJson);

    /**
     * Отправка фотографий осмотра пролета
     * @param file
     * @param fileInfo
     * @return
     */
    @Multipart
    @POST("/api/line/section/inspection/image")
    Call< UploadRes > uploadSectionInspectionImage(@Part MultipartBody.Part file, @Part("file_info") RequestBody fileInfo);

    /**
     * Запрос который говорит серверу что передача данных осмотра линии завершена
     * @param lineUid
     * @param inspectionId
     * @return
     */
    @POST("/api/line/{lineUid}/inspection/{inspectionId}/finalize")
    Call< UploadRes > finalizeLineInspection(@Path("lineUid") Long lineUid, @Path("inspectionId") Long inspectionId);

    /**
     * Получение списка линий
     * @param resId     РЭС
     * @param offset    смещение
     * @param limit     количество записей
     * @return
     */
   // @Headers("Accept-Encoding: identity")
    @GET("/api/lines")
    Call< LinesResponseJson > getLines(@Query("res_id") long resId, @Query("offset") int offset, @Query("limit") int limit);

    /**
     * получение текущей версии приложения
     * @return
     */
    @GET("/api/appversion")
    Call< AppVersionJson > getVersion();


}
