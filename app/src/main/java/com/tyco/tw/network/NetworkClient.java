package com.tyco.tw.network;

import java.util.concurrent.TimeUnit;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by jamesharnedy on 28/07/2017.
 */
public class NetworkClient {

    private Retrofit rest;
    private OkHttpClient.Builder httpClient;
    private Dispatcher mDispatcher = new Dispatcher();


    public NetworkClient() {
        httpClient = new OkHttpClient.Builder()
                .dispatcher(mDispatcher)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS);
    }


    public ApiService getApiService(String endPointUrl) {
        rest = new Retrofit.Builder()
                .baseUrl(endPointUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        return rest.create(ApiService.class);
    }
}
