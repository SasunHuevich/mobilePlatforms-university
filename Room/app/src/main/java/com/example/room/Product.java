package com.example.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "Products",
        primaryKeys = {"ProductID"},
        foreignKeys = @ForeignKey(
                entity = Category.class,
                parentColumns = "CategoryID",
                childColumns = "CategoryID",
                onDelete = ForeignKey.CASCADE
        )
)
public class Product {
    //@PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ProductID")
    private int id;

    @ColumnInfo(name = "ProductName")
    private String name;


    @ColumnInfo(name = "CategoryID")
    private int categoryId;

    @ColumnInfo(name = "Price")
    private double price;

    public Product(String name, double price, int categoryId) {
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
