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
    private String orderColumn;
    private SQLSortOrder sortOrder;


    public  TaskSQLHelper(){
        columns = new HashMap<>();

        columns.put(COLUMN_NAME, "TEXT");
        columns.put(COLUMN_DESCRIPTION, "TEXT");
        columns.put(COLUMN_CREATE_DATE, "INTEGER");
        columns.put(COLUMN_STATUS, "INTEGER");
        columns.put(COLUMN_CLOSE_DATE, "INTEGER");
        columns.put(COLUMN_PRIORITY, "INTEGER");
        columns.put(COLUMN_FINISH_DATE, "INTEGER");

        orderColumn = COLUMN_CREATE_DATE;
        sortOrder = SQLSortOrder.ASC;
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

    private Date GetDateValue(Cursor cursor, String column){
        int columnIndex = cursor.getColumnIndex(column);

        if(columnIndex>=0)
        {
            Long dateValue = cursor.getLong(columnIndex);

            if(dateValue>0)
                return new Date(dateValue);
        }
        return null;
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

    @Override
    public String GetOrderColumn() {
        return orderColumn;
    }

    @Override
    public SQLSortOrder GetSortOrder() {
        return sortOrder;
    }

    public void SetSortOrder(SQLSortOrder sortOrder){
        this.sortOrder = sortOrder;
    }

    public void SetSortedByName(){
        orderColumn = COLUMN_NAME;
    }

    public void SetSortedByCreateDate(){
        orderColumn = COLUMN_CREATE_DATE;
    }

    public void SetSortedByPriority(){
        orderColumn = COLUMN_PRIORITY;
    }

    public void SetSortedByCloseDate(){
        orderColumn = COLUMN_CLOSE_DATE;
    }

    public void SetSortedByStatus(){
        orderColumn = COLUMN_STATUS;
    }

    public void SetSortedByFinishDate(){
        orderColumn = COLUMN_FINISH_DATE;
    }
}

