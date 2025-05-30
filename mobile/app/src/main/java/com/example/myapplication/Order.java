package com.example.myapplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {
    private String orderId;
    private String orderDate;
    private String estimatedDelivery;
    private double totalAmount;
    private String status;
    private List<OrderItem> items;
    private boolean isReviewed;

    public Order(String orderId, String orderDate, String estimatedDelivery, double totalAmount, String status) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.estimatedDelivery = estimatedDelivery;
        this.totalAmount = totalAmount;
        this.status = status;
        this.items = new ArrayList<>();
        this.isReviewed = false;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getEstimatedDelivery() {
        return estimatedDelivery;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public void addItem(OrderItem item) {
        this.items.add(item);
    }

    public boolean isReviewed() {
        return isReviewed;
    }

    public void setReviewed(boolean reviewed) {
        isReviewed = reviewed;
    }
}