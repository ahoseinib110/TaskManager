package com.example.taskmanager.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.taskmanager.R;
import com.example.taskmanager.fragment.LoginFragment;

public abstract class SingleFragmentActivity extends AppCompatActivity {

    private static final String TAG = "bashir_SFA";

    public abstract Fragment createFragment();
    @LayoutRes
    public abstract int getLayoutResId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"single onResum");
        FragmentManager fragmentManager = getSupportFragmentManager();
        //LoginFragment loginFragment = (LoginFragment) fragmentManager.findFragmentById(R.id.fragment_container);
        fragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container,createFragment())
                .commit();
    }
}
