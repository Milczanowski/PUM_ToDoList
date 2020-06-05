package com.example.todolist;

import com.example.todolist.appcontroller.IIDable;
import com.example.todolist.sqldb.ISQLObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Task implements ISQLObject, IIDable, Serializable, IExportable {
    private long id;
    public String name, description;
    public int status, priority;
    public Date createDate, closeDate, finishDate;
    public ArrayList<Attachment> attachments;
    public ArrayList<Attachment> deleteAttachments;

    public Task(){
        id= -1;
        createDate = null;
        closeDate = null;
        finishDate = null;
        attachments = new ArrayList<>();
        deleteAttachments = new ArrayList<>();
    }

    @Override
    public void SetID(long id) {
        this.id = id;
    }

    @Override
    public long GetID() {
        return id;
    }



    @Override
    public String Export() {
        String export = "Task: " + id + '\n' +
                "Name: " + name + '\n' +
                "Description='" + description + '\n' +
                "Status=" + status + '\n' +
                "Priority=" + priority + '\n' +
                "CreateDate=" + createDate + '\n';

        if(closeDate!= null)
            export += "CloseDate=" + closeDate + '\n';

        if(finishDate != null)
            export +=  "FinishDate=" + finishDate+ '\n';

        return export;
    }
}
