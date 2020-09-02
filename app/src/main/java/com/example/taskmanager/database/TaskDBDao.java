package com.example.taskmanager.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.taskmanager.model.State;
import com.example.taskmanager.model.Task;

import java.util.List;

@Dao
public interface TaskDBDao {

    @Query("Select * From tasktable where state=:state and userId=:userId")
    public List<Task> getList(State state,int userId);

    @Query("select * from tasktable where id=:id and userId=:userId")
    public Task get(int id, int userId);

    @Update
    public void update(Task task);

    @Insert
    public void insert(Task task);

    @Delete
    public void delete(Task task);
}
