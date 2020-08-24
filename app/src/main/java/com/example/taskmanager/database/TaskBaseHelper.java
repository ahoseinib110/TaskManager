package com.example.taskmanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class TaskBaseHelper extends SQLiteOpenHelper {

    public TaskBaseHelper(@Nullable Context context) {
        super(context, TaskDBSchema.NAME, null, TaskDBSchema.VERSION);
    }

    /**
     * create the database with all tables, constraints, ...
     * it's like implementing the ERD.
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TaskDBSchema.TaskTable.NAME + "(" +
                TaskDBSchema.TaskTable.COLS.ID + " integer primary key autoincrement," +
                TaskDBSchema.TaskTable.COLS.USER_ID + " integer," +
                TaskDBSchema.TaskTable.COLS.UUID + " text," +
                TaskDBSchema.TaskTable.COLS.TITLE + " text," +
                TaskDBSchema.TaskTable.COLS.DESCRIPTION + " text," +
                TaskDBSchema.TaskTable.COLS.DATE + " long," +
                TaskDBSchema.TaskTable.COLS.STATE + " text" +
                ");");

        db.execSQL("CREATE TABLE " + TaskDBSchema.UserTable.NAME + "(" +
                TaskDBSchema.UserTable.COLS.ID + " integer primary key autoincrement," +
                TaskDBSchema.UserTable.COLS.USER_NAME + " text," +
                TaskDBSchema.UserTable.COLS.PASSWORD + " text" +
                ");");
    }

    /**
     * update the database with all alters.
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
