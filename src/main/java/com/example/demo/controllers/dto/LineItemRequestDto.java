package com.example.demo.controllers.dto;

public class LineItemRequestDto {
    private String productId;
    private String color;
    private String size;
    private int quantity;

    public LineItemRequestDto(String productId, String color, String size, int quantity) {
        this.productId = productId;
        this.color = color;
        this.size = size;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public String getColor() {
        return color;
    }

    public String getSize() {
        return size;
    }

    public int getQuantity() {
        return quantity;
    }
}
