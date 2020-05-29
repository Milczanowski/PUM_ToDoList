package com.example.todolist;


import android.app.Activity;
import android.view.View;
import android.widget.Button;

public class MainView {
    private IMainViewActions mainViewActions;

    private Button addTaskButton;

    public MainView(Activity activity , IMainViewActions mainViewActions){
        this.mainViewActions = mainViewActions;

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
}
