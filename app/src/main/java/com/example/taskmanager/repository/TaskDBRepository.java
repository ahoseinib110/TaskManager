package com.example.taskmanager.repository;


import android.content.Context;

import androidx.room.Room;

import com.example.taskmanager.database.TaskDB;
import com.example.taskmanager.model.State;
import com.example.taskmanager.model.Task;

import java.util.List;

public class TaskDBRepository {
    public static final String TAG = "bashir_TDBR";
    private static TaskDBRepository sTaskRepository;
    private static Context mContext;
    private static int mUserId;
    private TaskDB mTaskDB;

    public static TaskDBRepository getInstance(Context context, int userId) {
        mUserId = userId;
        mContext = context.getApplicationContext();
        if (sTaskRepository == null) {
            sTaskRepository = new TaskDBRepository();
        }
        return sTaskRepository;
    }

    private TaskDBRepository() {
        mTaskDB = Room.databaseBuilder(mContext, TaskDB.class, "TaskDB.db")
                .allowMainThreadQueries()
                .build();
    }


    private List<Task> getList(State state, int userId) {
        return mTaskDB.taskDao().getList(state, userId);
    }


    public Task get(Task task) {
        return mTaskDB.taskDao().get(task.getId(), task.getUserId());
    }


    //Update one
    public void update(Task task) {
        mTaskDB.taskDao().update(task);
    }

    //Delete
    public void delete(Task task) {
        mTaskDB.taskDao().delete(task);
    }

    //Create: Insert
    public void insert(Task task) {
        mTaskDB.taskDao().insert(task);
    }

    public TaskDB getTaskDB() {
        return mTaskDB;
    }
}
