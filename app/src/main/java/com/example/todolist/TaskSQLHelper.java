package com.example.todolist;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.todolist.sqldb.ISQLHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class TaskSQLHelper implements ISQLHelper<Task> {

    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_CREATE_DATE = "create_date";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_CLOSE_DATE = "close_date";
    public static final String COLUMN_PRIORITY = "priority";

    private Map<String, String> columns;
    private SimpleDateFormat dateFormat;

    public  TaskSQLHelper(){
        columns = new HashMap<>();

        columns.put(COLUMN_NAME, "TEXT");
        columns.put(COLUMN_DESCRIPTION, "TEXT");
        columns.put(COLUMN_CREATE_DATE, "TEXT");
        columns.put(COLUMN_STATUS, "INTEGER");
        columns.put(COLUMN_CLOSE_DATE, "TEXT");
        columns.put(COLUMN_PRIORITY, "INTEGER");

        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    }

    @Override
    public String GetTableName() {
        return "tasks";
    }

    @Override
    public Map<String, String> GetColumns() {
        return columns;
    }

    @Override
    public Task GetObject(Cursor cursor) {
        Task task = new Task();

        task.name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
        task.description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
        task.status = cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS));
        task.priority = cursor.getInt(cursor.getColumnIndex(COLUMN_PRIORITY));

        try {
            task.createDate = dateFormat.parse(cursor.getString(cursor.getColumnIndex(COLUMN_CREATE_DATE)));
            task.closeDate = dateFormat.parse(cursor.getString(cursor.getColumnIndex(COLUMN_CLOSE_DATE)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return task;
    }

    @Override
    public ContentValues GetContentValue(Task task) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, task.name);
        contentValues.put(COLUMN_DESCRIPTION, task.description);
        contentValues.put(COLUMN_STATUS, task.status);
        contentValues.put(COLUMN_PRIORITY, task.priority);
        contentValues.put(COLUMN_CREATE_DATE, dateFormat.format(task.createDate));
        contentValues.put(COLUMN_CLOSE_DATE, dateFormat.format(task.closeDate));

        return  contentValues;
    }
}

