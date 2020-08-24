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


    public Task(String taskTitle, State taskState) {
        this(UUID.randomUUID(),taskTitle,"", taskState,new Date());
    }


    public Task(UUID id, String taskTitle, String taskDescription, State taskState, Date date) {
        mUUID = id;
        mTaskTitle = taskTitle;
        mTaskDescription=taskDescription;
        mTaskState = taskState;
        mDate = date;
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
}
