package com.example.guessnumbergame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void onClick(View v){
        EditText edBegin = findViewById(R.id.begin);
        EditText edEnd = findViewById(R.id.end);

        int begin = edBegin.getText().toString().isEmpty() ? 0 : Integer.parseInt(edBegin.getText().toString());
        int end = edEnd.getText().toString().isEmpty() ? 100 : Integer.parseInt(edEnd.getText().toString());

        if (begin >= end) {
            Toast.makeText(this, "Начальное число должно быть меньше конечного!", Toast.LENGTH_SHORT).show();
            return;
        }


        Intent i = new Intent(getApplicationContext(), GameActivity.class);
        i.putExtra("begin", begin);
        i.putExtra("end", end);

        startActivity(i);
    }
}