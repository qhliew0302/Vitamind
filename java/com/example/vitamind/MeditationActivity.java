package com.example.vitamind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MeditationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation);

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.mainBottom);

        // Set meditation selected
        bottomNavigationView.setSelectedItemId(R.id.meditation);

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

        ImageView med1, med2, med3, med5, med10, med20;

        // create hooks
        med1 = findViewById(R.id.playButton_1);
        med2 = findViewById(R.id.playButton_2);
        med3 = findViewById(R.id.playButton_3);
        med5 = findViewById(R.id.playButton_4);
        med10 = findViewById(R.id.playButton_5);
        med20 = findViewById(R.id.playButton_6);

        // go to music activity 1
        med1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MeditationActivity.this, MusicActivity1.class);
                startActivity(i);
            }
        });

        // go to music activity 2
        med2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MeditationActivity.this, MusicActivity2.class);
                startActivity(i);
            }
        });

        // go to music activity 3
        med3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MeditationActivity.this, MusicActivity3.class);
                startActivity(i);
            }
        });

        // go to music activity 4
        med5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MeditationActivity.this, MusicActivity4.class);
                startActivity(i);
            }
        });

        // go to music activity 5
        med10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MeditationActivity.this, MusicActivity5.class);
                startActivity(i);
            }
        });

        // go to music activity 6
        med20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MeditationActivity.this, MusicActivity6.class);
                startActivity(i);
            }
        });

    }
}