package com.example.todolist;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;


public class TaskView extends LinearLayout {
    private TextView name, date, description, priority, status;

    public TaskView(Context context, Task task){
        super(context);

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.task_layout, this);

        name = findViewById(R.id.nameTextView);
        date = findViewById(R.id.dateTextView);
        description = findViewById(R.id.descriptionTextView);

        SetPriority(task);
        SetStatus(task);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        name.setText(task.name);
        description.setText(task.description);
        date.setText(dateFormat.format(task.closeDate));
    }

    private void SetPriority(Task task){
        priority = findViewById(R.id.priorityView);

        String[] priorityArray = getResources().getStringArray(R.array.priority_array);

        if(task.priority >= 0 && task.priority < priorityArray.length)
            priority.setText(priorityArray[task.priority]);
    }

    private void SetStatus(Task task){
        status = findViewById(R.id.statusView);

        String[] statusArray = getResources().getStringArray(R.array.status_array);

        if(task.priority >= 0 && task.priority < statusArray.length)
            status.setText(statusArray[task.priority]);
    }


}
