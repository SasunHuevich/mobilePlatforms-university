package com.example.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {


    DBHelper dbHelper;
    SQLiteDatabase db;
    ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        //deleteDatabase("tunes.db");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lv = findViewById(R.id.playlist);
        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();



        displayData("title ASC");

        Button addTuneButton = findViewById(R.id.addTuneButton);
        EditText titleEditText = findViewById(R.id.title);
        EditText artistEditText = findViewById(R.id.artist);
        EditText yearEditText = findViewById(R.id.year);
        EditText durationEditText = findViewById(R.id.duration);

        addTuneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.addTune(titleEditText.getText().toString(), artistEditText.getText().toString(), Integer.parseInt(yearEditText.getText().toString()), Integer.parseInt(durationEditText.getText().toString()));
                displayData("title ASC");


                titleEditText.setText("");
                artistEditText.setText("");
                yearEditText.setText("");
                durationEditText.setText("");
            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String orderBy = null;

        if (item.getItemId() == R.id.sort_title_asc) {
            orderBy = "title ASC";
        } else if (item.getItemId() == R.id.sort_title_desc) {
            orderBy = "title DESC";
        } else if (item.getItemId() == R.id.sort_artist_asc) {
            orderBy = "artist ASC";
        } else if (item.getItemId() == R.id.sort_artist_desc) {
            orderBy = "artist DESC";
        } else if (item.getItemId() == R.id.sort_year_asc) {
            orderBy = "year ASC";
        } else if (item.getItemId() == R.id.sort_year_desc) {
            orderBy = "year DESC";
        }

        if (orderBy != null) {
            displayData(orderBy);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void displayData(String orderBy){
        Cursor c = db.rawQuery("SELECT * FROM " + dbHelper.TABLE_NAME + " ORDER BY " + orderBy, null);
        /*c.getCount();
        c.moveToLast();
        c.getInt(1);*/
        TextView totalCountTextView = findViewById(R.id.totalCount);
        TextView totalDurationTextView = findViewById(R.id.totalDuration);
        String[] table_columns = {"title", "artist", "year", "duration"};
        int[] views = {R.id.title, R.id.artist, R.id.year, R.id.duration};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.item, c, table_columns, views, 0);
        String countQuery = "SELECT COUNT(*) AS total_count, SUM(duration) AS total_duration FROM " + dbHelper.TABLE_NAME;
        Cursor c1 = db.rawQuery(countQuery, null);
        if(c1.moveToFirst()){
            totalCountTextView.setText("Total Records: " + c1.getInt(c1.getColumnIndex("total_count")));
            totalDurationTextView.setText("Total Duration: " + c1.getInt(c1.getColumnIndex("total_duration")) + " sec");
        }

        lv.setAdapter(adapter);

    }
}