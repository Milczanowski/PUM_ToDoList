package com.example.todolist.appcontroller;

public interface IView<T> {
    public void ViewClearAll();
    public void ViewShow(T object);
    public void ViewUpdate(T object);
    public void ViewRemove(T object);
}
