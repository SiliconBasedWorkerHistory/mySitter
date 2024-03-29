package com.wh.mysitter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.wh.mysitter.bean.Task;

public class TaskListAdapter extends ListAdapter<Task, TaskListAdapter.MyViewHolder> {
    AppCompatActivity mParent;

    public TaskListAdapter(TaskListViewModel taskListViewModel, AppCompatActivity mParent) {
        super(new DiffUtil.ItemCallback<Task>() {
            @Override
            public boolean areItemsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
                return (oldItem.getCon().equals(newItem.getCon())
                        && oldItem.getId() == newItem.getId()
                        && oldItem.getCreateTime().equals(newItem.getCreateTime())
                        && oldItem.getTitle().equals(newItem.getTitle())
                        && oldItem.isReadDone() == newItem.isReadDone());
            }
        });
        this.mParent = mParent;
    }

    public TaskListAdapter(AppCompatActivity mParent) {
        super(new DiffUtil.ItemCallback<Task>() {
            @Override
            public boolean areItemsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
                return (oldItem.getCon().equals(newItem.getCon())
                        && oldItem.getId() == newItem.getId()
                        && oldItem.getCreateTime().equals(newItem.getCreateTime())
                        && oldItem.getTitle().equals(newItem.getTitle())
                        && oldItem.isReadDone() == newItem.isReadDone());
            }
        });
        this.mParent = mParent;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View ItemView = layoutInflater.inflate(R.layout.item_task_list, parent, false);
        final MyViewHolder holder = new MyViewHolder(ItemView);
        holder.cb_done.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Task task = (Task) holder.itemView.getTag(R.id.task_tag);
                task.setReadDone(b);

//                String url = ""

                if (b) {
                    holder.tv_con.setPaintFlags(holder.tv_con.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    holder.tv_con.setPaintFlags(holder.tv_con.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }
            }
        });
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                View v_task_item_dialog = LayoutInflater.from(mParent).inflate(R.layout.item_task_list_item_dialog,null,false);
//
//                TextView tv_task_title = v_task_item_dialog.findViewById(R.id.tv_task_item_title);
//                tv_task_title.setText(holder.title);
//                TextView tv_task_dev = v_task_item_dialog.findViewById(R.id.tv_task_item_device);
//                tv_task_dev.setText(holder.device);
//                TextView tv_task_createTime = v_task_item_dialog.findViewById(R.id.tv_task_item_create_time);
//                tv_task_createTime.setText(TimeUtils.getFormattedTime(holder.createTime));
//                TextView tv_task_con = v_task_item_dialog.findViewById(R.id.tv_task_item_task);
//                tv_task_con.setText(holder.task);
//
//                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mParent)
//                        .setView(v_task_item_dialog);
//                MyDialog myDialog = new MyDialog(alertDialog);
//                myDialog.setFullScreen();
//                myDialog.show(mParent.getSupportFragmentManager(),"myDeskClock_task_list_item");
//            }
//        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Task task = getItem(position);
        holder.itemView.setTag(R.id.task_tag, task);
        holder.tv_con.setText(task.getCon());
        holder.cb_done.setChecked(task.isReadDone());
        holder.title = task.getTitle();
        holder.device = task.getDeviceName();
        holder.createTime = task.getCreateTime();
        holder.task = task.getCon();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_con;
        CheckBox cb_done;
        String title,device,task;
        Long createTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_con = itemView.findViewById(R.id.tv_task_list_item_con);
            cb_done = itemView.findViewById(R.id.cb_task_list_item_done);
        }
    }
}
