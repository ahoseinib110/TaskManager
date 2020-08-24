package com.example.taskmanager.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.taskmanager.R;
import com.example.taskmanager.fragment.TaskFragment;

public class TaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Log.d("bashir","bashiiirrrrrr");
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.task_fragment_container);
        if(fragment==null){
            fragmentManager.beginTransaction()
                    .add(R.id.task_fragment_container, TaskFragment.newInstance())
                    .commit();
        }
    }
}