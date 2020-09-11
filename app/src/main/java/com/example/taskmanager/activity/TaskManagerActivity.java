package com.example.taskmanager.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;
import androidx.viewpager2.widget.ViewPager2;

import com.example.taskmanager.R;
import com.example.taskmanager.fragment.TaskDetailFragment;
import com.example.taskmanager.fragment.TaskListFragment;
import com.example.taskmanager.model.State;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskDBRepository;
import com.example.taskmanager.repository.UserDBRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;


public class TaskManagerActivity extends AppCompatActivity {
    public static final String TAG = "TMA_BASHIR";
    public static final String BUNDLE_ADAPTER = "BUNDLE_ADAPTER";
    private ViewPager2 mViewPagerTask;
    private TabLayout mTabLayout;

    public static final String EXTRA_USER_ID = "org.maktab.taskmanager.activity.userId";

    private int mUserId;
    private ViewPagerTaskAdadapter mViewPagerTaskAdapter;
    private TaskDBRepository mTaskRepository;
    private UserDBRepository mUserRepository;

    public FloatingActionButton mFABAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_manager);
        findViews();
        setOnClickListener();
        Intent intent = getIntent();
        mUserId = intent.getIntExtra(EXTRA_USER_ID, 0);
        mViewPagerTaskAdapter = new ViewPagerTaskAdadapter(this);
        mTaskRepository = TaskDBRepository.getInstance(this, mUserId);
        mUserRepository = UserDBRepository.getInstance(this);
        //if (mTaskRepository.getList(State.TODO).size() == 0 && mTaskRepository.getList(State.DOING).size() == 0 && mTaskRepository.getList(State.DONE).size() == 0) {
        //    mTaskRepository.createRandomTaskList(mTasksNumber, mName);
        //}
        mViewPagerTask.setAdapter(mViewPagerTaskAdapter);
        mViewPagerTask.setOffscreenPageLimit(3);
        new TabLayoutMediator(mTabLayout, mViewPagerTask,
                new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(String.valueOf(getSate(position)));
            }
        }).attach();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_task_list, menu);
        MenuItem item = menu.findItem(R.id.menu_item_users);
        if(mUserRepository.get(mUserId).getUserName().equals("admin")){
            item.setVisible(true);
        }else {
            item.setVisible(false);
        }
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_item_users:
                Log.d(TAG,"users item clicked");
                Intent intent = new Intent(this,UserListActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void mOnResume() {
        this.onResume();
    }


    private void findViews() {
        mFABAdd = findViewById(R.id.FABAdd);
        mTabLayout = findViewById(R.id.tabLayout);
        mViewPagerTask = findViewById(R.id.viewPagerTask);
    }

    private void setOnClickListener() {
       mFABAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                List<TaskListFragment> taskListFragments = new ArrayList<>();
                int i=0;
                while (true){
                    TaskListFragment taskListFragment = (TaskListFragment) getSupportFragmentManager().findFragmentByTag("f" + i);
                    if(taskListFragment!=null){
                        taskListFragments.add(taskListFragment);
                        i++;
                    }else{
                        break;
                    }
                }
                Log.d(TAG,taskListFragments.size()+"");
                TaskListFragment myFragment = taskListFragments.get(mViewPagerTask.getCurrentItem());
                if(myFragment!=null){
                    Task task = new Task(mUserId, "", State.TODO);
                    TaskDetailFragment taskDetailFragment = TaskDetailFragment.newInstance(task);
                    taskDetailFragment.setTargetFragment(myFragment, TaskListFragment.DETAIL_PICKER_REQUEST_CODE);
                    taskDetailFragment.show(myFragment.getFragmentManager(), TaskListFragment.DIALOG_FRAGMENT_TAG);
                }
                for (TaskListFragment taskListFragment : taskListFragments){
                    taskListFragment.setFABClicked();
                }
                */
                Task task = new Task(mUserId, "", State.TODO);
                Intent intent = TaskDetailActivity.newIntent(TaskManagerActivity.this,task);
                startActivityForResult(intent,TaskListFragment.DETAIL_PICKER_REQUEST_CODE);
            }

       });
    }


    public static Intent newIntent(Context context, int userId) {
        Intent intent = new Intent(context, TaskManagerActivity.class);
        //Log.d("TLA_BASHIR", name + " " + tasksNumber);
        intent.putExtra(EXTRA_USER_ID, userId);
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
            int userId  =mUserId ;
            State state = getSate(position);
            TaskListFragment taskListFragment = TaskListFragment.newInstance(userId, state);
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