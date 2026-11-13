package com.example.coffeerence;

import java.util.List;

public class Order {
    private static int nextId = 1;
    private int id;
    private List<Product> items;
    private String status;

    // Пустой конструктор для Gson (если используем сохранение)
    public Order() {
        this.id = nextId++;
        this.status = "В обработке";
    }

    public Order(List<Product> items) {
        this.id = nextId++;
        this.items = items;
        this.status = "В обработке";
    }

    public int getId() { return id; }
    public List<Product> getItems() { return items; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getTotalPrice() {
        if (items == null || items.isEmpty()) return 0;
        double sum = 0;
        for (Product p : items) {
            sum += p.getPrice();
        }
        return sum;
    }
}