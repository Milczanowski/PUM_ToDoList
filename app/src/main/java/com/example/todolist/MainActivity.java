package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import com.example.todolist.appcontroller.Controller;
import com.example.todolist.sqldb.SQLDatabase;

public class MainActivity extends BaseActivity implements IMainViewActions{
    private static final String DATABASE_NAME = "todolist.db";
    private static final int ADD_TASK_ACTIVITY_REQUEST_CODE = 0;

    private SQLDatabase<Task> taskSQLDatabase;
    private Controller<Task> controller;

    private MainView mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskSQLDatabase = new SQLDatabase<>(getApplicationContext(), DATABASE_NAME, new TaskSQLHelper());
        controller = new Controller<>(taskSQLDatabase);

        mainView = new MainView(this,  this);
        controller.AddView(mainView);
    }

    @Override
    public void ShowAddTaskView() {
        Intent intent = new Intent(this, AddTaskActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {
            case ADD_TASK_ACTIVITY_REQUEST_CODE:{
                if(requestCode == RESULT_OK){
                    TryAddTask(data);
                }
            }break;
        }
    }

    private void TryAddTask(Intent data){
        Task task = (Task) data.getSerializableExtra(INTENT_TASK);
        controller.AddObject(task);
    }
}
