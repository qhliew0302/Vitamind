package com.example.vitamind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class SettingsActivity extends AppCompatActivity {

    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.mainBottom);

        // Set settings selected
        bottomNavigationView.setSelectedItemId(R.id.settings);

        // create hooks
        logout = findViewById(R.id.LogoutBtn);

        final int home = R.id.home;
        final int reminder = R.id.reminder;
        final int meditation = R.id.meditation;
        final int journaling = R.id.journaling;
        final int settings = R.id.settings;

        // go to login page
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SettingsActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

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
                        startActivity(new Intent(getApplicationContext(),JournalingActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case settings:
                        return true;
                }
                return false;
            }
        });
    }
}