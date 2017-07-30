package com.tyco.tw.network;

import com.tyco.tw.TasksApplication;
import com.tyco.tw.callbacks.AddTaskCallback;
import com.tyco.tw.callbacks.AddTaskResponseCallback;
import com.tyco.tw.callbacks.TasksCallback;
import com.tyco.tw.callbacks.TasksResponseCallback;
import com.tyco.tw.models.AddTask;
import com.tyco.tw.models.Tasks;
import com.tyco.tw.utils.Utils;

import retrofit2.Call;

/**
 * Created by jamesharnedy on 28/07/2017.
 */

public class TWApiService {

    public static void tasks(String username, String password, TasksCallback callback) {
        // TODO: NOTE: Replaced user/pwd with encoded api key to simplify sign-in for sample proj.
        // Could use an interceptor to handle the api-key
        Call<Tasks> call = TasksApplication.getClient(Utils.TW_BASE_URI).tasks(Utils.API_KEY);
        call.enqueue(new TasksResponseCallback(callback));
    }

    public static void addTask(AddTask newTask, AddTaskCallback callback) {
        Call<Void> call = TasksApplication.getClient(Utils.TW_BASE_URI).addTask(Utils.API_KEY, newTask);
        call.enqueue(new AddTaskResponseCallback(callback));
    }
}
