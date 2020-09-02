package com.example.taskmanager.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.taskmanager.model.User;

@Dao
public interface UserDBDao {
    @Insert
    public void insert(User user);

    @Query("select * from userTable where userName=:userName")
    public User get(String userName);

    @Update
    public void update(User user);

    @Delete
    public void delete(User user);
}
