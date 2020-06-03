package com.example.todolist;

import android.content.ContentValues;
import android.database.Cursor;
import com.example.todolist.sqldb.ISQLHelper;

import java.util.ArrayList;

public class AttachmentSQLHelper implements ISQLHelper<Attachment> {
    protected static final String COLUMN_PATH = "path";
    protected static final String COLUMN_TASK = "task";

    private ArrayList<String> columns;

    private String taskPrimaryKey;
    private Long taskID;

    public AttachmentSQLHelper(String taskTableName, String taskPrimaryKey){
        this.taskPrimaryKey = taskPrimaryKey;

        columns = new ArrayList<>();

        columns.add(String.format("%s %s", COLUMN_PATH, "TEXT"));
        columns.add(String.format("%s %s", COLUMN_TASK, "INTEGER"));
        columns.add(String.format("%s %s", String.format("FOREIGN KEY(%s)", COLUMN_TASK), String.format("REFERENCES %s(%s)", taskTableName, taskPrimaryKey)));
    }

    @Override
    public String GetTableName() {
        return "task_attachments";
    }

    @Override
    public String GetPrimaryKey() {
        return "id";
    }

    @Override
    public ArrayList<String> GetColumns() {
        return columns;
    }

    @Override
    public Attachment GetObject(Cursor cursor) {
        Attachment attachment = new Attachment();
        attachment.path = cursor.getString(cursor.getColumnIndex(COLUMN_PATH));
        attachment.task = cursor.getLong(cursor.getColumnIndex(COLUMN_TASK));

        return attachment;
    }

    @Override
    public ContentValues GetContentValue(Attachment object) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PATH, object.path);
        contentValues.put(COLUMN_TASK, object.task);

        return  contentValues;
    }

    @Override
    public String GetOrderColumn() {
        return null;
    }

    @Override
    public SQLSortOrder GetSortOrder() {
        return null;
    }

    @Override
    public String GetWhereColumn() {
        return COLUMN_TASK;
    }

    @Override
    public Object GetWhereValue() {
        return null;
    }

    public void SetTaskID(Long taskID){
        this.taskID = taskID;
    }

}
