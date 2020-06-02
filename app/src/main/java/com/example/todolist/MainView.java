package com.example.todolist;


import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.todolist.appcontroller.IView;

import java.util.HashMap;

public class MainView implements IView<Task> {
    private Context context;
    private IMainViewActions mainViewActions;
    private LinearLayout tasksLayout;
    private Button addTaskButton;

    HashMap<Long, SmallTaskView> viewMap;

    public MainView(Activity activity, Context context, IMainViewActions mainViewActions){
        this.mainViewActions = mainViewActions;
        this.context = context;

        viewMap = new HashMap<>();

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
        tasksLayout.removeAllViews();
        viewMap.clear();
    }

    @Override
    public void ViewShow(Task object) {
        SmallTaskView taskView = new SmallTaskView(context, object);
        tasksLayout.addView(taskView);

        viewMap.put(object.GetID(),taskView);

        taskView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainViewActions.ShowTaskView(object);
            }
        });
    }

    @Override
    public void ViewRemove(Task object) {
        if(viewMap.containsKey(object.GetID())){
            View view = viewMap.get(object.GetID());
            tasksLayout.removeView(view);
            viewMap.remove(object.GetID());
        }
    }

    @Override
    public void ViewUpdate(Task object) {
        if(viewMap.containsKey(object.GetID())) {
            viewMap.get(object.GetID()).SetTask(object);
        }
    }
}
