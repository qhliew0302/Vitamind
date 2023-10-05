package com.example.vitamind;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity {

    GraphView graphView;
    DBHelper DB;
    ArrayList<Integer> day, mood;
    LineGraphSeries<DataPoint> series;
    int x, y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        DB = new DBHelper(this);

        day = new ArrayList<Integer>();
        mood = new ArrayList<Integer>();
        getData();

        // create hooks
        graphView = findViewById(R.id.moodGraph);

        series = new LineGraphSeries<DataPoint>();

        // plot data point according to the mood stored in the database
        for (int i = 0; i < day.size(); i++) {
            x = day.get(i);
            y = mood.get(i);
            series.appendData(new DataPoint(x,y), true, 31);
        }

        graphView.addSeries(series);
    }

    // read the mood from the array list
    private void getData() {

        Cursor cursor = DB.getMoodDay(12);
        if(cursor.getCount()==0) {
            Toast.makeText(GraphActivity.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            while(cursor.moveToNext()) {
                day.add(Integer.valueOf(cursor.getString(1)));
                mood.add(Integer.valueOf(cursor.getString(4)));
            }
        }

    }
}