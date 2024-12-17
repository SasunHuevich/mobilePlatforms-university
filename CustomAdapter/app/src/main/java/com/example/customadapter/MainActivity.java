package com.example.customadapter;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MainActivity extends AppCompatActivity {

    UserListAdapter adapter;
    ListView listView;

    ArrayList<User> users;

    public ArrayList<User> loadUsersFromJson(Context context){
        try{
            InputStream stream = getAssets().open("users.json");
            InputStreamReader reader = new InputStreamReader(stream);
            Gson gson = new Gson();
            Type userListType = new TypeToken<ArrayList<User>>() {}.getType();
            return gson.fromJson(reader, userListType);
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
        listView = findViewById(R.id.list);

        //ArrayList<User> users = new ArrayList<>();
        users =  loadUsersFromJson(this);


        /*for (int i = 0; i < 10; i++) {
            users.add(new User("Petya", "123", Sex.MAN));
            users.add(new User("Vasya", "234", Sex.MAN));
            users.add(new User("Valya", "456", Sex.WOMAN));
            users.add(new User("UFO", "@@@", Sex.UNKNOWN));
        }*/

        adapter = new UserListAdapter(this, users);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User clickedUser = users.get(position);
                adapter.toggleSelection(clickedUser);
            }
        });



        Button nameButton = findViewById(R.id.sortByNameButton);
        nameButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                adapter.sortByName();
            }
        });


        Button phoneButton = findViewById(R.id.sortByPhoneNumberButton);
        phoneButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                adapter.sortByPhoneNumber();
            }
        });


        Button sexButton = findViewById(R.id.sortBySexButton);
        sexButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                adapter.sortBySex();
            }
        });


        Button addUser = findViewById(R.id.addUserButton);
        addUser.setOnClickListener(v -> addUserButton());


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void addUserButton(){
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_input, null);
        Spinner sexSpinner = dialogView.findViewById(R.id.sexSpinner);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        ArrayAdapter<CharSequence> adapterr = ArrayAdapter.createFromResource(
                MainActivity.this,
                R.array.sex_array,
                android.R.layout.simple_spinner_item
        );
        adapterr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexSpinner.setAdapter(adapterr);


        builder.setPositiveButton("Добавить", (dialog, which) -> {

            String firstName = ((EditText) dialogView.findViewById(R.id.editFirstName)).getText().toString();
            String phone = ((EditText) dialogView.findViewById(R.id.editPhoneNumber)).getText().toString();

            Sex sex = Sex.valueOf(sexSpinner.getSelectedItem().toString());

            if (firstName.isEmpty() || phone.isEmpty()) {
                Toast.makeText(MainActivity.this, "Введите имя и фамилию", Toast.LENGTH_SHORT).show();
            } else {
                users.add(new User(firstName, phone, sex));
                adapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Отмена", (dialog, which) -> dialog.dismiss());

        builder.show();
    }



}