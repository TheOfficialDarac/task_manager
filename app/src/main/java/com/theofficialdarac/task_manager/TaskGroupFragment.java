package com.theofficialdarac.task_manager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.theofficialdarac.task_manager.databinding.FragmentTaskGroupBinding;
import com.theofficialdarac.task_manager.models.TaskGroup;


public class TaskGroupFragment extends Fragment {
    private TaskGroup currentTG;
    //    private FrameLayout frameLayout;
    FragmentTaskGroupBinding binding;

    public TaskGroupFragment(TaskGroup tg) {
        currentTG = tg;
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_task_group,
                container, false
        );

        binding.setTaskGroup(currentTG);

//        getChildFragmentManager()
//                .beginTransaction()
//                .replace(R.id.flTG, new TGTasksFragment(currentTG.getID()))
//                .commit();
        binding.btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flMain, new CreateTaskFragment(currentTG))
                        .commit();
            }
        });

        binding.btnViewTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flMain, new TGTasksFragment(currentTG))
                        .commit();
            }
        });

        binding.btnEditTG.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flMain, new TGTasksFragment(currentTG))
                    .commit();
        });


        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flMain, new TaskGroupsFragment(((MainActivity) getActivity()).getCurrentUser().getUserID()))
                        .commit();
            }
        });
        return binding.getRoot();
    }
}