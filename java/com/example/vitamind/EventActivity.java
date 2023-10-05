package com.example.vitamind;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class EventActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> name, date, time;
    DBHelper DB;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        DB = new DBHelper(this);
        name = new ArrayList<>();
        date = new ArrayList<>();
        time = new ArrayList<>();

        //create hooks
        recyclerView = findViewById(R.id.recyclerView);

        adapter = new MyAdapter(this, name, date, time);
        recyclerView.setAdapter(adapter);
        // delete event
        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String event_name = name.get(position);
                name.remove(position);
                date.remove(position);
                time.remove(position);
                adapter.notifyItemRemoved(position);
                DB.deleteData(event_name);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displayData();

    }

    // show data by reading the arraylist
    private void displayData() {

        Cursor cursor = DB.getData();
        if(cursor.getCount()==0) {
            Toast.makeText(EventActivity.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            while(cursor.moveToNext()) {
                name.add(cursor.getString(1));
                date.add(cursor.getString(2));
                time.add(cursor.getString(3));
            }
        }

    }

}