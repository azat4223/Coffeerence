package com.example.coffeerence;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private final List<Product> cartItems;

    private CartManager() {
        cartItems = new ArrayList<>();
    }

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addProduct(Product product) {
        cartItems.add(product);
    }

    public List<Product> getItems() {
        return cartItems;
    }

    public double getTotalPrice() {
        double total = 0;
        for (Product p : cartItems) {
            total += p.getPrice();
        }
        return total;
    }

    public void clear() {
        cartItems.clear();
    }
}