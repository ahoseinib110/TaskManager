package com.example.taskmanager.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.taskmanager.model.Task;

@Database(entities = {Task.class} , version = 1,exportSchema = false)
public abstract class TaskDB extends RoomDatabase {
    public abstract TaskDBDao taskDao();
}
