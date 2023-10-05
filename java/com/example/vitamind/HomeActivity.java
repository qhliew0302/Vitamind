package com.example.vitamind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {

    CardView mood, graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //create hooks
        mood = findViewById(R.id.moodTrack);
        graph = findViewById(R.id.moodGraph);

        // go to mood activity
        mood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, MoodActivity.class);
                startActivity(i);
            }
        });

        // go to graph activity
        graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, GraphActivity.class);
                startActivity(i);
            }
        });

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.mainBottom);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.home);

        final int home = R.id.home;
        final int reminder = R.id.reminder;
        final int meditation = R.id.meditation;
        final int journaling = R.id.journaling;
        final int settings = R.id.settings;

        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case reminder:
                        startActivity(new Intent(getApplicationContext(),ReminderActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case meditation:
                        startActivity(new Intent(getApplicationContext(),MeditationActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case home:
                        return true;
                    case journaling:
                        startActivity(new Intent(getApplicationContext(),JournalingActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case settings:
                        startActivity(new Intent(getApplicationContext(),SettingsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}