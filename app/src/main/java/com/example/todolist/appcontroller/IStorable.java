package com.example.todolist.appcontroller;

public interface IStorable<T> {
    public T[] GetAllObjects();
    public bool RemoveObject(T object);
    public bool AddOrUpdateObject(T object);
}

