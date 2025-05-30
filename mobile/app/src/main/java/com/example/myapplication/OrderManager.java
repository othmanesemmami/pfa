package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    private static OrderManager instance;
    private List<Order> orders;

    private OrderManager() {
        orders = new ArrayList<>();
    }

    public static synchronized OrderManager getInstance() {
        if (instance == null) {
            instance = new OrderManager();
        }
        return instance;
    }

    public void addOrder(Order order) {
        orders.add(0, order); // Ajouter au début de la liste (plus récent en premier)
    }

    public List<Order> getOrders() {
        return orders;
    }

    public Order getOrderById(String orderId) {
        for (Order order : orders) {
            if (order.getOrderId().equals(orderId)) {
                return order;
            }
        }
        return null;
    }

    // Méthode pour supprimer une commande
    public boolean removeOrder(String orderId) {
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getOrderId().equals(orderId)) {
                orders.remove(i);
                return true;
            }
        }
        return false;
    }

    // Méthode pour mettre à jour le statut d'une commande
    public boolean updateOrderStatus(String orderId, String newStatus) {
        for (Order order : orders) {
            if (order.getOrderId().equals(orderId)) {
                order.setStatus(newStatus);
                return true;
            }
        }
        return false;
    }
}