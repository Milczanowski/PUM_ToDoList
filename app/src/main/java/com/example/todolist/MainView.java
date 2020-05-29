package com.example.todolist;


import android.app.Activity;
import android.view.View;
import android.widget.Button;

public class MainView {
    private IMainViewActions mainViewActions;

    private Button addTaskButton;

    public MainView(Activity actvity , IMainViewActions mainViewActions){
        this.mainViewActions = mainViewActions;

        addTaskButton = actvity.findViewById(R.id.addTaskButton);
        addTaskButton.setOnClickListener(OnAddTaskButtonClick());
    }

    private View.OnClickListener OnAddTaskButtonClick(){
        return  new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainViewActions.AddTask();
            }
        };
    }
}
