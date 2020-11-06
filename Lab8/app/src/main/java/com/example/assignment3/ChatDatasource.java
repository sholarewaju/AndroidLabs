package com.example.assignment3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

public class ChatDatasource {
    // Database fields
    private SQLiteDatabase database;
    private TaskDbHelper dbHelper;
    private String[] allColumns = { TaskDbHelper.TaskEntry._ID, TaskDbHelper.TaskEntry.COL_MESSAGE,
            TaskDbHelper.TaskEntry.COL_ISSENT };

    public ChatDatasource(Context context) {
        dbHelper = new TaskDbHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Message addChatMessage(Message message) {
        //Object instatantiation of the ContentValues class
        ContentValues values = new ContentValues();
        //adds the database to be stored into the contentValues Object
        values.put(allColumns[1], message.getMsg());
        values.put(allColumns[2], message.getType());
        //insert into database
        long insertId= database.insertWithOnConflict(TaskDbHelper.TaskEntry.TABLE_CHATs,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE);
        message.setId(insertId);
      return message;
    }

    public void deleteChatMessage(Message message) {
        long id = message.getId();
        System.out.println("Message deleted with id: " + id);
        database.delete(TaskDbHelper.TaskEntry.TABLE_CHATs, allColumns[0]
                + " = " + id, null);
    }

    public void deleteChatMessage(int id) {
        System.out.println("Message deleted with id: " + id);
        database.delete(TaskDbHelper.TaskEntry.TABLE_CHATs, allColumns[0]
                + " = " + id, null);
    }
    public ArrayList<Message> getAllChat() {
        ArrayList<Message> messages = new ArrayList<Message>();

        Cursor cursor = database.query(TaskDbHelper.TaskEntry.TABLE_CHATs,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Message message = cursorToMessage(cursor);
            messages.add(message);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        printCursor(cursor,TaskDbHelper.DB_VERSION);
        cursor.close();
        return messages;
    }

    private Message cursorToMessage(Cursor cursor) {

        Boolean type=cursor.getString(2).equalsIgnoreCase("1")?true:false;
        Message message= new Message(type,cursor.getString(1));
        message.setId(cursor.getLong(0));
        return message;
    }

    private void printCursor( Cursor c, int version){
        Log.e("DB_VERSION","DB version:"+version);
        Log.e("COLS_count","Column count:"+c.getColumnCount());
        String column_names="";
        for (String colname: c.getColumnNames()) {
            column_names+=colname+",";
        }
        Log.e("COL_name","Column names:"+column_names);
        Log.e("ROW_COUNT","Result count:"+c.getCount());
        c.moveToFirst();
        while (!c.isAfterLast()) {
            Message message = cursorToMessage(c);
            Log.e("ROW_RESULT", message.toString());
            c.moveToNext();
        }
    }
}
