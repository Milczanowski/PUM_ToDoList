package com.example.todolist;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddTaskActivity extends BaseActivity {

    private EditText nameEditText, descriptionEditText;
    private Button addTaskButton;
    private Spinner statusSpinner, prioritySpinner;
    private TextView dateTextView, timeTextView;
    private LinearLayout dateLinearLayout, timeLinearLayout;
    private Calendar calendar;

    SimpleDateFormat dateFormat, timeFormat;

    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        task = new Task();

        calendar = Calendar.getInstance();

        nameEditText = findViewById(R.id.nameEditText);

        descriptionEditText = findViewById(R.id.descriptionEditText);
        addTaskButton = findViewById(R.id.addTaskButton);

        SetStatusSpinner();
        SetPrioritySpinner();
        SetFinishDate();

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

    private void SetFinishDate(){
        dateTextView = findViewById(R.id.dateTextView);
        timeTextView = findViewById(R.id.timeTextView);

        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        timeFormat = new SimpleDateFormat("HH:mm");

        dateTextView.setText(dateFormat.format(calendar.getTime()));
        timeTextView.setText(timeFormat.format(calendar.getTime()));

        dateLinearLayout = findViewById(R.id.dateLinearLayout);

        dateLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(AddTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        calendar.set(year,month,day);
                        dateTextView.setText(dateFormat.format(calendar.getTime()));
                    }
                }, year, month, day).show();
            }
        });

        timeLinearLayout = findViewById(R.id.timeLinearLayout);

        timeLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour = calendar.get(Calendar.HOUR);
                int minute = calendar.get(Calendar.MINUTE);

                new TimePickerDialog(AddTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        calendar.set(year,month,day, hour, minute);
                        timeTextView.setText(timeFormat.format(calendar.getTime()));
                    }
                },hour, minute, true).show();
            }
        });
    }

    private void AddTask(){
        task.name = nameEditText.getText().toString();
        task.description = descriptionEditText.getText().toString();
        task.createDate = Calendar.getInstance().getTime();
        task.finishDate = calendar.getTime();

        Intent intent = new Intent();
        intent.putExtra(INTENT_TASK, task);
        setResult(RESULT_OK, intent);
        finish();
    }
}
