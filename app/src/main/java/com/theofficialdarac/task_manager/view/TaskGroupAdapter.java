package com.theofficialdarac.task_manager.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.theofficialdarac.task_manager.R;
import com.theofficialdarac.task_manager.databinding.FragmentTaskGroupItemBinding;
import com.theofficialdarac.task_manager.interfaces.ItemClickListener;
import com.theofficialdarac.task_manager.models.TaskGroup;

import java.util.List;

public class TaskGroupAdapter extends RecyclerView.Adapter<TaskGroupAdapter.ViewHolder> {

    private Context context;
    private List<TaskGroup> lTGs;

    private ItemClickListener clickListener;

    public TaskGroupAdapter(Context context, List<TaskGroup> lTGs) {
        this.context = context;
        this.lTGs = lTGs;
    }

    public void setOnClickListener(ItemClickListener myListener) {
        clickListener = myListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FragmentTaskGroupItemBinding binding = DataBindingUtil
            .inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.fragment_task_group_item,
                parent,
                false);

        return new TaskGroupAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TaskGroup tg = lTGs.get(position);
        holder.taskGroupListItemBinding.setTaskGroup(tg);
//        holder.tvTGTitle.setText(lTGs.get(holder.getAdapterPosition()).getTitle());
//        holder.tvTGDescription.setText(lTGs.get(holder.getAdapterPosition()).getDescription());
//        holder.btnDeleteTG.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                if(ApiManager.getInstance().services().deleteTG(lTGs.get(position).getID()).isSuccessful()) {
////                    lTGs.remove(lTGs.get(position));
//                    notifyItemRemoved(holder.getAdapterPosition());
//                    notifyItemRangeRemoved(holder.getAdapterPosition(), lTGs.size());
////                }
////                else Toast.makeText(context, "Cannot delete item. Please try again later.", Toast.LENGTH_SHORT).show();
//            }
//        });
        holder.taskGroupListItemBinding.btnCardDeleteTG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(ApiManager.getInstance().services().deleteTG(lTGs.get(position).getID()).isSuccessful()) {
                    lTGs.remove(lTGs.get(position));
                    notifyItemRemoved(holder.getBindingAdapterPosition());
                    notifyItemRangeRemoved(holder.getBindingAdapterPosition(), 1);
////                }
////                else Toast.makeText(context, "Cannot delete item. Please try again later.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return lTGs.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        protected TextView tvTGTitle, tvTGDescription;
//        protected MaterialButton btnDeleteTG;

        private FragmentTaskGroupItemBinding taskGroupListItemBinding;

        protected ViewHolder(FragmentTaskGroupItemBinding itemBinding) {
            super(itemBinding.getRoot());
//            tvTGTitle = itemBinding.findViewById(R.id.tvTGCardTitle);
//            tvTGDescription = itemView.findViewById(R.id.tvTGCardDescription);
//            btnDeleteTG = itemView.findViewById(R.id.btnCardDeleteTG);
            this.taskGroupListItemBinding = itemBinding;
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
