package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;

public class AddTaskActivity extends BaseActivity {

    private EditText nameEditText, descriptionEditText;
    private Button addTaskButton;
    private Spinner statusSpinner, prioritySpinner;

    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        task = new Task();

        nameEditText = findViewById(R.id.nameEditText);

        descriptionEditText = findViewById(R.id.descriptionEditText);
        addTaskButton = findViewById(R.id.addTaskButton);

        SetStatusSpinner();
        SetPrioritySpinner();

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddTask();
            }
        });
    }

    private void SetStatusSpinner(){
        statusSpinner = findViewById(R.id.statusSpinner);
        statusSpinner.setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.status_array)));

        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                task.status = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
        }});
    }

    private void SetPrioritySpinner(){
        prioritySpinner = findViewById(R.id.prioritySpinner);

        prioritySpinner.setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.priority_array)));

        prioritySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                task.priority = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
         }});
    }

    private void AddTask(){
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
