package com.example.taskmanager.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.taskmanager.database.TaskBaseHelper;
import com.example.taskmanager.database.TaskDBSchema;
import com.example.taskmanager.database.cursorwrapper.UserCursorWrapper;
import com.example.taskmanager.model.User;

import java.util.List;

public class UserDBRepository {

    private static UserDBRepository sUserRepository;

    //future referenced: memory leaks
    private static Context mContext;

    private SQLiteDatabase mDatabase;

    public static UserDBRepository getInstance(Context context) {
        mContext = context.getApplicationContext();
        if (sUserRepository == null)
            sUserRepository = new UserDBRepository();

        return sUserRepository;
    }

    private UserDBRepository() {
        TaskBaseHelper crimeBaseHelper = new TaskBaseHelper(mContext);
        mDatabase = crimeBaseHelper.getWritableDatabase();
    }



    public List<User> getList() {
        return null;
    }


    public User get(String userName) {
        String selection = TaskDBSchema.UserTable.COLS.USER_NAME + "=?";
        String[] selectionArgs = new String[]{userName};
        UserCursorWrapper cursor = queryUsers(selection, selectionArgs);
        try {
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                return cursor.getUser();
            }
            return null;
        } finally {
            cursor.close();
        }
    }

    private UserCursorWrapper queryUsers(String selection, String[] selectionArgs) {
        Cursor cursor = mDatabase.query(TaskDBSchema.UserTable.NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null);
        UserCursorWrapper userCursorWrapper = new UserCursorWrapper(cursor);
        return userCursorWrapper;
    }


    public void update(User user) {
        ContentValues values = getUserContentValue(user);
        String where = TaskDBSchema.UserTable.COLS.USER_NAME + "=?";
        String[] whereArgs = new String[]{user.getUserName()};
        mDatabase.update(TaskDBSchema.UserTable.NAME, values, where, whereArgs);
    }


    public void delete(User user) {
        String where = TaskDBSchema.UserTable.COLS.USER_NAME + "=?";
        String[] whereArgs = new String[]{user.getUserName()};
        mDatabase.delete(TaskDBSchema.UserTable.NAME, where, whereArgs);
    }


    public void insert(User user) {
        ContentValues values = getUserContentValue(user);
        mDatabase.insert(TaskDBSchema.UserTable.NAME, null, values);
    }



    public void insertList(List<User> users) {
    }


    public int getPosition(String userName) {
        return -1;
    }


    private ContentValues getUserContentValue(User user) {
        ContentValues values = new ContentValues();
        values.put(TaskDBSchema.UserTable.COLS.USER_NAME, user.getUserName());
        values.put(TaskDBSchema.UserTable.COLS.PASSWORD, user.getPassword());
        return values;
    }
}
