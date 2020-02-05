package ru.drsk.progserega.inspectionsheet.storages.http;

import android.util.Log;

import java.io.IOException;


import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.ByteString;

public class ChunkedInterceptor2  implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        Response response = chain.proceed(request);

        Buffer buffer = new Buffer();
        RequestBody original = request.body() ;
        original.writeTo(buffer);
        ByteString bytes = buffer.snapshot();
        ResponseBody fixedLength = ResponseBody.create( original.contentType(), bytes);
        return response.newBuilder().body(fixedLength).build();
    }
}
