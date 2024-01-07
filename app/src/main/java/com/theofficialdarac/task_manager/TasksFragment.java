package com.theofficialdarac.task_manager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.theofficialdarac.task_manager.databinding.FragmentTaskItemBinding;
import com.theofficialdarac.task_manager.databinding.FragmentTasksBinding;
import com.theofficialdarac.task_manager.serviceapi.ApiManager;
import com.theofficialdarac.task_manager.view.TaskAdapter;
import com.theofficialdarac.task_manager.interfaces.ItemClickListener;
import com.theofficialdarac.task_manager.models.Task;
import com.theofficialdarac.task_manager.view.TaskGroupAdapter;
import com.theofficialdarac.task_manager.viewmodel.MyViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TasksFragment extends Fragment implements ItemClickListener {
    private int taskGroupID;
    private MyViewModel myViewModel;
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FragmentTasksBinding binding;
    private List<Task> lTasks;

    public TasksFragment(int taskGroupID) {
        // Required empty public constructor
        this.taskGroupID = taskGroupID;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_tasks,
                container,
                false
        );

        myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);


        swipeRefreshLayout = binding.srlTasks;
        swipeRefreshLayout.setColorSchemeResources(R.color.oxford_blue);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTGTasks();
            }
        });

        //  get & display data
        getTGTasks();
        return binding.getRoot();
    }
    @Override
    public void customOnClick(View view, int position) {
        Toast.makeText(getActivity(), lTasks.get(position).getTitle() + " is clicked!", Toast.LENGTH_SHORT).show();
    }
    private void getTGTasks() {
        myViewModel.getTGTasks(taskGroupID).observe(requireActivity(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasksFromLiveData) {
                lTasks = tasksFromLiveData;
                displayTasks();
            }

        });
    }
    private void displayTasks() {
        recyclerView = binding.rvTasks;
        adapter = new TaskAdapter(getContext(), lTasks);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnClickListener(this::customOnClick);
//        adapter.notifyDataSetChanged();

        if(swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }
}