package com.example.todolist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;

public class AttachmentView extends LinearLayout {
    private TextView name;
    private Button deleteButton;
    private IAttachmentDeleteable attachmentDeleteable;
    private Attachment attachment;
    protected Context context;

    public AttachmentView(Context context, Attachment attachment, IAttachmentDeleteable attachmentDeleteable) {
        super(context);

        this.context = context;
        this.attachment = attachment;
        this.attachmentDeleteable = attachmentDeleteable;

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.attachment_layout, this);

        name = findViewById(R.id.attachmentNameTextView);

        File file= new File(attachment.path);
        name.setText(file.getName());
        deleteButton = findViewById(R.id.deleteAttachmentButton);

        deleteButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDeleteDialog();
            }
        });
    }

    private void DeleteAttachment(){
        attachmentDeleteable.DeleteAttachment(attachment);;
    }

    private void ShowDeleteDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(R.string.delete_attachment_dialog);
        builder.setMessage(R.string.delete_attachment_dialog_message);
        builder.setPositiveButton(R.string.yes_button, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                DeleteAttachment();
            }
        });
        builder.setNegativeButton(R.string.no_button, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.create();
        builder.show();
    }
}
