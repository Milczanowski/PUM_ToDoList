package com.example.todolist;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;


public class SmallTaskView extends LinearLayout {
    private TextView name, date, description, priority, status;
    private SimpleDateFormat dateFormat;

    private String[] priorityArray, statusArray;

    public SmallTaskView(Context context, Task task){
        super(context);

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.small_task_layout, this);

        name = findViewById(R.id.nameTextView);
        date = findViewById(R.id.dateTextView);
        description = findViewById(R.id.descriptionTextView);

        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        SetTask(task);
    }

    public void SetTask(Task task){
        SetPriority(task);
        SetStatus(task);

        name.setText(task.name);
        description.setText(task.description);
        date.setText(dateFormat.format(task.finishDate));
    }

    private void SetPriority(Task task){
        if(priority == null) {
            priority = findViewById(R.id.priorityView);
            priorityArray = getResources().getStringArray(R.array.priority_array);
        }

        if(task.priority >= 0 && task.priority < priorityArray.length)
            priority.setText(priorityArray[task.priority]);
    }

    private void SetStatus(Task task){
        if(status == null) {
            status = findViewById(R.id.statusView);
            statusArray = getResources().getStringArray(R.array.status_array);
        }

        if(task.status >= 0 && task.status < statusArray.length)
            status.setText(statusArray[task.status]);
    }
}
