package com.example.myapplication;

import java.io.Serializable;

public class OrderItem implements Serializable {
    private String itemId;
    private String itemName;
    private String description;
    private double price;
    private int quantity;
    private int imageResourceId;

    public OrderItem(String itemId, String itemName, String description, double price, int quantity, int imageResourceId) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.imageResourceId = imageResourceId;
    }

    public String getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public double getTotal() {
        return price * quantity;
    }
}
