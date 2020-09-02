package com.example.taskmanager.repository;

import android.content.Context;

import androidx.room.Room;

import com.example.taskmanager.database.TaskDB;
import com.example.taskmanager.model.User;


public class UserDBRepository {

    private static UserDBRepository sUserRepository;
    private static Context mContext;

    private TaskDB mTaskDB;

    public static UserDBRepository getInstance(Context context) {
        mContext = context.getApplicationContext();
        if (sUserRepository == null)
            sUserRepository = new UserDBRepository();

        return sUserRepository;
    }

    private UserDBRepository() {
        mTaskDB = Room.databaseBuilder(mContext, TaskDB.class, "TaskDB.db")
                .allowMainThreadQueries()
                .build();
    }

    public void insert(User user) {
        mTaskDB.userDao().insert(user);
    }

    public User get(String userName) {
        return mTaskDB.userDao().get(userName);
    }

    public void update(User user) {
        mTaskDB.userDao().update(user);
    }

    public void delete(User user) {
        mTaskDB.userDao().delete(user);
    }
}
