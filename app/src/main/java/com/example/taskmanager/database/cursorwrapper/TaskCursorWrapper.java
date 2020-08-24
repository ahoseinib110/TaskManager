package com.example.taskmanager.database.cursorwrapper;

import android.database.Cursor;
import android.database.CursorWrapper;


import com.example.taskmanager.database.TaskDBSchema;
import com.example.taskmanager.model.State;
import com.example.taskmanager.model.Task;

import java.util.Date;
import java.util.UUID;

public class TaskCursorWrapper extends CursorWrapper {

    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public TaskCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Task getTask() {
        String stringUUID = getString(getColumnIndex(TaskDBSchema.TaskTable.COLS.UUID));
        String title = getString(getColumnIndex(TaskDBSchema.TaskTable.COLS.TITLE));
        String description = getString(getColumnIndex(TaskDBSchema.TaskTable.COLS.DESCRIPTION));
        Date date = new Date(getLong(getColumnIndex(TaskDBSchema.TaskTable.COLS.DATE)));
        State state = State.valueOf(getString(getColumnIndex(TaskDBSchema.TaskTable.COLS.STATE)));


        Task task = new Task(UUID.fromString(stringUUID), title,description,state, date);
        return task;
    }
}
