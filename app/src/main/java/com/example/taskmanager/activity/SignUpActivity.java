package com.example.taskmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.taskmanager.R;
import com.example.taskmanager.fragment.LoginFragment;
import com.example.taskmanager.fragment.SignUpFragment;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.UserDBRepository;
import com.google.android.material.textfield.TextInputLayout;


public class SignUpActivity extends SingleFragmentActivity implements SignUpFragment.CallBack {
    public static final String EXTRA_USERNAME = "EXTRA_USERNAME";
    public static final String EXTRA_PASSWORD = "EXTRA_PASSWORD";
    private static final String TAG = "bashir_SUA";
    String mUsername;
    String mPassword;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        mUsername = intent.getStringExtra(LoginFragment.KEY_USER_NAME);
        mPassword = intent.getStringExtra(LoginFragment.KEY_PASSWORD);
        super.onCreate(savedInstanceState);
    }

    @Override
    public Fragment createFragment() {
        return SignUpFragment.newInstance(mUsername,mPassword);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void removeFragment(String userName, String password,Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().remove(fragment).commit();
        Intent intent = new Intent();
        intent.putExtra(LoginFragment.KEY_USER_NAME,userName);
        intent.putExtra(LoginFragment.KEY_PASSWORD,password);
        Log.d(TAG,"after callback Login Activity "+userName+"  "+password);
        setResult(RESULT_OK,intent);
        finish();
    }
}