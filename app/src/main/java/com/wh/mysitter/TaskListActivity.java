package com.wh.mysitter;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.wh.mysitter.bean.ResponseDataTask;
import com.wh.mysitter.bean.Task;
import com.wh.mysitter.myXXX.XXXTaskApiRequest;
import com.wh.mysitter.utils.ReturnDataUtils;

import java.util.List;

public class TaskListActivity extends AppCompatActivity {
    private String TAG = "WH_" + getClass().getSimpleName();
    private MutableLiveData<List<Task>> listMutableLiveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        RecyclerView rv_task = findViewById(R.id.rv_task);
        rv_task.setLayoutManager(new LinearLayoutManager(this));
        final TaskListAdapter taskListAdapter = new TaskListAdapter(this);
        rv_task.setAdapter(taskListAdapter);

        TaskListViewModel taskListViewModel = ViewModelProviders.of(this).get(TaskListViewModel.class);
        listMutableLiveData = taskListViewModel.getTaskListLiveData();
        listMutableLiveData.observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                taskListAdapter.submitList(tasks);
            }
        });


        getTaskList(taskListAdapter);

        findViewById(R.id.floatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask("aaa","bbbb",taskListAdapter);
            }
        });

    }

    private void getTaskList(final TaskListAdapter taskListAdapter) {
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ResponseDataTask responseDataTask = ReturnDataUtils.parseJson(response, ResponseDataTask.class);
                listMutableLiveData.postValue(responseDataTask.getData());
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };

        new XXXTaskApiRequest.TaskListGetter()
                .getUndone()
                .ResultListener(listener)
                .ErrorListener(errorListener)
                .run(getApplicationContext());
    }

    private void addTask(String TITLE, String TASK, final TaskListAdapter taskListAdapter) {
        Response.Listener<String> listener1 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: " + response);
                getTaskList(taskListAdapter);
            }
        };

        Response.ErrorListener errorListener1 = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error.toString());
            }
        };

        new XXXTaskApiRequest.TaskAdder()
                .ResultListener(listener1)
                .ErrorListener(errorListener1)
                .set(TITLE, TASK)
                .run(getApplicationContext());
    }

    private void setDoneUndone(int id, boolean status, final TaskListAdapter taskListAdapter){
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getTaskList(taskListAdapter);
            }
        };
        Response.ErrorListener errorListener1 = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error.toString());
            }
        };
        XXXTaskApiRequest.TaskDoneUndoneSetter setter = new XXXTaskApiRequest.TaskDoneUndoneSetter();
        if(status){
            setter = setter.done(id);
        }else {
            setter = setter.undone(id);
        }
        setter.ResultListener(listener)
                .ErrorListener(errorListener1)
                .run(getApplicationContext());
    }

}