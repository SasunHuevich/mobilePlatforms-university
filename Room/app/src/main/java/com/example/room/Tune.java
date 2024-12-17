package com.example.room;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Locale;

@Entity(tableName = "tunes")
class Tune {
    @PrimaryKey
    @NonNull
    int _id;
    String artlist, title;
    int year;

    @Ignore
    public Tune(){
        this._id = 11;
        this.year = 1999;
        this.artlist = "";
        this.title = "";
    }

    public Tune(int _id, String artlist, String title, int year){
        this._id = _id;
        this.artlist = artlist;
        this.title = title;
        this.year = year;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "%s: %s (%d)", artlist, title, year);
    }
}
