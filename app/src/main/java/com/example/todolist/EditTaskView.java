package com.example.todolist;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
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
import java.util.HashMap;

public class EditTaskView extends LinearLayout implements IPhotoReceiver, IAttachmentDeleteable {

    private EditText nameEditText, descriptionEditText;
    private Spinner statusSpinner, prioritySpinner;
    private TextView dateTextView, timeTextView;
    private LinearLayout dateLinearLayout, timeLinearLayout, attachmentLayout;
    private Button addAttachemntButton;
    private Calendar calendar;

    private SimpleDateFormat dateFormat, timeFormat;

    private Task task;
    private Context context;
    private ICamera camera;

    private HashMap<Long, AttachmentView> attachmentViews;

    public EditTaskView(Context context, Task task, ICamera camera) {
        super(context);

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.edit_task_layout, this);

        this.camera = camera;
        this.task = task;
        this.context = context;

        calendar = Calendar.getInstance();

        SetNameEdit();
        SetDescriptionEdit();
        SetStatusSpinner();
        SetPrioritySpinner();
        SetFinishDate();
        SetAttachment();

        addAttachemntButton = findViewById(R.id.addAttachmentButton);
        addAttachemntButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                OnAddAttachment();
            }
        });
    }

    private void SetNameEdit(){
        nameEditText = findViewById(R.id.nameEditText);
        nameEditText.setText(task.name);

        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                task.name = nameEditText.getText().toString();
            }
        });
    }

    private void SetDescriptionEdit(){
        descriptionEditText = findViewById(R.id.descriptionEditText);
        descriptionEditText.setText(task.description);

        descriptionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                task.description = descriptionEditText.getText().toString();
            }
        });
    }

    private void SetStatusSpinner(){
        statusSpinner = findViewById(R.id.statusSpinner);
        statusSpinner.setAdapter(new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.status_array)));

        statusSpinner.setSelection(task.status);

        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                task.status = i;

                if(i ==2 && task.closeDate ==null){
                    task.closeDate = Calendar.getInstance().getTime();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }});
    }

    private void SetPrioritySpinner(){
        prioritySpinner = findViewById(R.id.prioritySpinner);

        prioritySpinner.setAdapter(new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.priority_array)));

        prioritySpinner.setSelection(task.priority);

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

        if(task.finishDate!=null)
            calendar.setTime(task.finishDate);
        else
            task.finishDate = calendar.getTime();

        dateTextView.setText(dateFormat.format(calendar.getTime()));
        timeTextView.setText(timeFormat.format(calendar.getTime()));

        dateLinearLayout = findViewById(R.id.dateLinearLayout);

        dateLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        calendar.set(year,month,day);
                        dateTextView.setText(dateFormat.format(calendar.getTime()));
                        task.finishDate = calendar.getTime();
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

                new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        calendar.set(year,month,day, hour, minute);
                        timeTextView.setText(timeFormat.format(calendar.getTime()));
                        task.finishDate = calendar.getTime();
                    }
                },hour, minute, true).show();
            }
        });
    }

    private void SetAttachment(){
        attachmentViews = new HashMap<>();
        attachmentLayout = findViewById(R.id.attachment_layout);

        for(Attachment attachment: task.attachments)
        {
            AttachmentView attachmentView = new AttachmentView(context,attachment, this);
            attachmentLayout.addView(attachmentView);
            attachmentViews.put(attachment.GetID(), attachmentView);
        }
    }

    private void OnAddAttachment(){
        camera.TakePhoto(this);
    }

    @Override
    public void ReceivePhoto(String path) {
        if(path == null)
            return;
        if(path.isEmpty())
            return;

        Log.d("TODO_1", path);

        Attachment attachment = new Attachment();
        attachment.task = task.GetID();
        attachment.path = path;
        task.attachments.add(attachment);

        AttachmentView attachmentView = new AttachmentView(context,attachment, this);
        attachmentLayout.addView(attachmentView);
        attachmentViews.put(attachment.GetID(), attachmentView);
    }

    @Override
    public void DeleteAttachment(Attachment attachment) {
        task.attachments.remove(attachment);
        if(attachmentViews.containsKey(attachment.GetID())){
            attachmentLayout.removeView(attachmentViews.get(attachment.GetID()));
            attachmentViews.remove(attachment.GetID());
            task.deleteAttachments.add(attachment);
        }
    }
}
