package com.tyco.tw.callbacks;

import com.tyco.tw.models.Tasks;


/**
 * Created by jamesharnedy on 28/07/2017.
 */

public interface TasksCallback extends BaseCallback {
    void tasksSuccess(Tasks tasks);
}
