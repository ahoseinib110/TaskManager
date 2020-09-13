package com.example.taskmanager.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "userTable")
public class User implements Serializable {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int mUserId;
    @ColumnInfo(name = "userName")
    private String mUserName;
    @ColumnInfo(name = "password")
    private String mPassword;
    @ColumnInfo(name = "RegisterDate")
    private Date mRegisterDate;

    public User() {

    }

    @Ignore
    public User(String userName, String password, Date date) {
        mUserName = userName;
        mPassword = password;
        mRegisterDate = date;
    }


    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        mUserId = userId;
    }

    public Date getRegisterDate() {
        return mRegisterDate;
    }

    public void setRegisterDate(Date registerDate) {
        mRegisterDate = registerDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "mUserName='" + mUserName + '\'' +
                ", mPassword='" + mPassword + '\'' +
                ", mRegisterDate=" + mRegisterDate +
                '}';
    }
}
