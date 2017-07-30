package com.tyco.tw.activities;


import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


public class BaseActivity extends AppCompatActivity {

    ////////////////////////////////
    // Fragment stack helper methods
    ////////////////////////////////

    void addFragment(@IdRes int containerViewId,
                     @NonNull Fragment fragment,
                     @NonNull String fragmentTag) {

        getSupportFragmentManager()
                .beginTransaction()
                .add(containerViewId, fragment, fragmentTag)
                .commit();
    }

    void replaceFragment(boolean addToBackstack,
                         @IdRes int containerViewId,
                         @NonNull Fragment fragment,
                         @NonNull String fragmentTag) {

        if (addToBackstack) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(containerViewId, fragment, fragmentTag)
                    .addToBackStack(null)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(containerViewId, fragment, fragmentTag)
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("BaseActivity", "onResume()");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d("BaseActivity", "onPause()");
    }
}
