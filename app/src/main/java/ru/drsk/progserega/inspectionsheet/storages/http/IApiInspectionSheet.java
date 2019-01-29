package ru.drsk.progserega.inspectionsheet.storages.http;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.Simple;
import ru.drsk.progserega.inspectionsheet.storages.http.api_is_models.UploadObject;
import ru.drsk.progserega.inspectionsheet.storages.http.ste_models.SteTPResponse;

public interface IApiInspectionSheet {

    @POST("/api/inspection")
    Call<Simple> uploadInspection(@Body Simple simple);

    @Multipart
    @POST("/api/inspection/image")
    Call<UploadObject> uploadFile(@Part MultipartBody.Part file, @Part("file_info") RequestBody fileInfo);
}
