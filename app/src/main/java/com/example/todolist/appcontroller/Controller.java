package com.example.todolist.appcontroller;

import java.util.ArrayList;
import java.util.Vector;

public class Controller<T> implements IAddable<T>, IRemoveable<T>, IUpdateable<T>{
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
            ViewShowObject(allObjects.get(i));
    }

    public void AddView(IView<T> view){
        views.add(view);
    }

    public void RemoveView(IView<T> view){
        views.remove(view);
    }

    @Override
    public void AddObject(T object) {
        storable.AddOrUpdateObject(object);

        ViewShowObject(object);
    }

    private void ViewShowObject(T object){
        for(int i = 0; i < views.size();++i)
            views.get(i).ViewShow(object);
    }

    @Override
    public void RemoveObject(T object) {
        storable.RemoveObject(object);

        ViewRemoveObject(object);
    }

    private void ViewRemoveObject(T object){
        for(int i = 0; i < views.size();++i)
            views.get(i).ViewRemove(object);
    }

    @Override
    public void UpdateObject(T object) {
        storable.AddOrUpdateObject(object);

        ViewUpdateObject(object);
    }

    private void ViewUpdateObject(T object){
        for(int i = 0; i < views.size();++i)
            views.get(i).ViewUpdate(object);
    }
}
