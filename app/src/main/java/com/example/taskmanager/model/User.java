package com.example.taskmanager.model;

import java.io.Serializable;

public class User implements Serializable {

    private String mUserName;
    private String mPassword;
    private int mUserId;


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

    public User( String userName, String password) {
        mUserName = userName;
        mPassword = password;
    }


    @Override
    public String toString() {
        return "User{" +
                ", mUserName='" + mUserName + '\'' +
                ", mPassword='" + mPassword + '\'' +
                '}';
    }
}
