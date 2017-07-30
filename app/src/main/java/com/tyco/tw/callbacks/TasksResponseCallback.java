package com.tyco.tw.callbacks;

import android.util.Log;

import com.tyco.tw.models.Tasks;
import com.tyco.tw.network.RetrofitError;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jamesharnedy on 28/07/2017.
 */

public class TasksResponseCallback implements Callback<Tasks> {

    private static final String TAG = TasksResponseCallback.class.getSimpleName();

    private TasksCallback callback;


    public TasksResponseCallback(TasksCallback callback){
        this.callback = callback;
    }


    public void successfulResponse(BaseCallback callback, Response<Tasks> response, Call<Tasks> call) {
        Log.d(TAG, "response: " + response.body());

        this.callback.tasksSuccess(response.body());
    }


    @Override
    public void onResponse(Call<Tasks> call, Response<Tasks> response) {
        if (response.isSuccessful()) {
            Log.d(TAG, "Response: " + response.body());

            successfulResponse(callback, response, call);
        } else {
            Log.d(TAG, "Response error");

            onFailure(call, new Throwable(response.message()));
        }
    }


    @Override
    public void onFailure(Call<Tasks> call, Throwable t) {
        Log.d(TAG, "Response failure " + t.getMessage());

        callback.requestFailure(new RetrofitError() {
            @Override
            public String getMessage() {
                return null;
            }

            @Override
            public void setMessage(String message) {

            }

            @Override
            public int getErrorCode() {
                return 0;
            }

            @Override
            public void setErrorCode(int code) {

            }
        }, t);
    }
}
