package com.example.todolist;


import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.todolist.appcontroller.IView;

public class MainView implements IView<Task> {
    private Context context;
    private IMainViewActions mainViewActions;
    private LinearLayout tasksLayout;
    private Button addTaskButton;

    public MainView(Activity activity, Context context, IMainViewActions mainViewActions){
        this.mainViewActions = mainViewActions;
        this.context = context;

        tasksLayout = activity.findViewById(R.id.tasksLayout);
        addTaskButton = activity.findViewById(R.id.addTaskButton);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnAddTaskButtonClick();
            }
        });
    }

    private void OnAddTaskButtonClick(){
        mainViewActions.ShowAddTaskView();
    }

    @Override
    public void ViewClearAll() {

    }

    @Override
    public void ViewShow(Task object) {
        TaskView taskView = new TaskView(context, object);
        tasksLayout.addView(taskView);
    }
}
