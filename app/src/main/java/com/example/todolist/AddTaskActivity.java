package com.example.todolist;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.LinearLayout;


public class AddTaskActivity extends BaseActivity {
    private Button addTaskButton;
    private LinearLayout contentLayout;

    private Task task;
    private EditTaskView editTaskView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        task = new Task();

        contentLayout = findViewById(R.id.contentLayout);
        editTaskView = new EditTaskView(AddTaskActivity.this, task);
        contentLayout.addView(editTaskView,0);

        addTaskButton = findViewById(R.id.addTaskButton);

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddTask();
            }
        });
    }

    private void AddTask(){
        Intent intent = new Intent();
        intent.putExtra(INTENT_TASK, task);
        setResult(RESULT_OK, intent);
        finish();
    }
}
