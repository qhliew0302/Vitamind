package com.example.vitamind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class JournalingActivity extends AppCompatActivity {

    CardView cardView;
    ImageView addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journaling);

        // create hooks
        cardView = findViewById(R.id.journalingList);
        addBtn = findViewById(R.id.AddButton2);

        // go to journals list activity
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(JournalingActivity.this, JournalsActivity.class);
                startActivity(i);
            }
        });

        // go to add journals activity
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(JournalingActivity.this, AddJournalsActivity.class);
                startActivity(i);
            }
        });

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.mainBottom);

        // Set journaling selected
        bottomNavigationView.setSelectedItemId(R.id.journaling);

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
                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case journaling:
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