package com.example.todolist.sqldb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.todolist.appcontroller.IStorable;

import java.util.ArrayList;

public class SQLDatabase <T extends  ISQLObject> extends SQLiteOpenHelper implements IStorable<T> {

    private ISQLHelper<T> sqlHelper;

    public SQLDatabase(Context context, String databaseName, ISQLHelper<T> sqlHelper, int version) {
        super(context, databaseName , null, version);
        this.sqlHelper = sqlHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = GetCreateQuery(sqlHelper);
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if(i != i1) {
            sqLiteDatabase.execSQL(String.format("DROP TABLE IF EXISTS %s", sqlHelper.GetTableName()));
            onCreate(sqLiteDatabase);
        }
    }

    protected <Y extends ISQLObject>String GetCreateQuery(ISQLHelper<Y> sqlHelper){
        String columns = "";

        for (String column : sqlHelper.GetColumns()){
            columns += String.format(", %s", column);
        }

        return String.format("create table %s (%s integer primary key %s)", sqlHelper.GetTableName(), sqlHelper.GetPrimaryKey(), columns);
    }

    @Override
    public ArrayList<T> GetAllObjects() {
        ArrayList<T> allObjects = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String query = String.format("SELECT * FROM %s %s %s;", sqlHelper.GetTableName(), GetWhereValue(), GetSortValue());

        Cursor cursor =  db.rawQuery(query, null );
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            T obj = sqlHelper.GetObject(cursor);

            allObjects.add(obj);
            cursor.moveToNext();
        }

        return allObjects;
    }

    private String GetSortValue(){
        String orderColumn = sqlHelper.GetOrderColumn();
        ISQLHelper.SQLSortOrder sortOrder = sqlHelper.GetSortOrder();

        if(orderColumn == null)
            return "";

        if(orderColumn.isEmpty())
            return "";

        if(sortOrder == null)
            return "";
        return String.format(" ORDER BY %s %s", orderColumn, sortOrder);
    }

    private String GetWhereValue(){
        String whereColumn = sqlHelper.GetWhereColumn();
        Object whereValue = sqlHelper.GetWhereValue();

        if(whereColumn == null)
            return "";

        if(whereColumn.isEmpty())
            return "";

        if(whereValue == null)
            return "";

        return String.format(" WHERE %s = %s", whereColumn, whereValue);
    }

    @Override
    public ArrayList<Long> GetSortedIDs() {
        ArrayList<Long> allObjects = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = String.format("SELECT %s FROM %s %s %s;", sqlHelper.GetPrimaryKey(), sqlHelper.GetTableName(), GetWhereValue(), GetSortValue());

        Cursor cursor =  db.rawQuery(query, null );

        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            allObjects.add(cursor.getLong(cursor.getColumnIndex(sqlHelper.GetPrimaryKey())));
            cursor.moveToNext();
        }

        return allObjects;
    }

    @Override
    public boolean RemoveObject(T object) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(sqlHelper.GetTableName(),  String.format("%s = ? ", sqlHelper.GetPrimaryKey()), new String[] { Long.toString(object.GetID()) }) > 0;
    }

    @Override
    public boolean AddOrUpdateObject(T object) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = sqlHelper.GetContentValue(object);

        if(object.GetID()>0){
            db.update(sqlHelper.GetTableName(), contentValues, String.format("%s = ? ", sqlHelper.GetPrimaryKey()), new String[] { Long.toString(object.GetID()) } );
        }else {
            object.SetID(db.insert(sqlHelper.GetTableName(), null, contentValues));
        }
        return true;
    }
}
