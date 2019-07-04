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
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.ResModel;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.SpModel;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.TransformerInspectionResult;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.UploadRes;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.TransformerInfo;

public interface IApiInspectionSheet {

    @GET("/api/allsp")
    Call<List<SpModel>> getAllSp();

    @GET("/api/allsp")
    Observable<List<SpModel>> getAllSpRx();

    @GET("/api/allres")
    Call<List<ResModel>> getAllRes();

    @GET("/api/allres")
    Observable<List<ResModel>> getAllResRx();


    @POST("/api/inspection")
    Call<UploadRes> uploadInspection(@Body TransformerInspectionResult transformerInspectionResult);

    @Multipart
    @POST("/api/inspection/image")
    Call<UploadRes> uploadInspectionImage(@Part MultipartBody.Part file, @Part("file_info") RequestBody fileInfo);

    @Multipart
    @POST("/api/transformer/image")
    Call<UploadRes> uploadTransformerImage(@Part MultipartBody.Part file, @Part("file_info") RequestBody fileInfo);


    @POST("/api/transformer/info")
    Call<UploadRes> uploadTransformerInfo(@Body TransformerInfo transformerInspectionResult);

}
