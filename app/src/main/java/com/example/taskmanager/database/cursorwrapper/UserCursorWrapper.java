package com.example.taskmanager.database.cursorwrapper;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.util.Log;

import com.example.taskmanager.database.TaskDBSchema;
import com.example.taskmanager.model.User;


public class UserCursorWrapper extends CursorWrapper {
    public UserCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public User getUser() {
        if(getWrappedCursor() == null){
         Log.d("bashir","nulll");
        }
        int index1 = getColumnIndex(TaskDBSchema.UserTable.COLS.USER_NAME);
        int index2 = getColumnIndex(TaskDBSchema.UserTable.COLS.PASSWORD);
        Log.d("bashir",index1+"");
        Log.d("bashir",index2+"");
        String userName = getString(getColumnIndex(TaskDBSchema.UserTable.COLS.USER_NAME));
        String password = getString(getColumnIndex(TaskDBSchema.UserTable.COLS.PASSWORD));
        User user = new User(userName,password);
        return user;
    }
}
