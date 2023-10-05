package com.example.vitamind;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MoodActivity extends AppCompatActivity {

    ImageView backBtn;
    SeekBar seekbar;
    TextView lvl, date;
    int moodLevel;
    Button doneBtn;
    Calendar calendar;
    int currentYear,currentMonth,currentDay;
    String monthText;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood);

        calendar = Calendar.getInstance();
        DB = new DBHelper(this);

        // get current date
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH) + 1;
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        monthText = getMonth(currentMonth);

        //create hooks
        backBtn = findViewById(R.id.backButton);
        seekbar = findViewById(R.id.seekBar);
        lvl = findViewById(R.id.moodLevel);
        date = findViewById(R.id.date);
        doneBtn = findViewById(R.id.doneButton);

        String dateText = currentDay + " " + monthText + " " + currentYear;

        date.setText(dateText);

        // go back to home activity
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MoodActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        });

        // when the user drag the seek bar, the mood level will change accordingly
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                lvl.setText(String.valueOf(progress));
                moodLevel = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // save mood to the database
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Boolean checkInsertData = DB.insertMood(currentDay, currentMonth, currentYear, moodLevel);

                if(checkInsertData){
                    Toast.makeText(MoodActivity.this, "New Mood Added", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MoodActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                }
                else{
                    Toast.makeText(MoodActivity.this, "No Mood Added", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // convert the value of month (int) to its respective text
    private String getMonth(int currentMonth) {
        switch(currentMonth){
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
            default:
                return "";
        }
    }
}