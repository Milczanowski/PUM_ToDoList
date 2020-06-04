package com.example.todolist;

import com.example.todolist.sqldb.ISQLObject;

import java.io.Serializable;

public class Attachment implements ISQLObject, Serializable {
    private long id;
    public long task;
    public String path;

    @Override
    public void SetID(long id) {
        this.id = id;
    }

    @Override
    public long GetID() {
        return id;
    }
}
