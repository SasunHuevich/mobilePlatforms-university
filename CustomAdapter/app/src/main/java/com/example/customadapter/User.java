package com.example.customadapter;

public class User {
    String name, phoneNumber;
    Sex sex;

    boolean isSelected;

    public User(String name, String phoneNumber, Sex sex){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.sex = sex;
        this.isSelected = false;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Sex getSex() {
        return sex;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

}
