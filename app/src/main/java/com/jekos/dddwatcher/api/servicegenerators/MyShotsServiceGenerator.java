package com.jekos.dddwatcher.api.servicegenerators;

import com.jekos.dddwatcher.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by жекос on 21.08.2017.
 */

public class MyShotsServiceGenerator {
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(getClientLogger().build())
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    public static  <S> S createService(Class<S> serviceClass)
    {
        return  retrofit.create(serviceClass);
    }

    private static OkHttpClient.Builder getClientLogger()
    {
        HttpLoggingInterceptor loger = new HttpLoggingInterceptor();
        loger.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(loger);
        return httpClient;
    }
}
