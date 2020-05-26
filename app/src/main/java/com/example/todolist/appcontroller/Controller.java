package com.example.todolist.appcontroller;

import java.util.HashSet;

public class Controller<T> implements IAddable<T>{
    protected IStorable<T> storable;
    protected HashSet<IView<T>> taskViews;

    public Controller(IStorable<T> storable){
        taskViews = new HashSet<>();
        this.storable = storable;
    }

    public void AddView(IView<T> view){
        taskViews.add(view);
    }

    public void RemoveView(IView<T> view){
        taskViews.remove(view);
    }

    @Override
    public void AddObject(T object) {

    }
}
