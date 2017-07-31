package com.tyco.tw.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.tyco.tw.R;
import com.tyco.tw.adapters.TasksRecyclerViewAdapter;
import com.tyco.tw.callbacks.AddTaskCallback;
import com.tyco.tw.callbacks.TasksCallback;
import com.tyco.tw.controllers.TasksController;
import com.tyco.tw.models.AddTask;
import com.tyco.tw.models.Task;
import com.tyco.tw.models.Tasks;
import com.tyco.tw.network.RetrofitError;
import com.tyco.tw.network.TWApiService;
import com.tyco.tw.utils.SimpleDividerDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class TasksFragment extends Fragment implements AddTaskCallback, TasksCallback {

    private static final String TAG = TasksFragment.class.getSimpleName();

    private OnListFragmentInteractionListener mListener;
    private List<Task> mTasks;

    @BindView(R.id.taskList)
    RecyclerView _tasksRV;
    @BindView(R.id.addTaskTIL)
    TextInputLayout _addTaskTIL;
    @BindView(R.id.addTaskET)
    EditText _addTaskET;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TasksFragment() {
    }


    public static TasksFragment newInstance(int columnCount) {
        TasksFragment fragment = new TasksFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tasks_list, container, false);

        setHasOptionsMenu(true);

        ButterKnife.bind(this, view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        _tasksRV.setLayoutManager(layoutManager);
        _tasksRV.addItemDecoration(new SimpleDividerDecoration(view.getContext()));
        mTasks = TasksController.getTasks();
        _tasksRV.setAdapter(new TasksRecyclerViewAdapter(mTasks, mListener));

        _addTaskET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    getActivity().invalidateOptionsMenu();
                }
            }
        });

        _addTaskET.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    processAddTask();
                    return true;
                }
                return false;
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        if (_addTaskET.hasFocus()) {
            inflater.inflate(R.menu.tasks, menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                if (_addTaskET.getText().length() == 0) {
                    // remove Menu Action
                    _tasksRV.requestFocus();
                    getActivity().invalidateOptionsMenu();
                } else {
                    // add task
                    processAddTask();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void processAddTask() {
        if (_addTaskET.getText().length() > 0) {
            AddTask newTask = new AddTask();
            Task newTodo = new Task();
            newTodo.content = _addTaskET.getText().toString();
            newTask.todoItem = newTodo;
            TWApiService.addTask(newTask, this);

            _addTaskET.setText("");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Task task);
        void onShowDialog(String title, String message);
    }


    @Override
    public void requestUnsuccessful(RetrofitError retrofitError) {
        if (isVisible()) {
            mListener.onShowDialog(getString(R.string.error_title), retrofitError.getMessage());
        }
    }

    @Override
    public void requestFailure(RetrofitError retrofitError, Throwable t) {
        if (isVisible()) {
            mListener.onShowDialog(getString(R.string.error_title), retrofitError.getMessage());
        }
    }

    @Override
    public void addTaskSuccess() {
        // TODO: Ignored passing user/pwd as it's a sample app
        TWApiService.tasks("", "", this);
    }

    @Override
    public void tasksSuccess(Tasks tasks) {
        TasksController.setTasks(tasks);
        mTasks.clear();
        mTasks.addAll(TasksController.getTasks());
        _tasksRV.getAdapter().notifyDataSetChanged();
    }
}
