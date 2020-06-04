package com.example.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.todolist.sqldb.ISQLHelper;
import com.example.todolist.sqldb.SQLDatabase;

import java.util.ArrayList;

public class TaskSQLDatabase extends SQLDatabase<Task> {

    private AttachmentSQLHelper attachmentSQLHelper;
    private SQLDatabase<Attachment> attachmentSQLDatabase;

    public TaskSQLDatabase(Context context, String databaseName, ISQLHelper<Task> taskSQLHelper, int version) {
        super(context, databaseName, taskSQLHelper, version);

        attachmentSQLHelper = new AttachmentSQLHelper(taskSQLHelper.GetTableName(), taskSQLHelper.GetPrimaryKey());

        attachmentSQLDatabase = new SQLDatabase<>(context, databaseName, attachmentSQLHelper, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        super.onCreate(sqLiteDatabase);

        attachmentSQLDatabase.onCreate(sqLiteDatabase);
    }

    @Override
    public ArrayList<Task> GetAllObjects() {
        ArrayList<Task> tasks = super.GetAllObjects();

        for(Task task: tasks){
            attachmentSQLHelper.SetTaskID(task.GetID());
            task.attachments = attachmentSQLDatabase.GetAllObjects();
        }

        return tasks;
    }

    @Override
    public boolean RemoveObject(Task object){
        if(super.RemoveObject(object)){
            for(Attachment attachment : object.attachments)
                attachmentSQLDatabase.RemoveObject(attachment);

            for(Attachment attachment : object.deleteAttachments){
                attachmentSQLDatabase.RemoveObject(attachment);
            }

            return  true;
        }
        return false;
    }

    @Override
    public boolean AddOrUpdateObject(Task object) {
        if(super.AddOrUpdateObject(object)){
            for(Attachment attachment : object.deleteAttachments){
                attachmentSQLDatabase.RemoveObject(attachment);
            }

            object.deleteAttachments.clear();

            for(Attachment attachment : object.attachments) {
                attachment.task = object.GetID();
                attachmentSQLDatabase.AddOrUpdateObject(attachment);
            }

            return  true;
        }
        return false;
    }
}
