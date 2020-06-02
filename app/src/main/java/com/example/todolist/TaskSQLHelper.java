package com.example.todolist;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.todolist.sqldb.ISQLHelper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TaskSQLHelper implements ISQLHelper<Task> {

    protected static final String COLUMN_NAME = "name";
    protected static final String COLUMN_DESCRIPTION = "description";
    protected static final String COLUMN_CREATE_DATE = "create_date";
    protected static final String COLUMN_STATUS = "status";
    protected static final String COLUMN_CLOSE_DATE = "close_date";
    protected static final String COLUMN_PRIORITY = "priority";
    protected static final String COLUMN_FINISH_DATE = "finish_date";

    private Map<String, String> columns;

    public  TaskSQLHelper(){
        columns = new HashMap<>();

        columns.put(COLUMN_NAME, "TEXT");
        columns.put(COLUMN_DESCRIPTION, "TEXT");
        columns.put(COLUMN_CREATE_DATE, "INTEGER");
        columns.put(COLUMN_STATUS, "INTEGER");
        columns.put(COLUMN_CLOSE_DATE, "INTEGER");
        columns.put(COLUMN_PRIORITY, "INTEGER");
        columns.put(COLUMN_FINISH_DATE, "INTEGER");
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

        int columnIndex = cursor.getColumnIndex(COLUMN_CREATE_DATE);
        if(columnIndex>=0)
            task.createDate = new Date(cursor.getLong(columnIndex));

        columnIndex = cursor.getColumnIndex(COLUMN_CLOSE_DATE);
        if(columnIndex>=0)
            task.closeDate = new Date(cursor.getLong(columnIndex));

        columnIndex = cursor.getColumnIndex(COLUMN_FINISH_DATE);
        if(columnIndex>=0)
            task.finishDate = new Date(cursor.getLong(columnIndex));

        return task;
    }

    @Override
    public ContentValues GetContentValue(Task task) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, task.name);
        contentValues.put(COLUMN_DESCRIPTION, task.description);
        contentValues.put(COLUMN_STATUS, task.status);
        contentValues.put(COLUMN_PRIORITY, task.priority);

        if(task.createDate!= null)
            contentValues.put(COLUMN_CREATE_DATE, task.createDate.getTime());

        if(task.closeDate!= null)
            contentValues.put(COLUMN_CLOSE_DATE, task.closeDate.getTime());

        if(task.finishDate!= null)
            contentValues.put(COLUMN_FINISH_DATE, task.finishDate.getTime());

        return  contentValues;
    }
}

