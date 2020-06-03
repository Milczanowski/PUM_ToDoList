package com.example.todolist;

import com.example.todolist.appcontroller.IIDable;
import com.example.todolist.sqldb.ISQLObject;

import java.io.Serializable;
import java.util.Date;

public class Task implements ISQLObject, IIDable, Serializable {
    private long id;
    public String name, description;
    public int status, priority;
    public Date createDate, closeDate, finishDate;

    public Task(){
        id= -1;
        createDate = null;
        closeDate = null;
        finishDate = null;
    }

    @Override
    public void SetID(long id) {
        this.id = id;
    }

    @Override
    public long GetID() {
        return id;
    }

}
