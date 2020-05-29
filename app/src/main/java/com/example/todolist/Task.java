package com.example.todolist;

import com.example.todolist.sqldb.ISQLObject;

import java.io.Serializable;
import java.util.Date;

public class Task implements ISQLObject, Serializable {
    private long id;
    public String name, description;
    public int status, priority;
    public Date createDate, closeDate;

    public Task(){
        id= -1;
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
