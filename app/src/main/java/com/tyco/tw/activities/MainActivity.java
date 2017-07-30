package com.tyco.tw.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import com.tyco.tw.R;
import com.tyco.tw.fragments.SigninFragment;
import com.tyco.tw.fragments.TasksFragment;
import com.tyco.tw.models.Task;

public class MainActivity extends BaseActivity implements
        SigninFragment.OnFragmentInteractionListener,
        TasksFragment.OnListFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onShowSignIn();
    }


    public void onShowSignIn() {
        SigninFragment signinFragment = new SigninFragment();
        addFragment(R.id.mainContainer, signinFragment, SigninFragment.class.getSimpleName());
    }


    public void onShowTasks() {
        TasksFragment tasksFragment = new TasksFragment();
        replaceFragment(false, R.id.mainContainer, tasksFragment, TasksFragment.class.getSimpleName());
    }


    public void onShowDialog(String title, String message) {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(android.R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }


    public void onFragmentInteraction(Uri uri) {}

    public void onListFragmentInteraction(Task task) {}
}
