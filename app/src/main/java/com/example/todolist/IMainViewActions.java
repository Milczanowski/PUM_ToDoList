package com.example.todolist;

public interface IMainViewActions {
    public void ShowAddTaskView();
    public void ShowTaskView(Task task);
    public void SetSorting(Integer index);
    public void ExportTask();
}
