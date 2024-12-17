package com.example.room;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    DB db;
    ListView listView;
    ArrayAdapter<String> adapter;
    DBHelperWithLoader dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        deleteOldDatabase(this);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);



        //db = TunesDB.create(this,false);
        dbHelper = new DBHelperWithLoader(this);
        db = DB.getInstance(this);
        addCategoryForTesting();
        listView = findViewById(R.id.listview);
        //Cursor c = db.query("SELECT * FROM tunes", null);
        //setCursorInUIThread(c);
        Log.d("DB Path", "Database is located at: " + getApplicationContext().getDatabasePath("tunes.db").getAbsolutePath());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void addCategoryForTesting() {
        new Thread(() -> {
            Category testCategory = new Category("Test Category");
            db.categoryDao().insert(testCategory);
            Log.d("CategoryDao", "Inserted category: " + testCategory.getName());
        }).start();
    }

    public void setCursorInUIThread(Cursor c){
        Context ctx = getApplicationContext();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SimpleCursorAdapter adapter = new SimpleCursorAdapter(ctx, R.layout.tune_item, c, c.getColumnNames(), new int[]{R.id._id, R.id.artist, R.id.title, R.id.year}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                Log.d("mytag", "Records in adapter: " + adapter.getCount());
                ListView lv = findViewById(R.id.listview);
                lv.setAdapter(adapter);
            }
        });
    }


    public void onClearClick(View v){
        new Thread(){
            @Override
            public void run(){
                db.clearAllTables();
                Cursor c = db.query("SELECT * FROM tunes", null);
                setCursorInUIThread(c);
            }
        }.start();
    }


    /*public void onAddTuneClick(View v){
        new Thread(){
            @Override
            public void run(){
                //Cursor c = db.query("SELECT * FROM tunes", null);
                //Log.d("mytag", "Records before insert: " + c.getCount());
                Playlist playlist = db.playlist();

                Random r = new Random();
                playlist.insert(new Tune(r.nextInt(10000), "The Prodigy", "Matrix theme", 2000));

                Cursor c = db.query("SELECT * FROM tunes", null);
                Log.d("mytag", "Records after insert: " + c.getCount());
                setCursorInUIThread(c);

            }
        }.start();
    }*/


    public void onCategories(View view) {
        new Thread(() -> {
            List<Category> categories = db.categoryDao().getAllCategories();
            Log.d("mytag", "Categories: " + categories);
            List<String> categoryNames = categories.stream().map(Category::getName).toList();

            Log.d("mytag", "Categories: " + categoryNames);
            runOnUiThread(() -> {
                updateListView(categoryNames);
                setupCategoryClickListener(categories);
            });
        }).start();
    }

    public void onProducts(View view) {
        new Thread(() -> {
            List<Product> products = db.productDao().getAllProducts();
            List<String> productNames = products.stream().map(product -> product.getName()).toList();

            runOnUiThread(() -> {
                updateListView(productNames);
                listView.setOnItemClickListener(null);
            });
        }).start();
    }


    private void updateListView(List<String> items) {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
    }

    private void setupCategoryClickListener(List<Category> categories) {
        listView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            Category selectedCategory = categories.get(position);

            new Thread(() -> {
                List<Product> products = db.productDao().getProductsByCategory(selectedCategory.getId());
                List<String> productNames = products.stream().map(Product::getName).toList();

                runOnUiThread(() -> updateListView(productNames));
                listView.setOnItemClickListener(null);
            }).start();
        });
    }

    public void deleteOldDatabase(Context context) {
        File dbFile = context.getDatabasePath("tunes.db");
        if (dbFile.exists()) {
            boolean deleted = dbFile.delete();
        }
    }

}