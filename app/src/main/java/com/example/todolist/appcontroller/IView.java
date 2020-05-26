package com.example.todolist.appcontroller;

public interface IView<T> {
    public void ViewClearAll();
    public void ViewShow(T object);
}
