package com.twb.designpatternsplugin.services.github.retrofit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import java.util.concurrent.TimeUnit;

public abstract class BaseHttpService {

    protected <T> T getApi(String endpoint, Class<T> clazz) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(20, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(5, TimeUnit.MINUTES);

        OkHttpClient okHttpClient = httpClientBuilder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(endpoint)
                .build();
        return retrofit.create(clazz);
    }
}
