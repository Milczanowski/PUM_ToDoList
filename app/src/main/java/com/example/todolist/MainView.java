package com.example.todolist;


import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.todolist.appcontroller.IIDable;
import com.example.todolist.appcontroller.IView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainView implements IView<Task> {
    private Context context;
    private IMainViewActions mainViewActions;
    private LinearLayout tasksLayout;
    private Button addTaskButton, exportButton;
    private Spinner sortSpinner;
    private Activity activity;

    HashMap<Long, SmallTaskView> viewMap;

    public MainView(Activity activity, Context context, IMainViewActions mainViewActions){
        this.mainViewActions = mainViewActions;
        this.context = context;
        this.activity = activity;

        viewMap = new HashMap<>();

        tasksLayout = activity.findViewById(R.id.tasksLayout);
        addTaskButton = activity.findViewById(R.id.addTaskButton);
        exportButton = activity.findViewById(R.id.exportButton);
        SetSortSpinner();

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnAddTaskButtonClick();
            }
        });

        exportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnExportButtonClick();
            }
        });
    }

    private void OnExportButtonClick(){
        mainViewActions.ExportTask();
    }

    private void OnAddTaskButtonClick(){
        mainViewActions.ShowAddTaskView();
    }

    private void SetSortSpinner(){
        sortSpinner = activity.findViewById(R.id.sortSpinner);
        sortSpinner.setAdapter(new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item,
                activity.getResources().getStringArray(R.array.sorting_array)));

        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mainViewActions.SetSorting(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void ViewClearAll() {
        tasksLayout.removeAllViews();
        viewMap.clear();
    }

    @Override
    public void ViewShow(Task object) {
        SmallTaskView taskView = new SmallTaskView(context, object);
        tasksLayout.addView(taskView);

        viewMap.put(object.GetID(),taskView);

        taskView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainViewActions.ShowTaskView(object);
            }
        });
    }

    @Override
    public void ViewRemove(Task object) {
        if(viewMap.containsKey(object.GetID())){
            View view = viewMap.get(object.GetID());
            tasksLayout.removeView(view);
            viewMap.remove(object.GetID());
        }
    }

    @Override
    public void ViewSetOrder(ArrayList<IIDable> ordered) {
        tasksLayout.removeAllViews();

        for(IIDable iiDable: ordered){
            Long id =  iiDable.GetID();

            if(!viewMap.containsKey(id))
                continue;

            View view = viewMap.get(id);
            tasksLayout.addView(view);
        }
    }

    @Override
    public void ViewUpdate(Task object) {
        if(viewMap.containsKey(object.GetID())) {
            SmallTaskView view = viewMap.get(object.GetID());
            view.SetTask(object);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainViewActions.ShowTaskView(object);
                }
            });
        }
    }
}
