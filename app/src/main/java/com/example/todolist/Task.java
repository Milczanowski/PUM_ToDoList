package com.example.todolist;

import com.example.todolist.sqldb.ISQLObject;

import java.util.Date;

public class Task implements ISQLObject {
    private long id;
    public String name, description;
    public int status, priority;
    public Date createDate, closeDate;

    @Override
    public void SetID(long id) {
        this.id = id;
    }

    @Override
    public long GetID() {
        return id;
    }

}
