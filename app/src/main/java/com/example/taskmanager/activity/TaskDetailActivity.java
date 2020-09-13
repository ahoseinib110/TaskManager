package com.example.taskmanager.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.taskmanager.R;
import com.example.taskmanager.fragment.TaskDetailFragment;
import com.example.taskmanager.model.Task;

public class TaskDetailActivity extends AppCompatActivity implements TaskDetailFragment.CallBack {
    public static final String BOUNDLE_TASK = "boundleTask";
    private Task mTask;

    public static final String EXTRA_TASK = "com.example.taskmanager.activity.task";

    public static Intent newIntent(Context context, Task task) {
        Intent intent = new Intent(context, TaskDetailActivity.class);
        intent.putExtra(EXTRA_TASK, task);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        Intent intent = getIntent();
        if (intent != null) {
            mTask = (Task) intent.getSerializableExtra(EXTRA_TASK);
        }
        if (savedInstanceState != null) {
            mTask = (Task) savedInstanceState.getSerializable(BOUNDLE_TASK);
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        TaskDetailFragment taskDetailFragment = TaskDetailFragment.newInstance(mTask);
        fragmentManager.beginTransaction().replace(R.id.detail_container, taskDetailFragment).commit();

        setResult(RESULT_OK);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(BOUNDLE_TASK, mTask);
    }

    @Override
    public void removeTaskDetailFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().remove(fragment).commit();
        setResult(Activity.RESULT_OK);
        finish();
    }
}