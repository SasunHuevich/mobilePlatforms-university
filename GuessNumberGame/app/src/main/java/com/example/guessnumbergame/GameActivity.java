package com.example.guessnumbergame;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    int begin, end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);


        Intent i = getIntent();
        begin = i.getIntExtra("begin", 0);
        end = i.getIntExtra("end", 100);


        ((TextView) findViewById(R.id.message)).setText("Ваше число больше: " + ((begin + end) / 2) + "?");


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void onClick(View v){
        int viewId = v.getId();
        if(end - begin == 1){
            ((TextView) findViewById(R.id.message)).setText("Ваше число: " + end + "!");
            findViewById(R.id.yes).setVisibility(View.INVISIBLE);
            findViewById(R.id.no).setVisibility(View.INVISIBLE);
        }
        else if(viewId == R.id.yes){
            begin = ((begin + end) / 2);
            ((TextView) findViewById(R.id.message)).setText("Ваше число больше: " + ((begin + end) / 2) + "?");
        }
        else if(viewId == R.id.no){
            end = ((begin + end) / 2);
            ((TextView) findViewById(R.id.message)).setText("Ваше число больше: " + ((begin + end) / 2) + "?");
        }


    }
}
