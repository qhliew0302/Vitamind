package com.example.vitamind;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddReminderActivity extends AppCompatActivity {

    ImageView backButton;
    DBHelper DB;
    EditText event_name, dateIn, timeIn;
    Button saveBtn;
    String dateText;
    final Calendar myCalendar = Calendar.getInstance () ;


    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        DB = new DBHelper(this);

        //create hooks
        backButton = findViewById(R.id.backButton2);
        event_name = findViewById(R.id.editEventName);
        dateIn = findViewById(R.id.editDate);
        timeIn = findViewById(R.id.editTime);
        saveBtn = findViewById(R.id.SaveButton);

        // hide keyboard
        dateIn.setInputType(InputType.TYPE_NULL);
        timeIn.setInputType(InputType.TYPE_NULL);

        // go back to reminder activity
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddReminderActivity.this, ReminderActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // save the event to the database
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String event_nameText = event_name.getText().toString();
                dateText = dateIn.getText().toString();
                String timeText = timeIn.getText().toString();

                // no empty field is allowed
                if(event_nameText.isEmpty() || dateText.isEmpty() || timeText.isEmpty()){
                    Toast.makeText(AddReminderActivity.this, "Please fill in all the details", Toast.LENGTH_SHORT).show();
                }
                else{
                    Boolean checkInsertData = DB.insertData(event_nameText, dateText, timeText);

                    if(checkInsertData){
                        Toast.makeText(AddReminderActivity.this, "New Event Added", Toast.LENGTH_SHORT).show();
                        updateLabel();
                        Intent i = new Intent (AddReminderActivity.this, ReminderActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else{
                        Toast.makeText(AddReminderActivity.this, "No Event Added", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

        dateIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog(dateIn);
            }
        });

        timeIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeDialog(timeIn);
            }
        });
    }

    // prompt time picker dialog
    private void showTimeDialog(EditText timeIn) {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                timeIn.setText(simpleDateFormat.format(calendar.getTime()));

            }
        };

        new TimePickerDialog(AddReminderActivity.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), false).show();
    }

    // prompt date picker dialog
    private void showDateDialog(EditText dateIn) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR,year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

                dateIn.setText(simpleDateFormat.format(myCalendar.getTime()));

            }
        };

        new DatePickerDialog(AddReminderActivity.this, dateSetListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    // schedule notification
    private void scheduleNotification (Notification notification , long delay) {
        Intent notificationIntent = new Intent(this, MyNotificationPublisher.class);
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        }
        assert alarmManager != null;
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, delay, pendingIntent);
    }

    // build notification
    private Notification getNotification (String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder( this, default_notification_channel_id ) ;
        builder.setContentTitle( "Scheduled Notification" ) ;
        builder.setContentText(content) ;
        builder.setSmallIcon(R.drawable.ic_launcher_foreground ) ;
        builder.setAutoCancel(true) ;
        builder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
        return builder.build() ;
    }

    // set notification
    private void updateLabel () {
        Date date = myCalendar.getTime();
        scheduleNotification(getNotification(dateText) , date.getTime()) ;
    }


}