package com.example.vitamind;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {


    // create a database in the phone
    public DBHelper(Context context) {
        super(context, "Vitamind.db", null, 1);
    }


    // create 3 tables in the database
    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table Reminder(id INTEGER primary key autoincrement, event_name TEXT, date TEXT, time TEXT)");
        MyDB.execSQL("create Table Journal(id INTEGER primary key autoincrement, date TEXT, journal TEXT)");
        MyDB.execSQL("create Table Mood(id INTEGER primary key autoincrement, day INTEGER, month INTEGER, year INTEGER, mood INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists Reminder");
        MyDB.execSQL("drop Table if exists Journal");
        MyDB.execSQL("drop Table if exists Mood");

    }

    // insert reminder data to the reminder table
    public Boolean insertData(String event_name, String date, String time){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("event_name", event_name);
        contentValues.put("date", date);
        contentValues.put("time", time);

        long result = MyDB.insert("Reminder", null, contentValues);
        if(result==-1)
        {
            return  false;
        }
        else
        {
            return true;
        }

    }

    // insert journal data to the journal table
    public Boolean insertJournal(String date, String jText){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("date", date);
        contentValues.put("journal", jText);

        long result = MyDB.insert("Journal", null, contentValues);
        if(result==-1)
        {
            return  false;
        }
        else
        {
            return true;
        }

    }

    // insert mood data to the mood table
    public Boolean insertMood(int day, int month, int year, int mood){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("day", day);
        contentValues.put("month", month);
        contentValues.put("year", year);
        contentValues.put("mood", mood);

        long result = MyDB.insert("Mood", null, contentValues);
        if(result==-1)
        {
            return  false;
        }
        else
        {
            return true;
        }

    }

    /**
     * Returns all the data from database
     * @return
     */
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + "Reminder";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getJournal(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + "Journal";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getMoodDay(int month){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + "Mood " + "where month = " + month;
        Cursor data = db.rawQuery(query, null);
        return data;
    }



    // delete event data if the event is cancelled
    public Boolean deleteData(String event_name){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Reminder where event_name = ?", new String[]{event_name});
        if(cursor.getCount()>0)
        {
            long result = DB.delete("Reminder", "event_name=?", new String[]{event_name});
            if(result==-1)
            {
                return  false;
            }
            else
            {
                return true;
            }
        }
        else
        {
            return false;
        }

    }

}
