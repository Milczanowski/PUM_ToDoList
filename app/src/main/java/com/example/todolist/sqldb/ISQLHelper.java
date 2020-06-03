package com.example.todolist.sqldb;

import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;

public interface ISQLHelper<T extends ISQLObject> {
    enum SQLSortOrder {
        ASC,
        DESC
    }

    public String GetTableName();
    public String GetPrimaryKey();
    public ArrayList<String> GetColumns();
    public T GetObject(Cursor cursor);
    public ContentValues GetContentValue(T object);
    public String GetOrderColumn();
    public SQLSortOrder GetSortOrder();
    public String GetWhereColumn();
    public Object GetWhereValue();
}
