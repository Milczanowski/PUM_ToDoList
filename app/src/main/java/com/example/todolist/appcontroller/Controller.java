package com.example.todolist.appcontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class Controller<T extends IIDable> implements IAddable<T>, IRemoveable<T>, IUpdateable<T>{
    protected IStorable<T> storable;
    protected Vector<IView<T>> views;

    protected HashMap<Long, T> Objects;

    public Controller(IStorable<T> storable){
        views = new Vector<>();
        Objects = new HashMap<>();
        this.storable = storable;
    }

    public void RefreshView(){
        for(IView<T> view : views)
            view.ViewClearAll();

        ArrayList<T> allObjects = storable.GetAllObjects();

        Objects.clear();

        for(T object: allObjects){
            Objects.put(object.GetID(), object);
            ViewShowObject(object);
        }
    }

    public void SortView() {
        ArrayList<Long> allObjects = storable.GetSortedIDs();
        ArrayList<IIDable> ordered = new ArrayList<>();

        for (Long id : allObjects) {
            if(Objects.containsKey(id)){
                ordered.add(Objects.get(id));
            }
        }
        ViewSetOrder(ordered);
    }

    private void ViewSetOrder(ArrayList<IIDable> ordered){
        for(IView<T> view : views)
            view.ViewSetOrder(ordered);
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

        if(!Objects.containsKey(object.GetID()))
            Objects.put(object.GetID(), object);

        ViewShowObject(object);
    }

    private void ViewShowObject(T object){
        for(IView<T> view : views)
            view.ViewShow(object);
    }

    @Override
    public void RemoveObject(T object) {
        if(Objects.containsKey(object.GetID()))
            Objects.remove(object.GetID());

        storable.RemoveObject(object);

        ViewRemoveObject(object);
    }

    private void ViewRemoveObject(T object){
        for(IView<T> view : views)
            view.ViewRemove(object);
    }

    @Override
    public void UpdateObject(T object) {
        storable.AddOrUpdateObject(object);

        ViewUpdateObject(object);
    }

    private void ViewUpdateObject(T object){
        for(IView<T> view : views)
            view.ViewUpdate(object);
    }
}
