package com.example.taskmanager.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

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

    @Ignore
    public Task(int userId,String taskTitle, State taskState) {
        this(userId,taskTitle,"", taskState,new Date());
    }

    @Ignore
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

    @Override
    public String toString() {
        return "Task{" +
                "mId=" + mId +
                ", mUserId=" + mUserId +
                ", mTaskTitle='" + mTaskTitle + '\'' +
                ", mTaskState=" + mTaskState +
                ", mTaskDescription='" + mTaskDescription + '\'' +
                ", mDate=" + mDate +
                '}';
    }

    public static class StateConverter {
        @TypeConverter
        public String stateToString(State state) {
            return state == null ? null : state.toString();
        }

        @TypeConverter
        public State stringToState(String value) {
            return value == null ? null : State.valueOf(value);
        }
    }

    public static class DateConverter {
        @TypeConverter
        public Long dateToTimeStamp(Date date) {
            return date == null ? null : date.getTime();
        }

        @TypeConverter
        public Date fromTimeStamp(Long value) {
            return value == null ? null : new Date(value);
        }
    }
}
