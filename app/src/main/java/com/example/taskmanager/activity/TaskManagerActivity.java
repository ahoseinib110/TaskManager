package com.example.taskmanager.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.taskmanager.R;
import com.example.taskmanager.fragment.TaskListFragment;
import com.example.taskmanager.model.State;
import com.example.taskmanager.repository.TaskDBRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;



public class TaskManagerActivity extends AppCompatActivity {
    public static final String TAG = "TMA_BASHIR";
    public static final String BUNDLE_ADAPTER = "BUNDLE_ADAPTER";
    private ViewPager2 mViewPagerTask;
    private TabLayout mTabLayout;

    public static final String EXTRA_NAME = "org.maktab.taskmanager.activity.taskName";
    public static final String EXTRA_NUMBER = "org.maktab.taskmanager.activity.taskNumber";
    private int mTasksNumber;
    private TaskDBRepository mTaskRepository;
    private String mName;
    private ViewPagerTaskAdadapter mViewPagerTaskAdapter;

    public FloatingActionButton mFABAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_manager);
        findViews();
        setOnClickListener();
        Intent intent = getIntent();
        mName = intent.getStringExtra(EXTRA_NAME);
        mTasksNumber = intent.getIntExtra(EXTRA_NUMBER, 0);
        mViewPagerTaskAdapter = new ViewPagerTaskAdadapter(this);
        mTaskRepository = TaskDBRepository.getInstance(this);
        //if (mTaskRepository.getList(State.TODO).size() == 0 && mTaskRepository.getList(State.DOING).size() == 0 && mTaskRepository.getList(State.DONE).size() == 0) {
        //    mTaskRepository.createRandomTaskList(mTasksNumber, mName);
        //}
        mViewPagerTask.setAdapter(mViewPagerTaskAdapter);

        new TabLayoutMediator(mTabLayout, mViewPagerTask,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        tab.setText(String.valueOf(getSate(position)));
                    }
                }).attach();
    }



    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void mOnResume() {
        this.onResume();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("bashir","hoooorrrraaaaa");
    }

    private void findViews() {
        mFABAdd = findViewById(R.id.FABAdd);
        mTabLayout = findViewById(R.id.tabLayout);
        mViewPagerTask = findViewById(R.id.viewPagerTask);
    }

    private void setOnClickListener() {
       // mFABAdd.setOnClickListener(new View.OnClickListener() {
       //     @Override
       //     public void onClick(View v) {
       //         Task task = new Task("", State.DONE);//***************
       //         mTaskRepository.insert(task);
       //         //mTaskList.add(task);
       //         //Log.d("TLF_BASHIR",mTaskRepository.getList().size()+"");
       //         //updateUI();
       //         TaskDetailFragment taskDetailFragment = TaskDetailFragment.newInstance(task.getUUID());
       //         taskDetailFragment.show(getSupportFragmentManager(), "tag");
       //     }
       // });
    }

    public static Intent newIntent(Context context, String name, int tasksNumber) {
        Intent intent = new Intent(context, TaskManagerActivity.class);
        //Log.d("TLA_BASHIR", name + " " + tasksNumber);
        intent.putExtra(EXTRA_NAME, name);
        intent.putExtra(EXTRA_NUMBER, tasksNumber);
        return intent;
    }

    private class ViewPagerTaskAdadapter extends FragmentStateAdapter {
        public ViewPagerTaskAdadapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            //Log.d(TAG,"salam  "+mTaskRepository.getList().size());
            String name = mName;
            int number;
            State state = getSate(position);
            number = 0 ; //mTaskRepository.getList(state).size();
            TaskListFragment taskListFragment = TaskListFragment.newInstance(name, number, state);
            return taskListFragment;
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }

    private State getSate(int position) {
        State state;
        switch (position) {
            case 0:
                state = State.TODO;
                break;
            case 1:
                state = State.DOING;
                break;
            case 2:
                state = State.DONE;
                break;
            default:
                state = State.TODO;
                break;
        }
        return state;
    }

}