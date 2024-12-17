package com.example.films;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class MainActivity extends AppCompatActivity {


    public List<Map<String, Object>> loadMoviesFromJson(Context context){
        try{
            InputStream stream = getAssets().open("movies.json");
            InputStreamReader reader = new InputStreamReader(stream);
            Gson gson = new Gson();
            Type movieListType = new TypeToken<List<Map<String, Object>>>() {}.getType();
            return gson.fromJson(reader, movieListType);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        List<Map<String, Object>> movies = loadMoviesFromJson(this);

        findViewById(R.id.buttonShowRandomMovie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!movies.isEmpty()){
                    int rand = 0;


                    if(movies.size() != 1){
                        rand = new Random().nextInt(movies.size() - 1);
                    }
                    else{
                        rand = 0;
                    }


                    ((TextView) findViewById(R.id.filmTitle)).setText((String) movies.get(rand).get("title"));
                    ((TextView) findViewById(R.id.filmRating)).setText("Оценка: " + String.valueOf(movies.get(rand).get("rating")));
                    ((TextView) findViewById(R.id.filmGenre)).setText("Жанр: " + (String) movies.get(rand).get("genre"));
                    ((TextView) findViewById(R.id.filmDirector)).setText("Режиссер: " + (String) movies.get(rand).get("director"));
                    ((TextView) findViewById(R.id.filmYear)).setText("Год: " + String.valueOf(((Double) movies.get(rand).get("year")).intValue()));

                    movies.remove(rand);
                }
                else{
                    ((TextView) findViewById(R.id.filmTitle)).setText("Фильмы закончились(");
                    ((TextView) findViewById(R.id.filmRating)).setText("");
                    ((TextView) findViewById(R.id.filmGenre)).setText("");
                    ((TextView) findViewById(R.id.filmDirector)).setText("");
                    ((TextView) findViewById(R.id.filmYear)).setText("");
                }
            }
        });




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}