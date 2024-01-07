package com.theofficialdarac.task_manager.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.theofficialdarac.task_manager.R;
import com.theofficialdarac.task_manager.databinding.FragmentTaskItemBinding;
import com.theofficialdarac.task_manager.interfaces.ItemClickListener;
import com.theofficialdarac.task_manager.models.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    private Context context;
    private List<Task> lTasks;

    private ItemClickListener clickListener;

    public TaskAdapter(Context context, List<Task> lTasks) {
        this.context = context;
        this.lTasks = lTasks;
    }

    public void setOnClickListener(ItemClickListener myListener) {
        clickListener = myListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FragmentTaskItemBinding binding = DataBindingUtil
                .inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.fragment_task_item,
                        parent,
                        false);
        return new TaskAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Task task = lTasks.get(position);
        holder.taskListItemBinding.setTask(task);
        holder.taskListItemBinding.btnTaskCardDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (ApiManager.getInstance().services().deleteTask(lTasks.get(position).getID()).isSuccessful()) {
                    lTasks.remove(lTasks.get(position));
                    notifyItemRemoved(position);
//                } else {
//                    Toast.makeText(context, "Cannot delete item. Please try again later.", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lTasks != null ? lTasks.size() : 0;
    }

    protected class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private FragmentTaskItemBinding taskListItemBinding;

        protected MyViewHolder(FragmentTaskItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.taskListItemBinding = itemBinding;
            itemBinding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.customOnClick(v, getAdapterPosition());
            }
        }
    }
}
