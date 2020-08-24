package com.example.taskmanager.repository;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.taskmanager.database.TaskBaseHelper;
import com.example.taskmanager.database.TaskDBSchema;
import com.example.taskmanager.database.cursorwrapper.TaskCursorWrapper;
import com.example.taskmanager.model.State;
import com.example.taskmanager.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskDBRepository {//{implements IRepository<Task> {
    private static TaskDBRepository sTaskRepository;
    private static Context mContext;
    private SQLiteDatabase mDatabase;
    private List<Task> mTasksTodo;
    private List<Task> mTasksDoing;
    private List<Task> mTasksDone;

    public static TaskDBRepository getInstance(Context context) {
        mContext = context.getApplicationContext();
        if (sTaskRepository == null)
            sTaskRepository = new TaskDBRepository();
        return sTaskRepository;
    }

    private TaskDBRepository() {
        TaskBaseHelper taskBaseHelper = new TaskBaseHelper(mContext);
        mDatabase = taskBaseHelper.getWritableDatabase();
        mTasksTodo = getListFromDatabase(State.TODO);
        mTasksDoing = getListFromDatabase(State.DOING);
        mTasksDone = getListFromDatabase(State.DONE);
    }

    //Read all
    public List<Task> getList(State state) {
        switch (state) {
            case TODO:
                return mTasksTodo;
            case DOING:
                return mTasksDoing;
            case DONE:
                return mTasksDone;
        }
        return null;
    }

    private List<Task> getListFromDatabase(State state) {
        List<Task> tasks = new ArrayList<>();
        String selection = TaskDBSchema.TaskTable.COLS.STATE + "=?";
        String[] selectionArgs = new String[]{state.toString()};
        TaskCursorWrapper cursor = queryTasks(selection, selectionArgs);
        if (cursor == null || cursor.getCount() == 0) {
            return new ArrayList<>();
        }
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                tasks.add(cursor.getTask());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return tasks;
    }


/*
    /*@Override
    public void setList(List<Crime> crimes) {
        mCrimes = crimes;
    }*/


    public Task get(Task task) {
        String selection = TaskDBSchema.TaskTable.COLS.UUID + "=?";
        String[] selectionArgs = new String[]{task.getUUID().toString()};

        TaskCursorWrapper cursor = queryTasks(selection, selectionArgs);
        if (cursor == null || cursor.getCount() == 0)
            return null;
        try {
            cursor.moveToFirst();
            return cursor.getTask();
        } finally {
            cursor.close();
        }
    }

    private TaskCursorWrapper queryTasks(String selection, String[] selectionArgs) {
        Cursor cursor = mDatabase.query(TaskDBSchema.TaskTable.NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null);

        TaskCursorWrapper taskCursorWrapper = new TaskCursorWrapper(cursor);
        return taskCursorWrapper;
    }

    //Update one
    public void update(Task task) {
        ContentValues values = getTaskContentValue(task);
        String where = TaskDBSchema.TaskTable.COLS.UUID + "=?";
        String[] whereArgs = new String[]{task.getUUID().toString()};
        Task task1 = get(task);
        if (task1 == null) {
            mDatabase.insert(TaskDBSchema.TaskTable.NAME, null, values);
        } else {
            mDatabase.update(TaskDBSchema.TaskTable.NAME, values, where, whereArgs);
        }
        mTasksTodo = getListFromDatabase(State.TODO);
        mTasksDoing = getListFromDatabase(State.DOING);
        mTasksDone = getListFromDatabase(State.DONE);
        Log.d("bashir", "list size " + mTasksTodo.size() + "  " + mTasksDoing.size() + "  " + mTasksDone.size());
    }



/*
    //Delete
    @Override
    public void delete(Crime crime) {
        String where = COLS.UUID + "=?";
        String[] whereArgs = new String[]{crime.getId().toString()};
        mDatabase.delete(CrimeDBSchema.CrimeTable.NAME, where, whereArgs);
    }

    //Create: Insert
    @Override
    public void insert(Crime crime) {
        ContentValues values = getCrimeContentValue(crime);
        mDatabase.insert(CrimeDBSchema.CrimeTable.NAME, null, values);
    }

    //Create: Insert
    @Override
    public void insertList(List<Crime> crimes) {
    }

    @Override
    public int getPosition(UUID uuid) {
        List<Crime> crimes = getList();
        for (int i = 0; i < crimes.size(); i++) {
            if (crimes.get(i).getId().equals(uuid))
                return i;
        }

        return -1;
    }

    /**
     * Convert crime pojo to ContentValue
     * @param crime
     * @return
     */

    private ContentValues getTaskContentValue(Task task) {
        ContentValues values = new ContentValues();
        values.put(TaskDBSchema.TaskTable.COLS.UUID, task.getUUID().toString());
        values.put(TaskDBSchema.TaskTable.COLS.TITLE, task.getTaskTitle());
        values.put(TaskDBSchema.TaskTable.COLS.DESCRIPTION, task.getTaskDescription());
        values.put(TaskDBSchema.TaskTable.COLS.DATE, task.getDate().getTime());
        values.put(TaskDBSchema.TaskTable.COLS.STATE, task.getTaskState().toString());
        return values;
    }

}
