package com.example.taskmanager.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.User;

@TypeConverters({Task.StateConverter.class , Task.DateConverter.class})
@Database(entities = {Task.class, User.class} , version = 1,exportSchema = false)
public abstract class TaskDB extends RoomDatabase {
    public abstract TaskDBDao taskDao();
    public abstract UserDBDao userDao();
}
