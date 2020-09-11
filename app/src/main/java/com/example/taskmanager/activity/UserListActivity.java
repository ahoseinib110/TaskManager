package com.example.taskmanager.activity;

import androidx.fragment.app.Fragment;

import com.example.taskmanager.R;
import com.example.taskmanager.fragment.UserListFragment;

public class UserListActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return UserListFragment.newInstance();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }
}