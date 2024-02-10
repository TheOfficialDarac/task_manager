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

import com.theofficialdarac.task_manager.databinding.FragmentTasksBinding;
import com.theofficialdarac.task_manager.interfaces.ItemClickListener;
import com.theofficialdarac.task_manager.models.Task;
import com.theofficialdarac.task_manager.view.TaskAdapter;
import com.theofficialdarac.task_manager.viewmodel.MyViewModel;

import java.util.List;
import java.util.stream.Collectors;

public class TasksFragment extends Fragment implements ItemClickListener {
    private Task.State state;
    private int taskGroupID;
    private MyViewModel myViewModel;
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FragmentTasksBinding binding;
    private List<Task> lTasks;

    public TasksFragment(int taskGroupID, Task.State state) {
        // Required empty public constructor
        this.taskGroupID = taskGroupID;
        this.state = state;
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

    private void getTGTasks() {
        myViewModel.getTGTasks(taskGroupID).observe(requireActivity(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasksFromLiveData) {
//                lTasks = tasksFromLiveData;

                lTasks = tasksFromLiveData.stream()
                        .filter(new java.util.function.Predicate<Task>() {
                            @Override
                            public boolean test(Task task) {
                                return task.getState() == state;
                            }
                        }).collect(Collectors.toList());
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

        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void customOnClick(View view, int position) {
        Toast.makeText(getActivity(), lTasks.get(position).getTitle() + " is clicked!", Toast.LENGTH_SHORT).show();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flMain, new TaskFragment(lTasks.get(position), taskGroupID, false))
                .commit();
    }
}
