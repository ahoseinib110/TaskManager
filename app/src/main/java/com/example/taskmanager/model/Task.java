package com.example.taskmanager.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity(tableName = "TaskTable")
public class Task implements Serializable {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int mId;
    @ColumnInfo(name = "userId")
    private int mUserId;
    @ColumnInfo(name = "title")
    private String mTaskTitle;
    @ColumnInfo(name = "state")
    private State mTaskState;
    @ColumnInfo(name = "taskDescription")
    private String mTaskDescription;
    @ColumnInfo(name = "date")
    private Date mDate;


    public Task(){

    }

    public Task(int userId,String taskTitle, State taskState) {
        this(userId,taskTitle,"", taskState,new Date());
    }

    public Task(int userId, String taskTitle, String taskDescription, State taskState, Date date) {
        mTaskTitle = taskTitle;
        mTaskDescription=taskDescription;
        mTaskState = taskState;
        mDate = date;
        mUserId=userId;
    }

    public String getTaskTitle() {
        return mTaskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        mTaskTitle = taskTitle;
    }

    public State getTaskState() {
        return mTaskState;
    }

    public void setTaskState(State taskState) {
        mTaskState = taskState;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTaskDescription() {
        return mTaskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        mTaskDescription = taskDescription;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        mUserId = userId;
    }
}
