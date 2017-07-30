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

public class AddTaskResponseCallback implements Callback<Void> {

    private static final String TAG = AddTaskResponseCallback.class.getSimpleName();

    private AddTaskCallback callback;


    public AddTaskResponseCallback(AddTaskCallback callback){
        this.callback = callback;
    }


    public void successfulResponse(BaseCallback callback, Response<Void> response, Call<Void> call) {
        Log.d(TAG, "response: " + response.body());

        this.callback.addTaskSuccess();
    }


    @Override
    public void onResponse(Call<Void> call, Response<Void> response) {
        successfulResponse(callback, response, call);
    }


    @Override
    public void onFailure(Call<Void> call, Throwable t) {
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
