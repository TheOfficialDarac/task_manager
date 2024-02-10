package com.theofficialdarac.task_manager;

import android.icu.text.CaseMap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.button.MaterialButton;
import com.theofficialdarac.task_manager.databinding.FragmentTaskGroupsBinding;
import com.theofficialdarac.task_manager.view.TaskGroupAdapter;
import com.theofficialdarac.task_manager.interfaces.ItemClickListener;
import com.theofficialdarac.task_manager.models.TaskGroup;
import com.theofficialdarac.task_manager.viewmodel.MyViewModel;

import java.util.List;


public class TaskGroupsFragment extends Fragment implements ItemClickListener {
    private MyViewModel myViewModel;
    private RecyclerView recyclerView;
    private TaskGroupAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FragmentTaskGroupsBinding binding;
    private List<TaskGroup> taskGroups;
    private int userID;

    public TaskGroupsFragment(int userID) {
        // Required empty public constructor
        this.userID = userID;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_task_groups,
                container,false
        );

        getActivity().setTitle(getResources().getString(R.string.title_task_groups));

        binding.mbtnAddTG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flMain, new CreateTGFragment(userID))
                        .commit();
            }
        });

        myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);

        swipeRefreshLayout = binding.srlTGs;
        swipeRefreshLayout.setColorSchemeResources(R.color.oxford_blue);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTGs();
            }
        });

        getTGs();

        return binding.getRoot();
    }

    private void getTGs() {
        myViewModel.getAllUserTGs(userID).observe(requireActivity(), new Observer<List<TaskGroup>>() {
            @Override
            public void onChanged(List<TaskGroup> taskGroupsFromLiveData) {
                taskGroups = taskGroupsFromLiveData;
                displayTGs();
            }
        });
    }

    private void displayTGs() {
        recyclerView = binding.rvTGs;
        adapter = new TaskGroupAdapter(getContext(), taskGroups);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnClickListener(this::customOnClick);
//        adapter.notifyDataSetChanged();
        if(swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }

    public void customOnClick(View view, int position) {
//        Toast.makeText(getActivity(), myViewModel.getAllUserTGs().getValue().get(position).getTitle(), Toast.LENGTH_SHORT).show();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flMain, new TaskGroupFragment(myViewModel.getAllUserTGs(userID).getValue().get(position)))
                .commit();
    }
}