package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

public class AddTaskActivity extends BaseActivity {

    EditText nameEditText, descriptionEditText;
    Button addTaskButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        nameEditText = findViewById(R.id.nameEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        addTaskButton = findViewById(R.id.addTaskButton);

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddTask();
            }
        });
    }

    private void AddTask(){
        Task task = new Task();
        task.name = nameEditText.getText().toString();
        task.description = descriptionEditText.getText().toString();
        task.createDate = Calendar.getInstance().getTime();
        task.closeDate = Calendar.getInstance().getTime();

        Intent intent = new Intent();
        intent.putExtra(INTENT_TASK, task);
        setResult(RESULT_OK, intent);
        finish();
    }
}
