package com.tyco.tw.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;


/**
 * Created by jamesharnedy on 28/07/2017.
 */

public class Tasks {
    @SerializedName("todo-items")
    public List<Task> todoItems;
}
