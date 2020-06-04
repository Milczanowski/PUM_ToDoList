package com.example.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;

public class AttachmentView extends LinearLayout {
    private TextView name;
    private Button deleteButton;
    private IAttachemntDeleteable attachemntDeleteable;
    private Attachment attachment;

    public AttachmentView(Context context, Attachment attachment, IAttachemntDeleteable attachemntDeleteable) {
        super(context);

        this.attachment = attachment;
        this.attachemntDeleteable = attachemntDeleteable;

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.attachment_layout, this);

        name = findViewById(R.id.attachmentNameTextView);

        File file= new File(attachment.path);
        name.setText(file.getName());
        deleteButton = findViewById(R.id.deleteButton);

        deleteButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
