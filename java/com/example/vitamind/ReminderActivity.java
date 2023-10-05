package com.example.vitamind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ReminderActivity extends AppCompatActivity {

    ImageView addReminder;
    CardView myEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.mainBottom);

        // Set Reminder selected
        bottomNavigationView.setSelectedItemId(R.id.reminder);

        // create hooks
        addReminder = findViewById(R.id.AddButton);
        myEvent = findViewById(R.id.eventList);

        // go to add reminder activity
        addReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ReminderActivity.this, AddReminderActivity.class);
                startActivity(i);
            }
        });

        // go to event activity
        myEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ReminderActivity.this, EventActivity.class);
                startActivity(i);
            }
        });


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
                        return true;
                    case meditation:
                        startActivity(new Intent(getApplicationContext(),MeditationActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case home:
                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        overridePendingTransition(0,0);
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