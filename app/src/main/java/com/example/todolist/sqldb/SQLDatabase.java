package com.example.todolist.sqldb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.todolist.appcontroller.IStorable;

import java.util.ArrayList;
import java.util.Map;

public class SQLDatabase <T extends  ISQLObject> extends SQLiteOpenHelper implements IStorable<T> {

    public static final String COLUMN_ID = "id";

    private ISQLHelper<T> sqlHelper;

    public SQLDatabase(Context context, String databaseName, ISQLHelper<T> sqlHelper) {
        super(context, databaseName , null, 1);
        this.sqlHelper = sqlHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String columns = "";

        for ( Map.Entry<String, String> column : sqlHelper.GetColumns().entrySet()){
            columns += String.format(", %s %s", column.getKey(), column.getValue());
        }

        sqLiteDatabase.execSQL(String.format("create table %s (%s integer primary key %s)", sqlHelper.GetTableName(), COLUMN_ID, columns));
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(String.format("DROP TABLE IF EXISTS %s", sqlHelper.GetTableName()));
        onCreate(sqLiteDatabase);
    }

    @Override
    public ArrayList<T> GetAllObjects() {
        ArrayList<T> allObjects = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( String.format("SELECT * from %s", sqlHelper.GetTableName()), null );
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false) {
            T obj = sqlHelper.GetObject(cursor);
            obj.SetID(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));

            allObjects.add(obj);
        }

        return allObjects;
    }

    @Override
    public boolean RemoveObject(T object) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(sqlHelper.GetTableName(),  String.format("%s = ? ", COLUMN_ID), new String[] { Long.toString(object.GetID()) }) > 0;
    }

    @Override
    public boolean AddOrUpdateObject(T object) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = sqlHelper.GetContentValue(object);

        if(object.GetID()>0){
            db.update(sqlHelper.GetTableName(), contentValues, String.format("%s = ? ", COLUMN_ID), new String[] { Long.toString(object.GetID()) } );
        }else {
            object.SetID(db.insert(sqlHelper.GetTableName(), null, contentValues));
        }
        return true;
    }
}
