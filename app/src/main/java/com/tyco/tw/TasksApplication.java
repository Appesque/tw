package com.tyco.tw;

import android.app.Application;

import com.tyco.tw.network.ApiService;
import com.tyco.tw.network.NetworkClient;

/**
 * Created by jamesharnedy on 28/07/2017.
 */

public class TasksApplication extends Application {
        private static NetworkClient networkClient = new NetworkClient();


    public static NetworkClient getNetworkClient() {
        return networkClient;
    }


    public static ApiService getClient(String endPointUrl) {
        return networkClient.getApiService(endPointUrl);
    }
}
