package com.example.todolist.appcontroller;

import android.util.Log;

import java.util.ArrayList;
import java.util.Vector;

public class Controller<T> implements IAddable<T>{
    protected IStorable<T> storable;
    protected Vector<IView<T>> views;

    public Controller(IStorable<T> storable){
        views = new Vector<>();
        this.storable = storable;
    }

    public void RefreshView(){
        for(int i = 0; i < views.size();++i)
            views.get(i).ViewClearAll();

        ArrayList<T> allObjects = storable.GetAllObjects();

        for(int i = 0; i < allObjects.size(); ++i)
            ShowObject(allObjects.get(i));
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

        ShowObject(object);
    }

    private void ShowObject(T object){
        for(int i = 0; i < views.size();++i)
            views.get(i).ViewShow(object);
    }


}
