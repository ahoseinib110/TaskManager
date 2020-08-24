package com.example.taskmanager.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Task implements Serializable {
    private String mTaskTitle;
    private State mTaskState;
    private UUID mUUID;
    private String mTaskDescription;
    private Date mDate;
    private int mUserId;


    public Task(int userId,String taskTitle, State taskState) {
        this(userId,UUID.randomUUID(),taskTitle,"", taskState,new Date());
    }


    public Task(int userId,UUID id, String taskTitle, String taskDescription, State taskState, Date date) {
        mUUID = id;
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

    public UUID getUUID() {
        return mUUID;
    }

    public void setUUID(UUID UUID) {
        mUUID = UUID;
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
