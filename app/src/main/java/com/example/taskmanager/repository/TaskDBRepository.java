package com.example.taskmanager.repository;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import com.example.taskmanager.database.TaskBaseHelper;
import com.example.taskmanager.database.cursorwrapper.TaskCursorWrapper;
import com.example.taskmanager.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskDBRepository {//{implements IRepository<Task> {
    private static TaskDBRepository sTaskRepository;
    private static Context mContext;
    private SQLiteDatabase mDatabase;


    public static TaskDBRepository getInstance(Context context) {
        mContext = context.getApplicationContext();
        if (sTaskRepository == null)
            sTaskRepository = new TaskDBRepository();

        return sTaskRepository;
    }

    private TaskDBRepository() {
        TaskBaseHelper taskBaseHelper = new TaskBaseHelper(mContext);
        mDatabase = taskBaseHelper.getWritableDatabase();
    }

    //Read all
    public List<Task> getList() {
       //List<Task> crimes = new ArrayList<>();
       //TaskCursorWrapper cursor = queryCrimes(null, null);
       //try {
       //    cursor.moveToFirst();

       //    while (!cursor.isAfterLast()) {
       //        crimes.add(cursor.getCrime());
       //        cursor.moveToNext();
       //    }
       //} finally {
       //    cursor.close();
       //}

        return null;
    }
/*
    /*@Override
    public void setList(List<Crime> crimes) {
        mCrimes = crimes;
    }*/
/*
    //Read one
    @Override
    public Crime get(UUID uuid) {
        /*for (Crime crime: mCrimes) {
            if (crime.getId().equals(uuid))
                return crime;
        }*/
/*
        String selection = COLS.UUID + "=?";
        String[] selectionArgs = new String[]{uuid.toString()};
        CrimeCursorWrapper cursor = queryCrimes(selection, selectionArgs);

        try {
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                return cursor.getCrime();
            }
            return null;
        } finally {
            cursor.close();
        }
    }

    private CrimeCursorWrapper queryCrimes(String selection, String[] selectionArgs) {
        Cursor cursor = mDatabase.query(CrimeDBSchema.CrimeTable.NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null);

        CrimeCursorWrapper crimeCursorWrapper = new CrimeCursorWrapper(cursor);
        return crimeCursorWrapper;
    }

    //Update one
    @Override
    public void update(Crime crime) {
        ContentValues values = getCrimeContentValue(crime);
        String where = COLS.UUID + "=?";
        String[] whereArgs = new String[]{crime.getId().toString()};
        mDatabase.update(CrimeDBSchema.CrimeTable.NAME, values, where, whereArgs);
    }

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
/*
    private ContentValues getCrimeContentValue(Crime crime) {
        ContentValues values = new ContentValues();
        values.put(COLS.UUID, crime.getId().toString());
        values.put(COLS.TITLE, crime.getTitle());
        values.put(COLS.DATE, crime.getDate().getTime());
        values.put(COLS.SOLVED, crime.isSolved() ? 1 : 0);

        return values;
    }
        */
}
