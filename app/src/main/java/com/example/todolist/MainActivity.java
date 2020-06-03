package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;

import com.example.todolist.appcontroller.Controller;
import com.example.todolist.sqldb.ISQLHelper;
import com.example.todolist.sqldb.SQLDatabase;

public class MainActivity extends BaseActivity implements IMainViewActions{
    private static final String DATABASE_NAME = "todolist.db";
    private static final int DATABASE_VERSION = 1;
    private static final int ADD_TASK_ACTIVITY_REQUEST_CODE = 1;
    private static final int TASK_ACTIVITY_REQUEST_CODE = 2;

    private TaskSQLHelper taskSQLHelper;
    private SQLDatabase<Task> taskSQLDatabase;

    private Controller<Task> controller;

    private MainView mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskSQLHelper = new TaskSQLHelper(getApplicationContext(), DATABASE_NAME, DATABASE_VERSION);
        taskSQLDatabase = new SQLDatabase<>(getApplicationContext(), DATABASE_NAME, taskSQLHelper, 1);

        controller = new Controller<>(taskSQLDatabase);

        mainView = new MainView(this, getApplicationContext(), this);
        controller.AddView(mainView);
        controller.RefreshView();
    }

    @Override
    public void ShowAddTaskView() {
        Intent intent = new Intent(this, AddTaskActivity.class);
        startActivityForResult(intent, ADD_TASK_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void ShowTaskView(Task task) {
        Intent intent = new Intent(this, TaskActivity.class);
        intent.putExtra(INTENT_TASK, task);
        startActivityForResult(intent, TASK_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {
            case ADD_TASK_ACTIVITY_REQUEST_CODE:{
                if(resultCode == RESULT_OK){
                    TryAddTask(data);
                }
            }break;
            case TASK_ACTIVITY_REQUEST_CODE:{
                switch (resultCode)
                {
                    case RESULT_SAVE_TASK:{
                        TryUpdateTask(data);
                    }break;
                    case RESULT_DELETE_TASK:{
                        TryDeleteTask(data);
                    }break;
                }
            }break;
        }
    }

    private void TryAddTask(Intent data){
        if(data == null)
            return;

        Task task = (Task) data.getSerializableExtra(INTENT_TASK);
        if(task!=null)
            controller.AddObject(task);
    }

    private void TryDeleteTask(Intent data){
        if(data == null)
            return;

        Task task = (Task) data.getSerializableExtra(INTENT_TASK);
        if(task!=null)
            controller.RemoveObject(task);
    }

    private void TryUpdateTask(Intent data){
        if(data == null)
            return;

        Task task = (Task) data.getSerializableExtra(INTENT_TASK);
        if(task!=null)
            controller.UpdateObject(task);
    }

    @Override
    public void SetSorting(Integer index) {
        switch (index) {
            case 0:{
                taskSQLHelper.SetSortedByCreateDate();
                taskSQLHelper.SetSortOrder(ISQLHelper.SQLSortOrder.ASC);
            }break;
            case 1:{
                taskSQLHelper.SetSortedByPriority();
                taskSQLHelper.SetSortOrder(ISQLHelper.SQLSortOrder.ASC);
            }break;
            case 2:{
                taskSQLHelper.SetSortedByName();
                taskSQLHelper.SetSortOrder(ISQLHelper.SQLSortOrder.ASC);
            }break;
            case 3:{
                taskSQLHelper.SetSortedByStatus();
                taskSQLHelper.SetSortOrder(ISQLHelper.SQLSortOrder.ASC);
            }break;
            case 4:{
                taskSQLHelper.SetSortedByCloseDate();
                taskSQLHelper.SetSortOrder(ISQLHelper.SQLSortOrder.ASC);
            }break;
            case 5:{
                taskSQLHelper.SetSortedByFinishDate();
                taskSQLHelper.SetSortOrder(ISQLHelper.SQLSortOrder.ASC);
            }break;
            case 6:{
                taskSQLHelper.SetSortedByCreateDate();
                taskSQLHelper.SetSortOrder(ISQLHelper.SQLSortOrder.DESC);
            }break;
            case 7:{
                taskSQLHelper.SetSortedByPriority();
                taskSQLHelper.SetSortOrder(ISQLHelper.SQLSortOrder.DESC);
            }break;
            case 8:{
                taskSQLHelper.SetSortedByName();
                taskSQLHelper.SetSortOrder(ISQLHelper.SQLSortOrder.DESC);
            }break;
            case 9:{
                taskSQLHelper.SetSortedByStatus();
                taskSQLHelper.SetSortOrder(ISQLHelper.SQLSortOrder.DESC);
            }break;
            case 10:{
                taskSQLHelper.SetSortedByCloseDate();
                taskSQLHelper.SetSortOrder(ISQLHelper.SQLSortOrder.DESC);
            }break;
            case 11:{
                taskSQLHelper.SetSortedByFinishDate();
                taskSQLHelper.SetSortOrder(ISQLHelper.SQLSortOrder.DESC);
            }break;
        }
        controller.SortView();
    }
}
