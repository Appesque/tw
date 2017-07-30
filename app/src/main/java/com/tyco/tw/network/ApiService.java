package com.tyco.tw.network;


import com.tyco.tw.models.AddTask;
import com.tyco.tw.models.Tasks;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by jamesharnedy on 28/07/2017.
 */

public interface ApiService {
    @GET(EndPoints.TASKS_URI)
    Call<Tasks> tasks(@Header("Authorization") String api_key);

    @POST(EndPoints.NEW_TASKS_URI)
    Call<Void> addTask(@Header("Authorization") String api_key, @Body AddTask task);
}
