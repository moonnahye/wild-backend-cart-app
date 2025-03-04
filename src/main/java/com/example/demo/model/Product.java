package com.example.demo.model;

public class Product {

    private ProductId id;
    private String name;
    private int price;

    public Product(ProductId id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public ProductId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void changePrice(int price) {
        this.price = price;
    }

    public void changeName(String name) {
        this.name = name;
    }
}
