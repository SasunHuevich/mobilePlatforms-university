package com.example.customadapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class UserListAdapter extends BaseAdapter {
    Context ctx;

    ArrayList<User> users;

    public void sortByName(){
        Collections.sort(users, (user1, user2) -> user1.getName().compareTo(user2.getName()));
        notifyDataSetChanged();
    }

    public void sortByPhoneNumber(){
        Collections.sort(users, (user1, user2) -> user1.getPhoneNumber().compareTo(user2.getPhoneNumber()));
        notifyDataSetChanged();
    }


    public void sortBySex(){
        Collections.sort(users, (user1, user2) -> user1.getSex().compareTo(user2.getSex()));
        notifyDataSetChanged();
    }

    public UserListAdapter(Context ctx, ArrayList<User> users){
        this.ctx = ctx;
        this.users = users;
    }

    @Override
    public int getCount(){
        return users.size();
    }

    @Override
    public Object getItem(int position){
        return users.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    public void toggleSelection(User user) {

        for (User u : users) {
            u.setSelected(false);
        }

        user.setSelected(!user.isSelected());
        notifyDataSetChanged();
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        // получаем данные из коллекции
        Date begin = new Date();
        User u = users.get(position);

        // создаем разметку (контейнер)
        convertView = LayoutInflater.from(ctx). inflate(R.layout.useritem, parent, false);
        // получаем ссылки на элементы интерфейса
        ImageView ivUserpic=  convertView.findViewById(R.id.userpic);
        ivUserpic.setOnClickListener((v) -> {v.setBackgroundColor(Color.RED);});
        TextView tvName = convertView.findViewById(R.id.name);
        TextView tvPhone = convertView.findViewById(R.id.phone);

        // задаем содержание
        tvName.setText(u.name);
        tvPhone.setText(u.phoneNumber);
        switch(u.sex){
            case MAN:
                ivUserpic.setImageResource(R.drawable.user_man);
                break;
            case WOMAN:
                ivUserpic.setImageResource(R.drawable.user_woman);
                break;
            case UNKNOWN:
                ivUserpic.setImageResource(R.drawable.user_unknown);
                break;
        }


        if (u.isSelected()) {
            convertView.setBackgroundColor(ContextCompat.getColor(ctx, R.color.selected_item_color));
        } else {
            convertView.setBackgroundColor(ContextCompat.getColor(ctx, R.color.default_item_color));
        }


        Date finish = new Date();
        Log.d("mytag", "getView time: " + (finish.getTime() - begin.getTime()));
        return convertView;

    }

}
