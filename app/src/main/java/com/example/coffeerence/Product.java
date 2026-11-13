package com.example.coffeerence;

public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private String imageUrl;
    private String ingredients;

    public Product(int id, String name, String description, double price, String imageUrl, String ingredients) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.ingredients = ingredients;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }
    public String getIngredients() { return ingredients; }
}