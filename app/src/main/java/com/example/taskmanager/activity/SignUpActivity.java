package com.example.taskmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.taskmanager.R;
import com.example.taskmanager.fragment.LoginFragment;
import com.example.taskmanager.fragment.SignUpFragment;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.UserDBRepository;
import com.google.android.material.textfield.TextInputLayout;


public class SignUpActivity extends SingleFragmentActivity {
    String mUsername;
    int mPassword=-1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        mUsername = intent.getStringExtra(LoginFragment.KEY_USER_NAME);
        mPassword = intent.getIntExtra(LoginFragment.KEY_PASSWORD,0);
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
}