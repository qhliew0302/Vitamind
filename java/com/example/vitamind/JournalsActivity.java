package com.example.vitamind;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class JournalsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> date, journal;
    DBHelper DB;
    MyAdapter2 adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journals);

        DB = new DBHelper(this);
        date = new ArrayList<>();
        journal = new ArrayList<>();

        //create hooks
        recyclerView = findViewById(R.id.recyclerView);

        adapter = new MyAdapter2(this, date, journal);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displayData();
    }

    // get data from the database and store them in the array list
    private void displayData() {

        Cursor cursor = DB.getJournal();
        if(cursor.getCount()==0) {
            Toast.makeText(JournalsActivity.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            while(cursor.moveToNext()) {
                date.add(cursor.getString(1));
                journal.add(cursor.getString(2));
            }
        }

    }
}