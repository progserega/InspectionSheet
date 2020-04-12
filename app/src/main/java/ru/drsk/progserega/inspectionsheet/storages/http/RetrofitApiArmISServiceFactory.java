package ru.drsk.progserega.inspectionsheet.storages.http;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.security.cert.X509Certificate;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.drsk.progserega.inspectionsheet.storages.ISettingsStorage;

public class RetrofitApiArmISServiceFactory {

    private  Retrofit retrofit;

    private OkHttpClient client;

    private  HttpLoggingInterceptor interceptor;



    public RetrofitApiArmISServiceFactory(ISettingsStorage settingsStorage) {
        interceptor = new HttpLoggingInterceptor()
               .setLevel(HttpLoggingInterceptor.Level.BASIC);
               // .setLevel(HttpLoggingInterceptor.Level.BODY);


        client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                //.addInterceptor(chunkedInterceptor)

                .addInterceptor(new LogJsonInterceptor())
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();



        retrofit = build(settingsStorage.loadSettings().getServerUrl());
    }

    private Retrofit build(String serverUrl){
       return  new Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(serverUrl) //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();
    }

    public void setBaseUrl(String serverUrl){
        retrofit = build(serverUrl);
    }

    public IApiInspectionSheet create(){
        return retrofit.create(IApiInspectionSheet.class);
    }
}
