package com.wh.mysitter;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.wh.mysitter.bean.Task;
import java.util.List;

public class TaskListViewModel extends AndroidViewModel {
    private MutableLiveData<List<Task>> taskListLiveData;
    public TaskListViewModel(@NonNull Application application) {
        super(application);
        taskListLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<Task>> getTaskListLiveData() {
        return taskListLiveData;
    }
}
