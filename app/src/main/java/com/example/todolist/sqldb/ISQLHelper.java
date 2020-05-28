package com.example.todolist.sqldb;

import android.content.ContentValues;
import android.database.Cursor;
import java.util.Map;

public interface ISQLHelper<T extends ISQLObject> {
    public String GetTableName();
    public Map<String, String> GetColumns();
    public T GetObject(Cursor cursor);
    public ContentValues GetContentValue(T object);
}
