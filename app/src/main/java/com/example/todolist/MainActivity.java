package com.example.todolist;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import com.example.todolist.appcontroller.Controller;
import com.example.todolist.sqldb.ISQLHelper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.FileProvider;

public class MainActivity extends BaseActivity implements IMainViewActions{
    private static final String DATABASE_NAME = "todolist.db";
    private static final int DATABASE_VERSION = 1;
    private static final int ADD_TASK_ACTIVITY_REQUEST_CODE = 1;
    private static final int TASK_ACTIVITY_REQUEST_CODE = 2;
    private static final String CHANNEL_ID = "TODOLIST_CHANGEL" ;

    private TaskSQLHelper taskSQLHelper;
    private TaskSQLDatabase taskSQLDatabase;

    private Controller<Task> controller;

    private MainView mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskSQLHelper = new TaskSQLHelper(getApplicationContext(), DATABASE_NAME, DATABASE_VERSION);

        taskSQLDatabase = new TaskSQLDatabase(getApplicationContext(), DATABASE_NAME, taskSQLHelper, 1);

        controller = new Controller<>(taskSQLDatabase);

        mainView = new MainView(this, getApplicationContext(), this);
        controller.AddView(mainView);
        controller.RefreshView();

        CreateNotificationChannel();
        CancelNotification();
    }

    @Override
    protected void onStop() {
        GenerateNotification();
        super.onStop();
    }

    private void GenerateNotification(){
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        ArrayList<Task> allTask = taskSQLDatabase.GetAllObjects();
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date tomorrow = calendar.getTime();

        for(Task task: allTask){
            if((now.after(task.finishDate) || tomorrow.after(task.finishDate)) && task.status != Task.STATUS_DONE){

                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
                builder.setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle(getString(R.string.notification_name))
                        .setContentText(task.name)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setWhen(calendar.getTimeInMillis())
                        .setAutoCancel(true);
                notificationManager.notify((int)task.GetID(), builder.build());
            }
        }
    }

    private void CancelNotification(){
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        ArrayList<Task> allTask = taskSQLDatabase.GetAllObjects();
        for(Task task: allTask){
            notificationManager.cancel((int)task.GetID());
        }
    }

    private void CreateNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void ShowAddTaskView() {
        Intent intent = new Intent(this, AddTaskActivity.class);
        startActivityForResult(intent, ADD_TASK_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void ShowTaskView(Task task) {
        Intent intent = new Intent(this, TaskActivity.class);
        intent.putExtra(INTENT_TASK, task);
        startActivityForResult(intent, TASK_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {
            case ADD_TASK_ACTIVITY_REQUEST_CODE:{
                if(resultCode == RESULT_OK){
                    TryAddTask(data);
                }
            }break;
            case TASK_ACTIVITY_REQUEST_CODE:{
                switch (resultCode)
                {
                    case RESULT_SAVE_TASK:{
                        TryUpdateTask(data);
                    }break;
                    case RESULT_DELETE_TASK:{
                        TryDeleteTask(data);
                    }break;
                }
            }break;
        }
    }

    private void TryAddTask(Intent data){
        if(data == null)
            return;

        Task task = (Task) data.getSerializableExtra(INTENT_TASK);
        if(task!=null)
            controller.AddObject(task);
    }

    private void TryDeleteTask(Intent data){
        if(data == null)
            return;

        Task task = (Task) data.getSerializableExtra(INTENT_TASK);
        if(task!=null)
            controller.RemoveObject(task);
    }

    private void TryUpdateTask(Intent data){
        if(data == null)
            return;

        Task task = (Task) data.getSerializableExtra(INTENT_TASK);
        if(task!=null)
            controller.UpdateObject(task);
    }

    @Override
    public void SetSorting(Integer index) {
        switch (index) {
            case 0:{
                taskSQLHelper.SetSortedByCreateDate();
                taskSQLHelper.SetSortOrder(ISQLHelper.SQLSortOrder.ASC);
            }break;
            case 1:{
                taskSQLHelper.SetSortedByPriority();
                taskSQLHelper.SetSortOrder(ISQLHelper.SQLSortOrder.ASC);
            }break;
            case 2:{
                taskSQLHelper.SetSortedByName();
                taskSQLHelper.SetSortOrder(ISQLHelper.SQLSortOrder.ASC);
            }break;
            case 3:{
                taskSQLHelper.SetSortedByStatus();
                taskSQLHelper.SetSortOrder(ISQLHelper.SQLSortOrder.ASC);
            }break;
            case 4:{
                taskSQLHelper.SetSortedByCloseDate();
                taskSQLHelper.SetSortOrder(ISQLHelper.SQLSortOrder.ASC);
            }break;
            case 5:{
                taskSQLHelper.SetSortedByFinishDate();
                taskSQLHelper.SetSortOrder(ISQLHelper.SQLSortOrder.ASC);
            }break;
            case 6:{
                taskSQLHelper.SetSortedByCreateDate();
                taskSQLHelper.SetSortOrder(ISQLHelper.SQLSortOrder.DESC);
            }break;
            case 7:{
                taskSQLHelper.SetSortedByPriority();
                taskSQLHelper.SetSortOrder(ISQLHelper.SQLSortOrder.DESC);
            }break;
            case 8:{
                taskSQLHelper.SetSortedByName();
                taskSQLHelper.SetSortOrder(ISQLHelper.SQLSortOrder.DESC);
            }break;
            case 9:{
                taskSQLHelper.SetSortedByStatus();
                taskSQLHelper.SetSortOrder(ISQLHelper.SQLSortOrder.DESC);
            }break;
            case 10:{
                taskSQLHelper.SetSortedByCloseDate();
                taskSQLHelper.SetSortOrder(ISQLHelper.SQLSortOrder.DESC);
            }break;
            case 11:{
                taskSQLHelper.SetSortedByFinishDate();
                taskSQLHelper.SetSortOrder(ISQLHelper.SQLSortOrder.DESC);
            }break;
        }
        controller.SortView();
    }

    @Override
    public void ExportTask() {
        ArrayList<Task> allTask = taskSQLDatabase.GetAllObjects();

        String export = "";

        for(Task task: allTask){
            export +=task.Export();
            export += "\n";
        }

        File file  = null;

        try {
            file = File.createTempFile("export_", ".txt", getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS));

            FileWriter writer = new FileWriter(file);
            writer.append(export);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(file!=null){
            Uri uri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + FILE_PROVIDER, file);

              Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "text/plain");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        }
    }
}
