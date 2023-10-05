package com.example.vitamind;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context context;
    private ArrayList name_id, date_id, time_id;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener){
        listener = clickListener;
    }

    public MyAdapter(Context context, ArrayList name_id, ArrayList date_id, ArrayList time_id) {
        this.context = context;
        this.name_id = name_id;
        this.date_id = date_id;
        this.time_id = time_id;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.eventlist,parent,false);
        return new MyViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name_id.setText(String.valueOf(name_id.get(position)));
        holder.date_id.setText(String.valueOf(date_id.get(position)));
        holder.time_id.setText(String.valueOf(time_id.get(position)));
    }

    // get number of rows of data in the database
    @Override
    public int getItemCount() {
        return name_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name_id, date_id, time_id;
        Button del_id;
        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            name_id = itemView.findViewById(R.id.textName);
            date_id = itemView.findViewById(R.id.textDate);
            time_id = itemView.findViewById(R.id.textTime);
            del_id = itemView.findViewById(R.id.deleteButton);

            del_id.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
