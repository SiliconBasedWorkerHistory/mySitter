package com.wh.mysitter.task;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wh.mysitter.BaseApp;
import com.wh.mysitter.R;

public class TaskListFragment extends Fragment {

    private AppCompatActivity mParent;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mParent = (AppCompatActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task_list,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TaskListViewModel taskListViewModel = ViewModelProviders.of(requireActivity()).get(TaskListViewModel.class);
        RecyclerView rv_task_list = requireActivity().findViewById(R.id.rv_task_list);
        rv_task_list.setLayoutManager(new LinearLayoutManager(requireContext()));
        TaskListAdapter taskListAdapter = new TaskListAdapter(taskListViewModel,mParent);
        rv_task_list.setAdapter(taskListAdapter);
//        taskListAdapter.submitList(BaseApp.tasks);
    }
}
