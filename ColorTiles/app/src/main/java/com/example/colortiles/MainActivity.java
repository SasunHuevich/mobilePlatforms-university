package com.example.colortiles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int darkColor;
    int brightColor;
    View[][] tiles = new View[4][4];

    public void randomizeColors() {
        Random rand = new Random();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (rand.nextBoolean()) {
                    tiles[i][j].setBackgroundColor(brightColor);
                } else {
                    tiles[i][j].setBackgroundColor(darkColor);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Resources r = getResources();
        darkColor = r.getColor(R.color.dark);
        brightColor = r.getColor(R.color.bright);
        tiles[0][0] = findViewById(R.id.t00);
        tiles[0][1] = findViewById(R.id.t01);
        tiles[0][2] = findViewById(R.id.t02);
        tiles[0][3] = findViewById(R.id.t03);
        tiles[1][0] = findViewById(R.id.t10);
        tiles[1][1] = findViewById(R.id.t11);
        tiles[1][2] = findViewById(R.id.t12);
        tiles[1][3] = findViewById(R.id.t13);
        tiles[2][0] = findViewById(R.id.t20);
        tiles[2][1] = findViewById(R.id.t21);
        tiles[2][2] = findViewById(R.id.t22);
        tiles[2][3] = findViewById(R.id.t23);
        tiles[3][0] = findViewById(R.id.t30);
        tiles[3][1] = findViewById(R.id.t31);
        tiles[3][2] = findViewById(R.id.t32);
        tiles[3][3] = findViewById(R.id.t33);

        randomizeColors();

        Random rand = new Random();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                // случайно выбираем цвет
                if (rand.nextBoolean()) {
                    tiles[i][j].setBackgroundColor(brightColor);
                } else {
                    tiles[i][j].setBackgroundColor(darkColor);
                }
            }
        }

    }
    public void changeColor(View v) {
        ColorDrawable d = (ColorDrawable) v.getBackground();
        if (d.getColor() == brightColor) {
            v.setBackgroundColor(darkColor);
        } else {
            v.setBackgroundColor(brightColor);
        }
    }
    public void onClick(View v) {

        String tag = v.getTag().toString();
        int x = 1, y = 1;

        x = Character.getNumericValue(tag.charAt(0));
        y = Character.getNumericValue(tag.charAt(1));
        changeColor(v);

        for (int i = 0; i < 4; i++) {
            changeColor(tiles[x][i]);
            changeColor(tiles[i][y]);
        }

        if (checkVictory()) {
            Toast.makeText(this, "Вы выиграли!", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean checkVictory() {
        int firstColor = ((ColorDrawable) tiles[0][0].getBackground()).getColor();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (((ColorDrawable) tiles[i][j].getBackground()).getColor() != firstColor) {
                    return false;
                }
            }
        }
        return true;



    }
}
