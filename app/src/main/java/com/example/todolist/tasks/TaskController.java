package com.example.todolist.tasks;

public class TaskController {

    protected ITaskStorable taskStorable;

    public  TaskController(ITaskStorable taskStorable){
        this.taskStorable = taskStorable;
    }
}
