package com.tyco.tw.controllers;

import com.tyco.tw.models.Task;
import com.tyco.tw.models.Tasks;

import java.util.List;

/**
 * Created by jamesharnedy on 28/07/2017.
 */

public class TasksController {

    private static Tasks mTasks;

    // TODO: Support paging of tasks and optimized retrieval based on certain keyed attrs if needs be


    public static void setTasks(Tasks tasks) {
        mTasks = tasks;
    }

    public static List<Task> getTasks() {
        if (mTasks != null) {
            return mTasks.todoItems;
        }
        return null;
    }
}
