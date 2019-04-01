package ru.drsk.progserega.inspectionsheet.storages.http;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitApiArmISServiceFactory {

    private  Retrofit retrofit;

    public RetrofitApiArmISServiceFactory() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
                //.setLevel(HttpLoggingInterceptor.Level.BASIC);
                .setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
               // .baseUrl("http://armis.ps:3010") //Базовая часть адреса
                .baseUrl("http://172.21.168.71:3010") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();
    }

    public IApiInspectionSheet create(){
        return retrofit.create(IApiInspectionSheet.class);
    }
}
