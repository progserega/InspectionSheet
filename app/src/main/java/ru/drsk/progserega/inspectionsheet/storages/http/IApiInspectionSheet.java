package ru.drsk.progserega.inspectionsheet.storages.http;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.TransformerInspectionResult;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.UploadObject;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.UploadRes;

public interface IApiInspectionSheet {

    @POST("/api/inspection")
    Call<UploadRes> uploadInspection(@Body TransformerInspectionResult transformerInspectionResult);

    @Multipart
    @POST("/api/inspection/image")
    Call<UploadObject> uploadFile(@Part MultipartBody.Part file, @Part("file_info") RequestBody fileInfo);
}
