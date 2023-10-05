package com.example.vitamind;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder> {

    private Context context;
    private ArrayList date_id, journal_id;
    private MyAdapter.OnItemClickListener listener;


    public MyAdapter2(Context context, ArrayList date_id, ArrayList journal_id) {
        this.context = context;
        this.date_id = date_id;
        this.journal_id = journal_id;
    }

    @NonNull
    @Override
    public MyAdapter2.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.journallist,parent,false);
        return new MyAdapter2.MyViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter2.MyViewHolder holder, int position) {
        holder.date_id.setText(String.valueOf(date_id.get(position)));
        holder.journal_id.setText(String.valueOf(journal_id.get(position)));
    }

    // get number of rows of data in the database
    @Override
    public int getItemCount() {
        return date_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView date_id, journal_id;
        public MyViewHolder(@NonNull View itemView, MyAdapter.OnItemClickListener listener) {
            super(itemView);
            date_id = itemView.findViewById(R.id.textDate);
            journal_id = itemView.findViewById(R.id.textJournal);

        }
    }
}
