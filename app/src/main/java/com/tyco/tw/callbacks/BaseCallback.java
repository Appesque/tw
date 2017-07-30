package com.tyco.tw.callbacks;

import com.tyco.tw.network.RetrofitError;

/**
 * Created by jamesharnedy on 28/07/2017.
 */

public interface BaseCallback {
    void requestUnsuccessful(RetrofitError retrofitError);
    void requestFailure(RetrofitError retrofitError, Throwable t);
}
