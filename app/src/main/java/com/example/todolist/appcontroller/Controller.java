package com.example.todolist.appcontroller;

import java.util.Vector;

public class Controller<T> implements IAddable<T>{
    protected IStorable<T> storable;
    protected Vector<IView<T>> views;

    public Controller(IStorable<T> storable){
        views = new Vector<>();
        this.storable = storable;
    }

    public void AddView(IView<T> view){
        views.add(view);
    }

    public void RemoveView(IView<T> view){
        views.remove(view);
    }

    @Override
    public void AddObject(final T object) {
        storable.AddOrUpdateObject(object);
        views.forEach((view)-> view.ViewShow(object));
    }
}
