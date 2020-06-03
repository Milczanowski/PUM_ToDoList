package com.example.todolist.appcontroller;

import java.util.ArrayList;

public interface IView<T extends IIDable> {
    public void ViewClearAll();
    public void ViewShow(T object);
    public void ViewUpdate(T object);
    public void ViewRemove(T object);
    public void ViewSetOrder(ArrayList<IIDable> ordered);
}
