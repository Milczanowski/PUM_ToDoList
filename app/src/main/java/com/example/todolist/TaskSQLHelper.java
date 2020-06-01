package com.example.todolist;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.todolist.sqldb.ISQLHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TaskSQLHelper implements ISQLHelper<Task> {

    private  static final  String EMPTY_STRING = "";
    protected static final String COLUMN_NAME = "name";
    protected static final String COLUMN_DESCRIPTION = "description";
    protected static final String COLUMN_CREATE_DATE = "create_date";
    protected static final String COLUMN_STATUS = "status";
    protected static final String COLUMN_CLOSE_DATE = "close_date";
    protected static final String COLUMN_PRIORITY = "priority";
    protected static final String COLUMN_FINISH_DATE = "finish_date";

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
        columns.put(COLUMN_FINISH_DATE, "TEXT");

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
        task.createDate = GetDateValue(cursor, COLUMN_CREATE_DATE);
        task.closeDate = GetDateValue(cursor, COLUMN_CLOSE_DATE);
        task.finishDate = GetDateValue(cursor, COLUMN_FINISH_DATE);

        return task;
    }

    @Override
    public ContentValues GetContentValue(Task task) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, task.name);
        contentValues.put(COLUMN_DESCRIPTION, task.description);
        contentValues.put(COLUMN_STATUS, task.status);
        contentValues.put(COLUMN_PRIORITY, task.priority);

        contentValues.put(COLUMN_CREATE_DATE, GetDateStringValue(task.createDate));
        contentValues.put(COLUMN_CLOSE_DATE, GetDateStringValue(task.closeDate));
        contentValues.put(COLUMN_FINISH_DATE, GetDateStringValue(task.finishDate));

        return  contentValues;
    }

    private Date GetDateValue(Cursor cursor, String column){
        try {
            int columnIndex = cursor.getColumnIndex(column);

            if(columnIndex >= 0)
                return dateFormat.parse(cursor.getString(columnIndex));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  null;
    }

    private String GetDateStringValue(Date date){
        if(date != null)
            return dateFormat.format(date);

        return EMPTY_STRING;
    }


}

