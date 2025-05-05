package com.example.myapplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartManager {
    private static CartManager instance;
    private Map<String, CartItem> cartItems; // Using a Map with unique keys

    private CartManager() {
        cartItems = new HashMap<>();
    }

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addToCart(CartItem newItem) {
        // Create a unique key for each product
        String key = newItem.getProductId() + "_" + newItem.getProductName();

        // Check if the item already exists
        if (cartItems.containsKey(key)) {
            // Update quantity
            CartItem existingItem = cartItems.get(key);
            existingItem.setQuantity(existingItem.getQuantity() + newItem.getQuantity());
        } else {
            // Add new item
            cartItems.put(key, newItem);
        }
    }

    public List<CartItem> getCartItems() {
        return new ArrayList<>(cartItems.values());
    }

    public int getTotalItems() {
        int total = 0;
        for (CartItem item : cartItems.values()) {
            total += item.getQuantity();
        }
        return total;
    }

    public double getTotalPrice() {
        double total = 0;
        for (CartItem item : cartItems.values()) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }

    public void removeItem(int productId) {
        // Find and remove the item with the matching product ID
        List<String> keysToRemove = new ArrayList<>();
        for (Map.Entry<String, CartItem> entry : cartItems.entrySet()) {
            if (entry.getValue().getProductId() == productId) {
                keysToRemove.add(entry.getKey());
            }
        }

        for (String key : keysToRemove) {
            cartItems.remove(key);
        }
    }
    public void removeFromCart(int productId) {
        String key = String.valueOf(productId);
        cartItems.remove(key);
    }
    public void clearCart() {
        cartItems.clear();}

}