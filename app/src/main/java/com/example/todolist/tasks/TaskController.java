package com.example.todolist.tasks;

import java.util.HashSet;


public class TaskController {
    protected ITaskStorable taskStorable;
    protected HashSet<ITaskView> taskViews;

    public TaskController(ITaskStorable taskStorable){
        taskViews = new HashSet<>();
        this.taskStorable = taskStorable;
    }

    public void AddView(ITaskView view){
        taskViews.add(view);
    }

    public void RemoveView(ITaskView view){
        taskViews.remove(view);
    }
}
