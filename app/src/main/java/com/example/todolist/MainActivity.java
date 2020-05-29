package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.todolist.appcontroller.Controller;
import com.example.todolist.appcontroller.IAddable;
import com.example.todolist.sqldb.SQLDatabase;

public class MainActivity extends AppCompatActivity implements IMainViewActions{
    private static final String DATABASE_NAME = "todolist.db";

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
    }

    @Override
    public void AddTask() {
        Intent intent = new Intent(this, AddTaskActivity.class);
        intent.putExtras("taskAddable", controller);
        startActivity(intent);
    }
}
