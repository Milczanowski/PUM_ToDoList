package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.todolist.appcontroller.Controller;
import com.example.todolist.sqldb.SQLDatabase;

public class MainActivity extends AppCompatActivity {
    private static final String DATABASE_NAME = "todolist.db";

    private SQLDatabase<Task> taskSQLDatabase;
    private Controller<Task> controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskSQLDatabase = new SQLDatabase<>(getApplicationContext(), DATABASE_NAME, new TaskSQLHelper());

        controller = new Controller<>(taskSQLDatabase);
    }
}
