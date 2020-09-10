package com.example.taskmanager.activity;


import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.taskmanager.R;
import com.example.taskmanager.fragment.LoginFragment;

public class LoginActivity extends SingleFragmentActivity {

    private String mUserName;
    private int mPassword=-1;

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
            mPassword = data.getIntExtra(LoginFragment.KEY_PASSWORD, -1);
        }
    }
}