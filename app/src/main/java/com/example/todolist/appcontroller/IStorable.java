package com.example.todolist.appcontroller;

import java.util.ArrayList;

public interface IStorable<T> {
    public ArrayList<T> GetAllObjects();
    public boolean RemoveObject(T object);
    public boolean AddOrUpdateObject(T object);
}

