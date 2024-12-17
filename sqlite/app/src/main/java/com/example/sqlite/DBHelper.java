package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    final static String DB_NAME = "tunes.db";
    final static String TABLE_NAME = "tunes";
    final static int DB_VERSION = 1;

    public DBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, artist TEXT, year INT, duration INT)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (title, artist, year, duration) VALUES ('Firestarter', 'The Prodigy', 1996, 300)");

    }


    public void addTune(String title, String artist, int year, int duration) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("artist", artist);
        values.put("year", year);
        values.put("duration", duration);

        db.insert(DBHelper.TABLE_NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // вызывается после смены версии
    }
}
