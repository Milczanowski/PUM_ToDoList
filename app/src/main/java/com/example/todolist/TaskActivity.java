package com.example.todolist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class TaskActivity extends BaseActivity {
    private LinearLayout contentLayout, closeTimeLayout, createTimeLayout;
    private TextView createTextView, closeTextView;
    private Button deleteButton;

    private SimpleDateFormat dateFormat;

    private EditTaskView editTaskView;

    private Task task;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        contentLayout = findViewById(R.id.contentLayout);

        task = (Task) getIntent().getSerializableExtra(INTENT_TASK);


        editTaskView = new EditTaskView(TaskActivity.this, task);
        contentLayout.addView(editTaskView,0);
        editTaskView.setFocusable(false);

        dateFormat = new SimpleDateFormat("HH:mm dd-MM-yyyy");

        createTextView = findViewById(R.id.createTextView);
        closeTextView = findViewById(R.id.closeTextView);

        createTimeLayout = findViewById(R.id.createTimeLinearLayout);
        closeTimeLayout = findViewById(R.id.closeTimeLinearLayout);

        if(task.createDate!= null)
            createTextView.setText(dateFormat.format(task.createDate));
        else createTimeLayout.setVisibility(View.INVISIBLE);

        if(task.closeDate!= null)
            closeTextView.setText(dateFormat.format(task.closeDate));
        else closeTimeLayout.setVisibility(View.INVISIBLE);

        SetDeleteButton();
    }

    private void SetDeleteButton(){
        deleteButton = findViewById(R.id.deleteButton);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDeleteDialog();
            }
        });
    }

    private void ShowDeleteDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(TaskActivity.this);

        builder.setTitle(R.string.delete_dialog);
        builder.setMessage(R.string.delete_dialog_message);
        builder.setPositiveButton(R.string.yes_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent();
                        intent.putExtra(INTENT_TASK, task);
                        setResult(RESULT_DELETE_TASK, intent);
                        finish();
                    }
                });
        builder.setNegativeButton(R.string.no_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        builder.create();
        builder.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(INTENT_TASK, task);
        setResult(RESULT_SAVE_TASK, intent);
        super.onBackPressed();
    }
}