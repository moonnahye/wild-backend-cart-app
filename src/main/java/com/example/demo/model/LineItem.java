package com.example.demo.model;

import java.util.UUID;

public class LineItem {

    private final String id;
    private final String productId;
    private final String productOption;

    private int quantity;


    public LineItem(String productId, String productOption, int quantity) {
        this.id = "LineItem-"+ UUID.randomUUID();
        this.productId = productId;
        this.productOption = productOption;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductOption() {
        return productOption;
    }



    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }
}
