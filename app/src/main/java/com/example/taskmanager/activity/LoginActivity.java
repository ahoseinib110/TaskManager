package com.example.taskmanager.activity;


import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.taskmanager.R;
import com.example.taskmanager.fragment.LoginFragment;

public class LoginActivity extends SingleFragmentActivity implements LoginFragment.CallBack{

    private static final String TAG = "bashir_LA";
    private String mUserName;
    private String mPassword;

    @Override
    public Fragment createFragment() {
        return LoginFragment.newInstance(mUserName,mPassword);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        if (requestCode == LoginFragment.REQUEST_CODE_SIGN_UP) {
            mUserName = data.getStringExtra(LoginFragment.KEY_USER_NAME);
            mPassword = data.getStringExtra(LoginFragment.KEY_PASSWORD);
            Log.d(TAG,"after callback login "+mUserName+" "+mPassword);
        }
    }

    @Override
    public void startSignUpActivity(String userName, String password) {
        Intent intent = new Intent(this, SignUpActivity.class);
        if (userName!=null) {
            intent.putExtra(LoginFragment.KEY_USER_NAME, String.valueOf(userName));
        }
        if (password!=null) {
            intent.putExtra(LoginFragment.KEY_PASSWORD, String.valueOf(password));
        }
        startActivityForResult(intent, LoginFragment.REQUEST_CODE_SIGN_UP);
    }

    @Override
    public void startTaskManagerActivity(int userId) {
        Intent intent = TaskManagerActivity.newIntent(this, userId);
        startActivity(intent);
    }


}