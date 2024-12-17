package com.example.arrayadapter;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    ArrayList<String> names = new ArrayList<>();

    ArrayAdapter<String> adapter;

    int selectedPosition = -1;

    public String RandomPeop(){
        String[] first_names = getResources().getStringArray(R.array.names);
        String[] last_names = getResources().getStringArray(R.array.second_names);

        Random random = new Random();

        String s = first_names[random.nextInt(first_names.length)];
        s += " ";
        s += last_names[random.nextInt(last_names.length)];

        return s;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.listview);


        for(int i = 0; i < 30; i++){
            names.add(RandomPeop());
        }

        adapter = new ArrayAdapter<String>(this, R.layout.item, names);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener((parent, view, position, id) -> {

            if (selectedPosition != -1) {
                View previousSelectedView = parent.getChildAt(selectedPosition - parent.getFirstVisiblePosition());
                if (previousSelectedView != null) {
                    previousSelectedView.setBackgroundColor(Color.TRANSPARENT);
                    previousSelectedView.setTag(null);
                }
            }

            if (selectedPosition != position){
                selectedPosition = position;
                view.setBackgroundColor(Color.LTGRAY);
            }
            else{
                selectedPosition = -1;
            }



        });




        //names.add("Kata"); // danie ne izmeniatsa
        //adapter.notifyDataSetChanged();

        //String[] first_names = getResources().getStringArray(R.array.names);
        //String[] last_names = getResources().getStringArray(R.array.second_names);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }

    public void onAddItemClick(View view){
        names.add(RandomPeop());
        adapter.notifyDataSetChanged();
    }

    public void onAnotherButtonClick(View view){
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_input, null);

        EditText editTextFirstName = dialogView.findViewById(R.id.editFirstName);
        EditText editTextLastName = dialogView.findViewById(R.id.editLastName);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);


        builder.setPositiveButton("Добавить", (dialog, which) -> {

            String firstName = ((EditText) dialogView.findViewById(R.id.editFirstName)).getText().toString();
            String lastName = ((EditText) dialogView.findViewById(R.id.editLastName)).getText().toString();


            if (firstName.isEmpty() || lastName.isEmpty()) {
                Toast.makeText(MainActivity.this, "Введите имя и фамилию", Toast.LENGTH_SHORT).show();
            } else {
                names.add(firstName + " " + lastName);
                adapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Отмена", (dialog, which) -> dialog.dismiss());

        builder.show();
    }
}