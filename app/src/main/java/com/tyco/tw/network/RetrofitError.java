package com.tyco.tw.network;

/**
 * Created by jamesharnedy on 28/07/2017.
 */

public interface RetrofitError {
    String getMessage();
    void setMessage(String message);
    int getErrorCode();
    void setErrorCode(int code);
}
