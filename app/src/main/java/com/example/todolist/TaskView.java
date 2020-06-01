package com.example.todolist;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;


public class TaskView extends LinearLayout {
    private TextView name, date, description;

    public TaskView(Context context, Task task){
        super(context);

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.task_layout, this);

        name= findViewById(R.id.nameTextView);
        date= findViewById(R.id.dateTextView);
        description= findViewById(R.id.descriptionTextView);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        name.setText(task.name);
        description.setText(task.description);
        date.setText(dateFormat.format(task.closeDate));
    }

}
