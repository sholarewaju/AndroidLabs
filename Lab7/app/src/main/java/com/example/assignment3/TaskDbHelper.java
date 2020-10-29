package com.example.assignment3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by babafemi on 12/11/17.
 */

public class TaskDbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "com.chat.app";
    public static final int DB_VERSION = 1;

    public class TaskEntry implements BaseColumns {
        public static final String TABLE_CHATs = "chat";
        public static final String COL_MESSAGE = "msg";
        public static final String COL_ISSENT = "is_sent";

    }
    public TaskDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //SQL QUERY TO CREATE TABLE
        String createLectureNotesTable = "CREATE TABLE " + (TaskEntry.TABLE_CHATs) + " ( " +
                TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskEntry.COL_MESSAGE + " TEXT NOT NULL, " +
                TaskEntry.COL_ISSENT + " TEXT NOT NULL );";


                db.execSQL(createLectureNotesTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //UPGRADES THE DATABASE BY DROPPING THE TABLE AND CREATING THE TABLE AGAIN.
        db.execSQL("DROP TABLE IF EXISTS " + TaskEntry.TABLE_CHATs);
        onCreate(db);
    }
}
